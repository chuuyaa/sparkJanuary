package org.apache.spark.deploy.update

import org.apache.spark.SparkConf
import org.apache.spark.deploy.SparkSubmitArguments

import scala.collection.mutable.ArrayBuffer

class UpdateAnalyzer {
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
