package cn.jhsoft.spark.project.utils

import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}

/**
  * Created by chen on 2017/8/3.
  */
object JedisConnectionPool {

  val config = new JedisPoolConfig()
  //最大连接数,
  config.setMaxTotal(10)
  //最大空闲连接数,
  config.setMaxIdle(5)
  //当调用borrow Object方法时，是否进行有效性检查 -->
  config.setTestOnBorrow(true)
  val pool = new JedisPool(config, "127.0.0.1", 6379)

  def getConnection(): Jedis = {
    pool.getResource

  }

  def main(args: Array[String]) {
    val conn = JedisConnectionPool.getConnection()
    val r = conn.keys("*")
    println(r)
  }

}
