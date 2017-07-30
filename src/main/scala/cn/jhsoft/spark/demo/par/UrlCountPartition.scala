package cn.jhsoft.spark.demo.par

import java.net.URL

import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}

/**
  * 自定义分区
  */

import scala.collection.mutable

/**
  * Created by chen on 2017/7/30.
  */
object UrlCountPartition {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("UrlCount").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //rdd1将数据切分，元组中放的是（URL， 1）
    val rdd1 = sc.textFile(this.getClass().getResource("/logfile/usercount/itcast.log").toString).map(line => {
      val f = line.split("\t")
      (f(1), 1)
    })
    val rdd2 = rdd1.reduceByKey(_+_)

    val rdd3 = rdd2.map(t => {
      val url = t._1
      val host = new URL(url).getHost
      // 想使用 partitionBy 的话，这里需要k-v形式的
      (host, (url, t._2))
    }).cache()   // cache会将数据缓存到内存当中。cache是一个Transformation,是延时加载的。collect是Action，他一调用就会执行缓存。
    // 缓存会调用persist底层方法。如果要清缓存调用   unpersist(true)。

    // 自定义分区器
    // 去重，得到所有不重复的 host
    val ints = rdd3.map(_._1).distinct().collect()
    val hostPartitioner = new HostPartitioner(ints)
    // mapPartitions 是对每个分区进行操作，参数是iterator，要求返回iterator，里面是做排序操作的
    val rdd4 = rdd3.partitionBy(hostPartitioner).mapPartitions(it => {
      it.toList.sortBy(_._2._2).reverse.take(2).iterator
    })

    // 默认分区器
//    var rdd4 = rdd3.partitionBy(new HashPartitioner(ints.length))

    rdd4.saveAsTextFile("/wordcount/sparkout")

    sc.stop()
  }

}


// 自定义分区器
class HostPartitioner(ins : Array[String]) extends Partitioner{

  // 根据传过来的 host数组，定义一个map，hostname->0, hostname1->1
  val parMap = new mutable.HashMap[String, Int]()
  var count = 0
  for (i<-ins){
    parMap += (i -> count)
    count += 1
  }

  // 共有多少个分区
  override def numPartitions: Int = ins.length

  // 返回分区号
  override def getPartition(key: Any): Int = {
    parMap.getOrElse(key.toString, 0)
  }
}
