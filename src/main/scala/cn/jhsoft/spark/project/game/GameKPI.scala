package cn.jhsoft.spark.project.game

import cn.jhsoft.spark.demo.stream.LoggerLevels
import cn.jhsoft.spark.project.utils.{FilterUtils, TimeUtils}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by chen on 2017/8/3.
  *
  * 游戏数据，日活，留存等数据
  *
  */
object GameKPI {

  def main(args: Array[String]): Unit = {

    LoggerLevels.setStreamingLogLevels()

    // 查询从2-2日0点到2-3日0点的数据
    val queryTime = "2016-02-02 00:00:00"
    val beginTime = TimeUtils(queryTime)
    val endTime = TimeUtils.getCertainDayTime(+1)

    // 两个线程，可以用*，代表电脑有几个核就起几个线程
    val conf = new SparkConf().setAppName("GameKPI").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //切分之后的数据
    val splitedLogs = sc.textFile("/Users/chen/java/spark/shell/es/log/GameLog.txt").map(_.split("\\|"))
    // 过滤出当天的所有操作数据 并缓冲
    val filteredLogs = splitedLogs.filter(fields => FilterUtils.filterByTime(fields, beginTime, endTime))
      .cache()

    // 日新增用户数，Daily New Users 缩写 DNU,统计的那天注册用户数
    val dnu = filteredLogs.filter(fileds => FilterUtils.filterByType(fileds, EventType.REGISTER)).count()

    // 日活跃用户数 DAU （Daily Active Users）,要过滤出重复登录的和又注册了又登录了的
    val dau = filteredLogs.filter(fileds => FilterUtils.filterByTypes(fileds, EventType.REGISTER, EventType.LOGIN))
      .map(_(3)).distinct().count()

    //  留存率：某段时间的新增用户数记为A，经过一段时间后，仍然使用的用户占新增用户A的比例即为留存率
    //  日新增用户在+1日登陆的用户占新增用户的比例
    val _beginTime = TimeUtils.getCertainDayTime(-1)
    val _endTime = beginTime
    // 前一天注册用户数
    val lastDayRegUser = splitedLogs.filter(fields => FilterUtils.filterByTypeAndTime(fields, EventType.REGISTER, _beginTime, _endTime))
      .map(x=>(x(3), 1))
    // 第二天登录用户数
    val todayLoginUser = filteredLogs.filter(fields => FilterUtils.filterByType(fields, EventType.LOGIN))
      .map(x=>(x(3), 1)).distinct()

    // 留存用户数，就是对上面这两个map进行join
    val d1r : Double = lastDayRegUser.join(todayLoginUser).count()
    // 留存用户率
    val d1rr = d1r / lastDayRegUser.count()

    println(d1r)
    println(d1rr)

    // 汇总后，在driver端 把数据写入Mysql或Redis
    // 需要汇聚 则在Driver写。如果不需要汇聚只是简单的处理如加标签加字段等，则可以在Worker机器的Executer里写数据

    sc.stop()
  }

}
