//package cn.jhsoft.scala.demo.actor
//
//import scala.actors.Actor
//
///**
//  * Created by chen on 2017/7/27.
//  */
//class MyActor extends Actor{
//  override def act(): Unit = {
//    while (true){
//      // receive是偏函数
//      receive{
//        case "start"=>{
//          println("Starting...")
//          Thread.sleep(1000)
//          println(Thread.currentThread().getName)
//          println("started")
//        }
//        case "stop"=>{
//          println()
//          println()
//          println()
//          println("Stoping...")
//          Thread.sleep(1000)
//          println(Thread.currentThread().getName)
//          println("stoped")
//        }
//        case "exit"=>{
//          exit()
//        }
//      }
//    }
//  }
//}
//
///**
//  * 这里面，循环用loop，是可以不断的用原来的线程池
//  */
//class YourActor extends Actor{
//  override def act(): Unit = {
//    loop{
//      // react是偏函数，比receive效率更高。
//      react{
//        case "start"=>{
//          println("Starting...")
//          Thread.sleep(1000)
//          println(Thread.currentThread().getName)
//          println("started")
//        }
//        case "stop"=>{
//          println()
//          println()
//          println()
//          println("Stoping...")
//          Thread.sleep(1000)
//          println(Thread.currentThread().getName)
//          println("stoped")
//        }
//        case "exit"=>{
//          // 退出线程
//          println("exiting")
//          exit()
//        }
//      }
//    }
//  }
//}
//
//object MyActor{
//  def main(args: Array[String]): Unit = {
////    val actor = new MyActor
////    actor.start()
////    actor ! "start"
////    // 感叹号是方法  相当于 actor.!("start")
//    // 一个感叹号 表示异步消息没有返回值，2个感叹号是异步消息有返回值，1个感叹号1个问题，是表示同步消息且有返回值
////    actor ! "stop"
////    println("消息发送完成")
//
//
//    val actor1 = new YourActor
//    actor1.start()
//    actor1 !"start"
//    Thread.sleep(5000)
//    actor1 !"exit"
//  }
//}
