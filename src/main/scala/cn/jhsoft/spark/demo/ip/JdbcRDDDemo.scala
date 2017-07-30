package cn.jhsoft.spark.demo.ip

import java.sql.DriverManager

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by chen on 2017/7/30.
  */
object JdbcRDDDemo {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("JdbcRDDDemo").setMaster("local[2]")
    val sc = new SparkContext(conf)

    // 下面两种方式是一样的。
//    def connection() = {
    // 定义函数，输入参数是空的，就是不需要输入，返回 Connection
    val connection = () => {
      Class.forName("com.mysql.jdbc.Driver").newInstance()
      DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata", "root", "123456")
    }

    // 6,10,2   1和4是 sql语句里的两个参数，最后的2，是代表2个worker里的execute来同时读数据。也就是几个Partitions,起几个进程来读
    // rs是返回的字段的ResultSet，jdbcRDD里再返回一个 元组Map。 getString是从 1 下标开始的
    val jdbcRDD = new JdbcRDD(
      sc,
      connection,
      "SELECT * FROM location_info where id >= ? AND id <= ?",
      6, 10, 2,
      rs => {
        val str = rs.getString(2)
        val count = rs.getString(3)
        (str, count)
      }
    )
    val jrdd = jdbcRDD.collect()
    println(jdbcRDD.collect().toBuffer)
    sc.stop()
  }

}
