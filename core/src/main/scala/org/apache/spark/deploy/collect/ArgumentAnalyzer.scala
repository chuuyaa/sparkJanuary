package org.apache.spark.deploy.collect

import com.sun.management.OperatingSystemMXBean
import org.apache.avro.generic.GenericData.StringType
import org.apache.spark.deploy.SIngleton

import java.lang.management.ManagementFactory
import java.lang.management.OperatingSystemMXBean
import javax.management.MBeanServerConnection
//import org.apache.spark.sql._
//import org.apache.spark.sql.SparkSession
//import org.apache.spark.rdd.RDD
//import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import java.nio.file._
import java.nio.file.Path
import scala.io.Source
import java.lang.Runtime


class ArgumentAnalyzer {

//  def anothermain(): Unit ={
//    val mb = 1024*1024;
//    val gb = 1024*1024*1024;
//    /* PHYSICAL MEMORY USAGE */
//    println("\n**** Sizes in Mega Bytes ****\n");
//    val operatingSystemMXBean = {
//      new OperatingSystemMXBean {
//        ManagementFactory.getOperatingSystemMXBean()
//      }
//    }
//    OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
//    //RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
//    //operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//    com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean)
//    java.lang.management.ManagementFactory.getOperatingSystemMXBean();
//    long physicalMemorySize = os.getTotalPhysicalMemorySize();
//    System.out.println("PHYSICAL MEMORY DETAILS \n");
//    System.out.println("total physical memory : " + physicalMemorySize / mb + "MB ");
//    long physicalfreeMemorySize = os.getFreePhysicalMemorySize();
//    System.out.println("total free physical memory : " + physicalfreeMemorySize / mb + "MB");
//    /* DISC SPACE DETAILS */
//    File diskPartition = new File("C:");
//    File diskPartition1 = new File("D:");
//    File diskPartition2 = new File("E:");
//    long totalCapacity = diskPartition.getTotalSpace() / gb;
//    long totalCapacity1 = diskPartition1.getTotalSpace() / gb;
//    double freePartitionSpace = diskPartition.getFreeSpace() / gb;
//    double freePartitionSpace1 = diskPartition1.getFreeSpace() / gb;
//    double freePartitionSpace2 = diskPartition2.getFreeSpace() / gb;
//    double usablePatitionSpace = diskPartition.getUsableSpace() / gb;
//    System.out.println("\n**** Sizes in Giga Bytes ****\n");
//    System.out.println("DISC SPACE DETAILS \n");
//    //System.out.println("Total C partition size : " + totalCapacity + "GB");
//    //System.out.println("Usable Space : " + usablePatitionSpace + "GB");
//    System.out.println("Free Space in drive C: : " + freePartitionSpace + "GB");
//    System.out.println("Free Space in drive D:  : " + freePartitionSpace1 + "GB");
//    System.out.println("Free Space in drive E: " + freePartitionSpace2 + "GB");
//    if(freePartitionSpace <= totalCapacity%10 || freePartitionSpace1 <= totalCapacity1%10)
//    {
//      System.out.println(" !!!alert!!!!");
//    }
//    else
//      System.out.println("no alert");
//
//    Runtime runtime;
//    byte[] bytes;
//    System.out.println("\n \n**MEMORY DETAILS  ** \n");
//    // Print initial memory usage.
//    runtime = Runtime.getRuntime();
//    printUsage(runtime);
//
//    // Allocate a 1 Megabyte and print memory usage
//    bytes = new byte[1024*1024];
//    printUsage(runtime);
//
//    bytes = null;
//    // Invoke garbage collector to reclaim the allocated memory.
//    runtime.gc();
//
//    // Wait 5 seconds to give garbage collector a chance to run
//    try {
//      Thread.sleep(5000);
//    } catch(InterruptedException e) {
//      e.printStackTrace();
//      return;
//    }
//
//    // Total memory will probably be the same as the second printUsage call,
//    // but the free memory should be about 1 Megabyte larger if garbage
//    // collection kicked in.
//    printUsage(runtime);
//    for(int i = 0; i < 30; i++)
//    {
//      long start = System.nanoTime();
//      // log(start);
//      //number of available processors;
//      int cpuCount = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
//      Random random = new Random(start);
//      int seed = Math.abs(random.nextInt());
//      log("\n \n CPU USAGE DETAILS \n\n");
//      log("Starting Test with " + cpuCount + " CPUs and random number:" + seed);
//      int primes = 10000;
//      //
//      long startCPUTime = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
//      start = System.nanoTime();
//      while(primes != 0)
//      {
//        if(isPrime(seed))
//        {
//          primes--;
//        }
//        seed++;
//
//      }
//      float cpuPercent = calcCPU(startCPUTime, start, cpuCount);
//      log("CPU USAGE : " + cpuPercent + " % ");
//
//
//      try
//        {
//          Thread.sleep(1000);
//        }
//      catch (InterruptedException e) {}
//    }
//
//    try
//      {
//        Thread.sleep(500);
//      }`enter code here`
//    catch (Exception ignored) { }
//  }
//  }
//
//  def main(args: Array[String]): Unit = {
//
//    OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class)
//    MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer()
//    OperatingSystemMXBean osMBean = ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class)
//
//    val nanoBefore = System.nanoTime
//    val cpuBefore = osMBean.getProcessCpuTime
//
//    // Call an expensive task, or sleep if you are monitoring a remote process
//
//    val cpuAfter = osMBean.getProcessCpuTime
//    val nanoAfter = System.nanoTime
//
//    var percent = 0L
//    if (nanoAfter > nanoBefore) percent = ((cpuAfter - cpuBefore) * 100L) / (nanoAfter - nanoBefore)
//    else percent = 0
//
//    System.out.println("Cpu usage: " + percent + "%")
//
//  }

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
  def readSizes(file: String): Int = {
    val path = Paths.get(file)
    val fileSize = Files.size(path)
    val toGB = fileSize / 1024 / 1024 / 1024
    toGB.toInt
  }

  def countLines(file: String): Int ={
    val src = Source.fromFile(file)
    val count = src.getLines.size
    count
  }

  def dataStats(files: String): Unit ={
    println(readSizes(files).toString)
  }

  def readWorkload(workload: String) ={
    // todo what after read the workload(sorting, iteration, graph, mathematics)
  }

  def getHardwareSpec(): Unit ={
    val rt = Runtime.getRuntime()
    val free_memory = rt.freeMemory()
    val max_memory = rt.maxMemory()
    val total_memory = rt.totalMemory()
    val used_memory = total_memory - free_memory
    val processors = rt.availableProcessors()
    println("free memory: " + free_memory + " max memory: " + max_memory + " total memory: " + total_memory + " processors: " +processors)
  }

  def getHardwareSpecFromMaster(host:String, port:String, cores:String): Unit ={
//    val hardwareSpec = Seq(host, port, cores)
//    val hsSchema = List(StructField("host", StringType, true), StructField("port", StringType, true), StructField("cores", StringType, true))
//    val hsDF = spark.
  }
}