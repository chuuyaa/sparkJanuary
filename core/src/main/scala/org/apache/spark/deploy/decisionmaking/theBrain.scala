package org.apache.spark.deploy.decisionmaking

import org.apache.spark.SparkConf
import org.apache.spark.deploy.SparkSubmit.{KUBERNETES, LOCAL, MESOS, STANDALONE, YARN}
import org.apache.spark.deploy.SparkSubmitArguments
import org.apache.spark.internal.config.SUBMIT_DEPLOY_MODE

class theBrain {
//  private var _conf: SparkConf = _
//
//  //def master: String = _conf.get("spark.master")
//  //def deployMode: String = _conf.get(SUBMIT_DEPLOY_MODE)
//  //def appName: String = _conf.get("spark.app.name")
//  _conf.set("spark.app.id", "the Brain")

  def theBrainAnalyzing(_conf: SparkConf, clusterManager: Int) : SparkConf={
    println("[CUYATEST] masuk theBRainAnalyzing")

    /**
   * input configurations hardcoded, or given
   */
  // local, yarn, mesos except standalone which has no master and slave (should use different case check)
  val master:String = null
  val masterMemory: String = "30"
  val masterCores: String = "16"
  val workerNodes: String = "2"
  val workerMemoryPerNode: String = "14"
  val workerCoresPerNode: String = "8"

  /**
    * Availability configuration after reserved according to rules, except for standalon cases that has only 1 cores or 1 GB memory (different check cases)
    */

  val availableMasterMemory:Int = masterMemory.toInt - 1
  val availableMasterCores:Int = masterCores.toInt - 1
  val availableWorkerMemory:Int = workerMemoryPerNode.toInt - 1
  val availableWorkerCores:Int = workerCoresPerNode.toInt - 1

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
  /**
   * given/set by user or we set it up according to several check cases
   * by default = executorCoreUpperBound
   * @return
   */

  // how many cases to check before choosing the executors
    var a = 1
    var b = 1
    var unusedMemoryPerNode = 0
    var unusedCoresPerNode = 0
    var executorsPerNode = 0

    do{
        executorsPerNode = executorsPerNode + 1
        a = availableWorkerMemory/executorsPerNode
        b = availableWorkerCores/executorsPerNode
        unusedMemoryPerNode = availableWorkerMemory-(executorsPerNode*totalMemoryPerExecutor)
        unusedCoresPerNode = availableWorkerCores - (executorsPerNode*coresPerExecutor)
      println(a, b, unusedMemoryPerNode, unusedCoresPerNode, executorsPerNode)
    }while(a > executorMemoryUpperBound.toInt || b > executorCoreUpperBound.toInt || unusedMemoryPerNode != 1 && unusedCoresPerNode != 1)


    println("Selected Executors Per Node: " +executorsPerNode)

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



    // Assume we know the hardware/system properties
    // 1. CPU Cores
    /**
     * Spark scales well to 10 of CPU cores per machine since it performs minimal sharing between threads
     * You should likely provision at least 8-16 cores per machine
     * Case 1: CPU cost are depending on your workload. Cpu-intensive application require more
     * Case 2: Most application are either CPU or network-bound once the data is in memory (bigger data needs more Cpu cores)
     */
    val processorCores = "4"
    // 2. Network
    /**
     * When data is in memory, most spark app are network-bound.
     * Using 10 Gigabit or higher network is the best way to make these applications faster,
     * esp true for "distributed reduce" applications (group-bys, reduce-bys, SQL joins)
     * higher network required to allow smoother spark shuffling the data across the networks
     */
    val network = "10Gigabit"
    // 3. Memory
    /**
     * Spark can run well with anywhere from 8GB to 100GB of memory per machine.
     * In all cases, we recommend allocating only at most 75% of the memory for Spark (the rest are for OS and buffer cache)
     * Memory needed are depending on your application (according to dataset size) - load part of your dataset in a Spark RDD and use the Storage tab of Spark’s monitoring UI (http://<driver-node>:4040) to see its size in memory
     * Memory usage is greatly affected by storage level and serialization format.
     * If you have more than 200GB RAM, you can launch multiple executors in a single node.
     * In spark standalone mode, a worker is responsible for launching multiple executors according to its available memory and cores,
     * and each executor will be launched in a separate Java VM
     */
    val TotalMemory = "16GB"
    val availableMemory = "6GB"
    // 4. Local Disks
    /**
     * Spark can perform a lot of its computation in memory, it still uses local disks to store data that doesn't fit in RAM,
     * as well as to preserve intermediate output between stages.
     * Recommended having 4-8 disks per node, configured without RAID (separate mount points)
     */
    val totalDisks = "10GB"
    // 5. Storage Systems
    /**
     * Spark jobs that require to read input data from an external storage system (HDFS, HBase)
     * it is important to place it as close to this system as possible.
     * * run spark on the same nodes as HDFS
     * * run Hadoop and Spark on a common cluster manager like Mesos or Hadoop YARN
     * * run Spark on different nodes in the same local-area network as HDFS
     * * For low-latency data stores like HBase, it may be preferable to run computing jobs on different nodes than the storage system to avoid interference
     */
      val dataStore = "HDFS"


    // Assume we have different mode - client, cluster, yarn-client, yarn-cluster
    if (clusterManager == STANDALONE || clusterManager == YARN || clusterManager == MESOS || clusterManager == KUBERNETES){
      //set ...
      println("the brain check if clustermanager == standalone, yarn, mesos, kubernetes")
      _conf.set("spark.driver.core", driverCores.toString)
        .set("spark.driver.memoryOverhead", overheadDriverMemory.toString+"g")
        .set("spark.driver.memory",driverMemory.toString+"g")
        .set("spark.executor.instances", (executorsPerNode*workerNodes.toInt).toString)
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
    _conf
  }

}
