package org.apache.spark.deploy.collect

//import org.apache.spark.sql._
//import org.apache.spark.sql.SparkSession
//import org.apache.spark.rdd.RDD
//import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

class ArgumentAnalyzer {

  //val spark = SparkSession.builder().appName("Cuya create database").config("spark.some.config.option", "some-value").getOrCreate()
  //import spark.implicits._


  def analyzeArgument(master: String,
                      deployMode : String,
                      executorMemory:String,
                      executorCore : String ) : Unit={
    println("Berjaya masuk master : "+master)
    println("Berjaya masuk deploy mode : "+deployMode)
    println("Berjaya masuk executor memory : "+executorMemory)
    println("Berjaya masuk executor core : "+executorCore)

    //  dah siap pass value.
    // then firstly have to perform
    //def  performAnalysisAndAutoConf()
    // then secondly perform update method if needed to change the argument
    //def updateArgument(master, deployMode, executorMemory,executorCore)
  }
//    def analyzeArgument(master: String,
//                        deployMode : String,
//                        executorMemory:String,
//                        executorCore : String ) : Unit={
//
//      // Starting a Spark Session
//      val spark = SparkSession.builder().appName("Cuya create database").config("spark.some.config.option", "some-value").getOrCreate()
//      import spark.implicits._
//
//      // Creating case class
//      case class Param(master: String, deployMode: String, theClass: String, name: String, jars: String,
//                       packages: String, repositories: String, conf: String, properties: String,
//                       driverMemory:String, executorMemory:String, proxyUser:String, driverCores:Int,
//                       supervise:String, kill:String, status:String, totalExecutorCores:Int, executorCores:Int,
//                       queue:String, numExecutors:Int, archives:String, args:Int, theJar:String)
//
//      // Defining Param schema
//      def parseParam(str: String): Param={
//        val line = str.split(",")
//        Param(line(0), line(1), line(2), line(3), line(4), line(5), line(6), line(7), line(8), line(9), line(10), line(11),line(12).toInt, line(13), line(14), line(15), line(16).toInt, line(17).toInt, line(18), line(19).toInt, line(20), line(21).toInt, line(22))
//      }
//
//      // Defining parseRDD
//      def parseRDD(rdd: RDD[String]): RDD[Param]={
//        val header = rdd.first
//        rdd.filter(_(0) != header(0)).map(parseParam).cache()
//      }
//
//      // Reading dataOne.csv into paramsDF dataframe
//      //val paramsDF = parseRDD(spark.sparkContext.textFile("C:\\PHDImplementation\\dataOne.csv")).toDF.cache()
//
//      // the other workaround
//      val pes = spark.read.option("header",true).csv("C:\\PHDImplementation\\dataOne.csv").toDF.cache()
//      pes.registerTempTable("testable")
//      spark.sql("show databases").show
//      spark.sql("show tables").show
//
//      // display the table
//      pes.select("master", "name").show
//
//      var newdf = spark.sql("SELECT master, name, deployMode FROM testable")
//      newdf.show
//
//      // Try insert data into the existing table
//      val secDF = Seq(("C", "Company3"), ("D", "Company4"))
//        .toDF("master", "name")
//
//      secDF.write.mode(SaveMode.Append).insertInto("testable")
//      setupDatabase()
//      //insertDatabase()
//    }
}