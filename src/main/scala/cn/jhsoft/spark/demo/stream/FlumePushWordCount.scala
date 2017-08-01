package cn.jhsoft.spark.demo.stream

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.flume.FlumeUtils

/**
  * Created by chen on 2017/8/1.
  * flume push数据到本程序，很少用
  */
object FlumePushWordCount {

  def main(args: Array[String]): Unit = {

    LoggerLevels.setStreamingLogLevels()
    val conf = new SparkConf().setAppName("FlumeWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))

    //推送方式: flume向spark发送数据，192.168.31.139 为本机ip，也就是Worker的Excuter这个机器的IP
    val flumeStream = FlumeUtils.createStream(ssc, "192.168.31.139", 8888)
    //flume中的数据通过event.getBody()才能拿到真正的内容
    val words = flumeStream.flatMap(x => new String(x.event.getBody().array()).split(" ")).map((_, 1))

    val results = words.reduceByKey(_ + _)
    results.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
