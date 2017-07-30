package cn.jhsoft.spark.demo.wordcount

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by chen on 2017/7/30.
  */
object UserLocation {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("MoblieLocation").setMaster("local[2]")
    val sc = new SparkContext(conf)
    // 获取资源文件路径
    //println(this.getClass().getResource("/userlocal/bs_log"))

    val rdd1 = sc.textFile(this.getClass().getResource("/logfile/userlocal/bs_log").toString).map(x => {
      val arr = x.split(",")
      val mb = (arr(0),arr(2))
      val flag = arr(3)
      var time = arr(1).toLong
      // flag为1是代表来上班的时间，0代表离开的时间，这样他们相减即可得到停留的时间
      if (flag == "1") time = -time
      (mb, time)
    })
    // 得到每个人在每个基站停留的时间
    val rdd2 = rdd1.reduceByKey(_+_)

    val rdd3 = sc.textFile(this.getClass().getResource("/logfile/userlocal/loc_info.txt").toString).map(x => {
      val arr = x.split(",")
      val bs = arr(0)
      (bs, (arr(1), arr(2)))
    })

    val rdd4 = rdd2.map(t => (t._1._2, (t._1._1, t._2)))
    val rdd5 = rdd4.join(rdd3)

    val rdd6 = rdd2.map(t => (t._1._1, t._1._2, t._2)).groupBy(_._1).values.map(it => {
      it.toList.sortBy(_._3).reverse
    })
    println(rdd6.collect.toBuffer)

    sc.stop()
  }
}
