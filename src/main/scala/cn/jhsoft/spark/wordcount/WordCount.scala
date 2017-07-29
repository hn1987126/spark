package cn.jhsoft.spark.wordcount

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by chen on 2017/7/29.
  */
object WordCount {

  def main(args: Array[String]): Unit = {
    val config = new SparkConf().setAppName("WC")
    val sc = new SparkContext(config)
    sc.textFile(args(0)).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).sortBy(_._2, false)
    sc.stop()
  }

}
