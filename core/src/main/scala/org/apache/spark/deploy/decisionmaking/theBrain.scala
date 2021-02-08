package org.apache.spark.deploy.decisionmaking

import org.apache.spark.deploy.SparkSubmitArguments

class theBrain {

  def theBrainAnalyzing(args: SparkSubmitArguments): Unit = {
    // Memory Overhead Coefficient (0.1):
    // Percentage of memory in each executor that will be reserved for spark.yarn.executor.memoryOverhead
    val memoryOverheadCoefficient = 0.1
    if(memoryOverheadCoefficient>1){

    }
    // Executor Memory Upper Bound (64GB):
    // The upper bound for executor memory. Each executor runs on its own JVM. Upwards of 64GB of memory and grabage collection issues can cause slowness
    val executorMemoryUpperBound = "64"
    if (args.executorMemory > executorMemoryUpperBound){
      // set executor_memory = 64
    }
    // Executor Core Upper Bound (5):
    // The upper bound for cores per executor. More than 5 cores per executor can degrade HDFS I/O throughput. I believe this value can safely be increased if writing to a web-based "file system" such as S3, but significant increases to this limit are not recommended
    val executorCoreUpperBound = "5"
    if (args.executorCores > executorCoreUpperBound){
      // set executor_core = 4
    }
    // OS Reserved Cores (1):
    // Cores per machine to reserve for OS processes. Should be zero if only a percentage of the machine's core were made available to Spark (ie; entered in the Cores Per Node field above)
    val osReservedCores = 1

    // OS Reserved Memory (1GB):
    // The amount of RAM per machine to reserve for OS processes. Should be zero if only a percentage of the machine's RAM was made available to Spark (ie; entered in the Memory Per Node field above)
    val osReservedMemory = 1

    // Parallelism Per Core (2):
    // The level of parallelism per allocated core. This field is used to determine the spark.default.parallelism setting. Generally recommended setting for this value is double the number of cores
    val parallelismPerCore = 2

    // local mode rules
    /**
     * Note: Cores Per Node and Memory Per Node could also be used to optimize Spark for local mode.
     * If your local machine has 8 cores and 16 GB of RAM and you want to allocate 75% of your resources
     * to running a Spark job, setting Cores Per Node and Memory Per Node to 6 and 12 respectively will
     * give you optimal settings. You would also want to zero out the OS Reserved settings. If Spark is
     * limited to using only a portion of your system, there is no need to set aside resources specifically
     * for the OS.
     */
  }

}
