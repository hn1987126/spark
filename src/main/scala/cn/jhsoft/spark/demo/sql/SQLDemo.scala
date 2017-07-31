package cn.jhsoft.spark.demo.sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
  * Created by chenyi9 on 2017/7/31.
  */
object SQLDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SQLDemo")//.setMaster("local[3]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    // 设置hadoop的启动用户
    System.setProperty("HADOOP_USER_NAME", "hadoop");

    val personRdd = sc.textFile("hdfs://s1:9000/wordcount/input/1.txt").map(line => {
      val filed = line.split(",")
      Person(filed(0).toLong, filed(1), filed(2).toInt)
    })

    // 隐式转换
    import sqlContext.implicits._
    val personDF = personRdd.toDF()
    personDF.registerTempTable("t_person")

    sqlContext.sql("select * from t_person where age>40").show()

    sc.stop()

  }

}

//case class一定要放到外面
case class Person(id:Long, name:String, age:Int)

