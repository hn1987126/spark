package cn.jhsoft.spark.demo.stream

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by chenyi9 on 2017/8/1.
  */
object StreamingWordCount {

  def main(args: Array[String]): Unit = {

    // 设置日志级别
    LoggerLevels.setStreamingLogLevels()

    val conf = new SparkConf().setAppName("StreamingWordCount")
    val sc = new SparkContext(conf)
    val scc = new StreamingContext(sc, Seconds(5))

    // 接收数据
    val ds = scc.socketTextStream("s1", 8888)
    val result = ds.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_)
    result.print()
    scc.start()
    scc.awaitTermination()


  }

}
