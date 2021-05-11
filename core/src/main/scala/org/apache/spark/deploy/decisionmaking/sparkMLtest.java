//package org.apache.spark.deploy.decisionmaking;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.mllib.regression.LabeledPoint;
//
//import java.io.IOException;
//
//public class sparkMLtest {
//    public static void main(String[] args) throws IOException{
//        sparkMLtest demo = new sparkMLtest();
//        JavaSparkContext sc = demo.createSparkContext();
//        // Data preparation
//        String inputFile = "testGeneratexx4.csv";
//        JavaRDD<LabeledPoint> parsedData = loadDataFromFileAndDataPreparation(sc, inputFile);
//    }
//
//    public JavaSparkContext createSparkContext() {
//        SparkConf conf = new SparkConf().setAppName("Main")
//                .setMaster("local[2]")
//                .set("spark.executor.memory", "3g")
//                .set("spark.driver.memory", "3g");
//
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        return sc;
//    }
//
//    private static JavaRDD<LabeledPoint> loadDataFromFileAndDataPreparation(JavaSparkContext sc, String inputFile) throws IOException{
//    }
//}
