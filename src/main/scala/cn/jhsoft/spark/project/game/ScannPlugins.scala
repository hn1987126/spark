package cn.jhsoft.spark.project.game

import cn.jhsoft.spark.demo.stream.LoggerLevels
import cn.jhsoft.spark.project.utils.JedisConnectionPool
import kafka.serializer.StringDecoder
import org.apache.commons.lang3.time.FastDateFormat
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Milliseconds, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by chen on 2017/8/3.
  *
  * 实时监控，以防使用外挂，把使用外挂的人放入Redis中，可以让游戏引擎踢他下线
  *
  */
object ScannPlugins {


  def main(args: Array[String]): Unit = {

    LoggerLevels.setStreamingLogLevels()

    val conf = new SparkConf().setAppName("ScannPlugins").setMaster("local[4]")
    // 序列化提升性能
    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Milliseconds(10000))

    sc.setCheckpointDir("/wordcount/tmp/ck3")

    val Array(zkQuorum, group, topics, numThreads) = Array("s1:2181,s2:2181,s3:2181,s4:2181", "g1", "game-log", "1")
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val kafkaParams = Map[String, String](
      "zookeeper.connect" -> zkQuorum,
      "group.id" -> group,
      "auto.offset.reset" -> "smallest"
    )
    // 从流中取数据
    val dstream = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicMap, StorageLevel.MEMORY_AND_DISK_SER)
    // 由于从kafka中取出来的数据是 k,v格式的，所以这里只需要取v,也就是 _._2
    val lines = dstream.map(_._2)
    val splitedLines = lines.map(_.split("    "))
    // 过滤出 使用 "骨玉权杖" 这个技能的数据，且类型是10
    val filteredLines = splitedLines.filter(x=>{
      val et = x(3)
      val item = x(8)
      et == "10" && item == "骨玉权杖"
    })

    // 窗口函数
    // map里为  (用户, 时间)
    // 30秒为一个窗口，20秒滑动一次。
    val grouedWindow = filteredLines.map(x=>(x(7), dateFormat.parse(x(12)).getTime)).groupByKeyAndWindow(Seconds(30), Seconds(20))
    // 过滤次数小于5的，发生了5次以上才算是外挂，在10秒中作为采集周期中
    val filtered = grouedWindow.filter(_._2.size >= 5)

    // 对比他们的时间,如果相隔太短则视为外挂
    val itemAvgTime = filtered.mapValues(x=>{
      val list = x.toList.sorted
      val size = list.size
      val first = list(0)
      val end = list(size - 1)
      val cha:Double = end - first  // 最后一次时间 - 最先一次时间
      cha / size      // 平均时间
    })

    // 时间小于1秒的是有问题的用户
    val badUser = itemAvgTime.filter(_._2 < 10000)

    badUser.foreachRDD(rdd=>{
      rdd.foreachPartition(it=>{
        val connection = JedisConnectionPool.getConnection()
        it.foreach(t=>{
          val user = t._1
          val avgTime = t._2
          val currentTime = System.currentTimeMillis()
          connection.set(user + "_" + currentTime, avgTime.toString)
        })
        connection.close()
      })
    })

    badUser.print()
    filteredLines.print()

    ssc.start()
    ssc.awaitTermination()

  }

}
