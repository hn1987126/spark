package cn.jhsoft.spark.demo.wordcount

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by chen on 2017/7/29.
  */
object WordCount {

  def main(args: Array[String]): Unit = {
    val config = new SparkConf().setAppName("WC")
    val sc = new SparkContext(config)
    sc.textFile(args(0)).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).sortBy(_._2, false).saveAsTextFile(args(1))

    //textFile方法会产生两个RDD，1、HadoopRDD，2、MapPartitionRDD【调用了spark的map方法，把第一步hadoop返回的偏移量去掉了(hadoop返回了，偏移量和行内容)】
    //flatMap 方法会产生一个RDD   MapPartitionRDD
    //map 方法会产生一个RDD   MapPartitionRDD
    //reduceByKey 方法会产生一个RDD   ShuffledRDD
    //saveToTextFile  方法产生一个RDD    MapPartitionRDD


    sc.stop()
  }

}
