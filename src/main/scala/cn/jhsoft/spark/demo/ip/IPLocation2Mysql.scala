package cn.jhsoft.spark.demo.ip

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by chen on 2017/7/30.
  */
/*

drop database bigdata;
CREATE DATABASE IF NOT EXISTS bigdata default charset utf8 COLLATE utf8_general_ci;
use bigdata;
DROP TABLE IF EXISTS `location_info`;
CREATE TABLE `location_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `location` varchar(45) DEFAULT NULL,
  `counts` bigint(20) DEFAULT NULL,
  `accesse_date` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

* */
object IPLocation2Mysql {

  // 写mysql
  val data2MySQL = (iterator: Iterator[(String, Int)]) => {
    var conn: Connection = null
    var ps : PreparedStatement = null
    val sql = "INSERT INTO location_info (location, counts, accesse_date) VALUES (?, ?, ?)"
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata?characterEncoding=utf8", "root", "123456")
      iterator.foreach(line => {
        ps = conn.prepareStatement(sql)
        ps.setString(1, line._1)
        ps.setLong(2, line._2)
        ps.setLong(3, System.currentTimeMillis())
        ps.executeUpdate()
      })
    } catch {
      case e: Exception => println("Mysql Exception")
    } finally {
      if (ps != null)
        ps.close()
      if (conn != null)
        conn.close()
    }
  }

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

    // 写入mysql，一个分区调一次，相当于只要建立几次链接。
    // _ 代表一个分区的迭代器。也可以省掉  如：result.foreachPartition(data2MySQL
    result.foreachPartition(data2MySQL(_))

    //println(result.collect().toBuffer)

    sc.stop()

  }
}
