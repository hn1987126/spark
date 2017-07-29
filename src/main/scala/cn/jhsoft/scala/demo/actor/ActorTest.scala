//package cn.jhsoft.scala.demo.actor
//
//import scala.actors.Actor
//
///**
//  * Created by chen on 2017/7/27.
//  * 多线程
//  * 2.10.x版本有。再之后就没有 Actor 了。
//  * 继承Actor需要实现 act方法，在外面调用他的start方法。
//  */
//object MyActor1 extends Actor{
//  override def act(): Unit = {
//    for (i<- 1 to 10){
//      println("actor-1-"+i)
//      Thread.sleep(1000)
//    }
//  }
//}
//
//object MyActor2 extends Actor{
//  override def act(): Unit = {
//    for (i <- 1 to 10){
//      println("actor-2-"+i)
//      Thread.sleep(1000)
//    }
//  }
//}
//
//// 启动两个线程，并行执行，调他们的start方法，实际上会执行 act
//object ActorTest extends App{
//
//  MyActor1.start()
//  MyActor2.start()
//
//}