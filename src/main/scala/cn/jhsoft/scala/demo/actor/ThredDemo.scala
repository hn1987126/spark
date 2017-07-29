package cn.jhsoft.scala.demo.actor

import java.util.concurrent.{Callable, Executors, Future}

/**
  * Created by chen on 2017/7/27.
  */
object ThredDemo {

  def main(args: Array[String]): Unit = {
    val pool = Executors.newFixedThreadPool(5)
    for (i<- 1 to 10){
      pool.execute(new Runnable {
        override def run(): Unit = {
          println(Thread.currentThread().getName)
          Thread.sleep(1000)
        }
      })
    }


    // 下面的方式能获取到线程的结果和状态。
    val f:Future[Int] = pool.submit(new Callable[Int] {

      override def call(): Int = {
        Thread.sleep(1000)
        100
      }

    })
    println(f.isDone)
    if(!f.isDone)
      Thread.sleep(3000)

    println(f.isDone)
    if(f.isDone)
      println(f.get)
    else
      Thread.sleep(10000)

    println(f.isDone)
    println(f.get)
  }
}
