package org.apache.spark.deploy.update

import org.apache.spark.SparkConf
import org.apache.spark.deploy.SparkSubmit.{KUBERNETES, LOCAL, MESOS, STANDALONE, YARN}
import org.apache.spark.deploy.SparkSubmitArguments
import org.apache.spark.deploy.decisionmaking.{CalculateUtil, RequestData}

import scala.collection.mutable.ArrayBuffer

class UpdateUtil {

  def updateForce(_conf: SparkConf, clusterManager: Int, requestData: RequestData ): SparkConf ={
    val recalculate = new CalculateUtil()
    val (driverCores, overheadDriverMemory, driverMemory, totalInstances, overheadMemoryPerExecutor, memoryPerExecutor, coresPerExecutor, parallelism) = recalculate.calulateValue(requestData)

    // Assume we have different mode - client, cluster, yarn-client, yarn-cluster
    if (clusterManager == STANDALONE || clusterManager == YARN || clusterManager == MESOS || clusterManager == KUBERNETES){
      //set ...
      println("the brain check if clustermanager == standalone, yarn, mesos, kubernetes")
      _conf.set("spark.driver.core", driverCores.toString)
        .set("spark.driver.memoryOverhead", overheadDriverMemory.toString+"g")
        .set("spark.driver.memory",driverMemory.toString+"g")
        .set("spark.executor.instances", totalInstances.toString)
        .set("spark.executor.memoryOverhead",overheadMemoryPerExecutor+"g")
        .set("spark.executor.memory", memoryPerExecutor+"g")
        .set("spark.executor.cores", coresPerExecutor.toString)
        .set("spark.default.parallelism", parallelism.toString)
    }
    if (clusterManager == LOCAL){
      println("[CUYATEST] masuk clustermanager == local")
      //args.driverCores = "3"
      _conf.set("spark.driver.cores", "2")
    }
    // local mode rules
    /**
     * Note: Cores Per Node and Memory Per Node could also be used to optimize Spark for local mode.
     * If your local machine has 8 cores and 16 GB of RAM and you want to allocate 75% of your resources
     * to running a Spark job, setting Cores Per Node and Memory Per Node to 6 and 12 respectively will
     * give you optimal settings. You would also want to zero out the OS Reserved settings. If Spark is
     * limited to using only a portion of your system, there is no need to set aside resources specifically
     * for the OS.
     */

    return _conf
  }

  def updateAnalyzer(args:SparkSubmitArguments)={
    println("[CUYATEST] masuk updateAnalyzer")

    val updatedArgs = new ArrayBuffer[String]()
    //val sparkConf = args.toSparkConf()

    //args.driverMemory = "1g"
    //args.executorCores = "3"
    //sparkConf.set("spark.driver.cores", "2")
    //sparkConf.setMaster("local[2]")

    args
  }
}
