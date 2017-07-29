package cn.jhsoft.scala.rpc

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._

/**
  * Created by chen on 2017/7/27.
  */
class Worker(var masterHost : String, var masterPort : Int) extends Actor{

  var master:ActorSelection = _

  var memory : Int = 0
  var cores : Int = 0
  val workerId = UUID.randomUUID().toString
  // 10秒检查一次心跳
  val HEART_INTERVAL = 10000

  def this(masterHost : String, masterPort : Int, memory : Int, cores : Int){
    this(masterHost, masterPort)
    this.memory = memory
    this.cores = cores
  }

  override def preStart(): Unit = {
    master = context actorSelection s"akka.tcp://MasterSystem@$masterHost:$masterPort/user/Master"
    master ! RegisterWorker(workerId, memory, cores)
  }

  override def receive: Receive = {
    // 自己注册了以后，master给自己的反馈
    case RegisteredWorker(masterUrl) => {
      println(masterUrl)

      //启动定时器发送心跳
      import context.dispatcher
      //多长时间后执行 单位,多长时间执行一次 单位, 消息的接受者(直接给master发不好, 先给自己发送消息, 自己那可以做逻辑处理， 什么情况下再给master发送消息), 信息
      // 4个参数的意思是，什么时候开始发，间隔多长时间发，给谁发，发的消息内容是什么
      context.system.scheduler.schedule(0 millis, HEART_INTERVAL millis, self, SendHeartbeat)
    }
      // 接收自己的心跳，并发给master
    case SendHeartbeat => {
      println("send heartbeat to master")
      master ! Heartbeat(workerId)
    }
  }
}


object Worker{

  def main(args: Array[String]): Unit = {
    val host = args(0)
    val port = args(1).toInt
    val masterHost = args(2)
    val masterPort = args(3).toInt
    val memory = args(4).toInt
    val cores = args(5).toInt

    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val actorSystem = ActorSystem("WorkerSystem", config)

    // 执行actorOf的时候，会调用 class Worker 里的 preStart方法和 receive 方法
    actorSystem.actorOf(Props(new Worker(masterHost, masterPort, memory, cores)), "Worker")
    actorSystem.awaitTermination()
  }

}