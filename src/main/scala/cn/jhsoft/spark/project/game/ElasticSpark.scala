package cn.jhsoft.spark.project.game

import cn.jhsoft.spark.demo.stream.LoggerLevels
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark._

/**
  * Created by chen on 2017/8/3.
  */
object ElasticSpark {

  def main(args: Array[String]) {

    LoggerLevels.setStreamingLogLevels()

    val conf = new SparkConf().setAppName("ElasticSpark").setMaster("local")
    conf.set("es.nodes", "s1,s2")
    conf.set("es.port", "9200")
    conf.set("es.index.auto.create", "true")
    val sc = new SparkContext(conf)
    //val query: String = "{\"query\":{\"match_all\":{}}}"
    val start = 48.99
    val end = 50.99

    val tp = "Tong"
    val query: String = s"""{
       "query": {"match_all": {}},
       "filter" : {
          "bool": {
            "must": [
                {"term" : {"name.last" : "Tong"}},
                {
                "range": {
                  "price": {
                  "gte": "$start",
                  "lte": "$end"
                  }
                }
              }
            ]
          }
       }
     }"""
    val rdd1 = sc.esRDD("store", query)

    println(rdd1.collect().toBuffer)
    println(rdd1.collect().size)

    sc.stop()
  }
}
