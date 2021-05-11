package org.apache.spark.deploy.decisionmaking

import com.fasterxml.jackson.databind.ObjectMapper
import scalaj.http.{Http, HttpOptions}

/**
 * this is http class for accessing API from xgboost model (ML) in python
 * which is located in server
 * the steps:
 * 1. http post request and expecting output probability 1,2,3,6,7 EPN in HttpResponse[String]
 * 2. assert to check whether we get the response result
 * 3. convert the HttpResponse[String] into json form
 * 4. from the json, check which probability value is the highest and assign as EPN value (in int)
 * 5. pass the EPN value to theBrain class (theBrain class require EPN value and will process other
 * calculation of all important parameter settings
 */
class CalculateUtil {

  /**
   * Benchmark / Upper Bound / Rules
   */
  // Rule 1 : Memory Overhead Coefficient (0.1)
  // Percentage of memory in each executor that will be reserved for spark.executor.memoryOverhead
  val memoryOverheadCoefficient = 0.1
  // Rule 2 : Executor Memory Upper Bound (64GB)
  // The upper bound for executor memory. Each executor runs on its own JVM. Upwards of 64GB of memory and grabage collection issues can cause slowness
  val executorMemoryUpperBound = "64"
  // Rule 3 : Executor Core Upper Bound (5)
  // The upper bound for cores per executor. More than 5 cores per executor can degrade HDFS I/O throughput. I believe this value can safely be increased if writing to a web-based "file system" such as S3, but significant increases to this limit are not recommended
  val executorCoreUpperBound = "5"
  // Rule 4 : OS Reserved Cores (1)
  // Cores per machine to reserve for OS processes. Should be zero if only a percentage of the machine's core were made available to Spark (ie; entered in the Cores Per Node field above)
  val osReservedCores = 1
  // Rule 5 : OS Reserved Memory (1GB)
  // The amount of RAM per machine to reserve for OS processes. Should be zero if only a percentage of the machine's RAM was made available to Spark (ie; entered in the Memory Per Node field above)
  val osReservedMemory = 1
  // Rule 6 : Parallelism Per Core (2)
  // The level of parallelism per allocated core. This field is used to determine the spark.default.parallelism setting. Generally recommended setting for this value is double the number of cores
  val parallelismPerCore = 2

  def calulateValue(requestData: RequestData) = {
    val masterMemory: String = requestData.mm
    val masterCores: String = requestData.mc
    val workerNodes: String = requestData.wn
    val workerMemoryPerNode: String = requestData.wmn
    val workerCoresPerNode: String = requestData.wcn
    val availableMasterMemory:Int = masterMemory.toInt - 1
    val availableMasterCores:Int = masterCores.toInt - 1
    val availableWorkerMemory:Int = workerMemoryPerNode.toInt - 1
    val availableWorkerCores:Int = workerCoresPerNode.toInt - 1


    val executorsPerNode = new HttpRequestUtil().requestEPNValue(requestData)
    println("The EPN value: "+executorsPerNode)

    // All driver
    // To determine : spark.driver.memory | spark.driver.memoryOverhead | spark.driver.cores
    def driverCores = availableMasterCores.toInt
    println("The Driver Core: "+driverCores)

    def overheadDriverMemory = Math.ceil(availableMasterMemory * 0.1).toInt
    println("The Overhead Driver Memory: "+overheadDriverMemory)

    def driverMemory = availableMasterMemory - overheadDriverMemory
    println("The Driver Memory: "+driverMemory)

    // All executors
    // To determine : spark.executor.instances | spark.executor.memoryOverhead | spark.executor.memory | spark.executor.cores | spark.default.parallelism
    def totalInstances = executorsPerNode*workerNodes.toInt
    println("The Total Number of Instances: "+totalInstances)

    def totalMemoryPerExecutor = Math.floor(availableWorkerMemory.toInt / executorsPerNode).toInt
    println("The Total Memory Per Executor: "+totalMemoryPerExecutor)

    def overheadMemoryPerExecutor = Math.ceil(totalMemoryPerExecutor * 0.1).toInt
    println("The Overhead Memory Per Executor: "+overheadMemoryPerExecutor)

    def memoryPerExecutor = totalMemoryPerExecutor - overheadMemoryPerExecutor
    println("The Memory Per Executor: "+memoryPerExecutor.toString)

    def coresPerExecutor =  Math.floor(availableWorkerCores.toInt / executorsPerNode).toInt
    println("The Cores Per Executor: "+coresPerExecutor)

    def parallelism = (workerNodes.toInt*executorsPerNode)*(coresPerExecutor*parallelismPerCore)
    println("The Default Parallelism: "+parallelism)
    (driverCores, overheadDriverMemory, driverMemory, totalInstances, overheadMemoryPerExecutor, memoryPerExecutor, coresPerExecutor, parallelism)
  }
}
