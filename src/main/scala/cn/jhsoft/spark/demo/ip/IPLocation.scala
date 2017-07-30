package cn.jhsoft.spark.demo.ip

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by chen on 2017/7/30.
  */
object IPLocation {

  // ip字符串转数字
  def ip2Long(ip: String): Long = {
    val fragments = ip.split("[.]")
    var ipNum = 0L
    for (i <- 0 until fragments.length){
      ipNum =  fragments(i).toLong | ipNum << 8L
    }
    ipNum
  }

  // 二分法查找
  def binarySearch(lines: Array[(String, String, String)], ip: Long) : Int = {
    var low = 0
    var high = lines.length - 1
    while (low <= high) {
      val middle = (low + high) / 2
      if ((ip >= lines(middle)._1.toLong) && (ip <= lines(middle)._2.toLong))
        return middle
      if (ip < lines(middle)._1.toLong)
        high = middle - 1
      else {
        low = middle + 1
      }
    }
    -1
  }

  def main(args: Array[String]) {

    val conf = new SparkConf().setMaster("local[2]").setAppName("IPLocation")
    val sc = new SparkContext(conf)

    val ipRulesRdd = sc.textFile("/wordcount/sparkinput/ip.txt").map(line =>{
      val fields = line.split("\\|")
      val start_num = fields(2)
      val end_num = fields(3)
      val province = fields(6)
      (start_num, end_num, province)
    })
    //全部的ip映射规则
    val ipRulesArrary = ipRulesRdd.collect()

    //广播规则，别的机器上的worker要用
    val ipRulesBroadcast = sc.broadcast(ipRulesArrary)

    //加载要处理的数据
    val ipsRDD = sc.textFile("/wordcount/sparkinput/access.log").map(line => {
      val fields = line.split("\\|")
      fields(1)
    })

    val result = ipsRDD.map(ip => {
      val ipNum = ip2Long(ip)
      val index = binarySearch(ipRulesBroadcast.value, ipNum)
      val info = ipRulesBroadcast.value(index)
      info
    }).map(t=>(t._3, 1)).reduceByKey(_+_).sortBy(t=>t._2, false)

    println(result.collect().toBuffer)

    sc.stop()

  }
}
