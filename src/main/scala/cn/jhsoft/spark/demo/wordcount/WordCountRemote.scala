package cn.jhsoft.spark.demo.wordcount

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by chen on 2017/7/30.
  * 远程调试
  */
object WordCountRemote {

  def main(args: Array[String]): Unit = {

    // "/Users/chen/java/spark/target/spark-1.0.jar" 这个路径字符串 是点右键 Copy Path来的

    val config = new SparkConf().setAppName("WC")
//        .setJars(Array("/Users/chen/java/spark/target/spark-1.0.jar"))
        .setJars(Array("D:\\Java\\spark\\target\\spark-1.0.jar"))
        .setMaster("spark://s1:7077")
    // 设置hadoop的启动用户
    System.setProperty("HADOOP_USER_NAME", "hadoop");

    val sc = new SparkContext(config)
    sc.textFile(args(0)).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).sortBy(_._2, false).saveAsTextFile(args(1))

    //textFile方法会产生两个RDD，1、HadoopRDD，2、MapPartitionRDD【调用了spark的map方法，把第一步hadoop返回的偏移量去掉了(hadoop返回了，偏移量和行内容)】
    //flatMap 方法会产生一个RDD   MapPartitionRDD
    //map 方法会产生一个RDD   MapPartitionRDD
    //reduceByKey 方法会产生一个RDD   ShuffledRDD [下游到上游来摘取数据的过程，那就要等上游的数据处理完才能拉]
    //saveToTextFile  方法产生一个RDD    MapPartitionRDD


    sc.stop()
  }

}
