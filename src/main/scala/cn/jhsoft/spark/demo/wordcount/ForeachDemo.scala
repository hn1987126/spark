package cn.jhsoft.spark.demo.wordcount

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by chen on 2017/7/30.
  */
object ForeachDemo {
  def main(args: Array[String]): Unit = {
    // local[1],local[2]等，多个本地线程来跑
    val conf = new SparkConf().setAppName("ForeachDemo").setMaster("local")
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(List(1,2,3,4,5,6,7,8,9), 3)
    rdd1.foreach(println(_))

    val rdd2 = sc.parallelize(List("dog", "salmon", "salmon", "rat", "elephant"), 3)
    val rdd3 = rdd2.keyBy(_.length)
    println(rdd3.collect().toBuffer)

    sc.stop()
  }
}
