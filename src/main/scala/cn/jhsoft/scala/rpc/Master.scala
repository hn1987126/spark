package cn.jhsoft.scala.rpc

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.duration._

/**
  * Created by chen on 2017/7/27.
  */
class Master(val masterHost : String, val masterPort : Int) extends Actor{

  // 存储已注册 worker 的Map
  var workerMap = new mutable.HashMap[String, WorkerInfo]()
  // 定义一个set，只存WorkInfo，用于以后根据里面的内存，cpu核等做排序，而且也适合做删除
  var workerInfoSet = new mutable.HashSet[WorkerInfo]()
  // Master多长时间去检查Worker的心跳
  val CHECK_INTERVAL = 15000

  override def preStart(): Unit = {
    println("master preStart invoked")
    // Master需要自己启动一个定时器，每隔一段时间去杀掉worker列表里那些 心跳时间超时的，也就是超过指定时间还没来报心跳的程序
    import context.dispatcher
    context.system.scheduler.schedule(0 millis, CHECK_INTERVAL millis, self, CheckTimeOutWorker)
  }

  override def receive: Receive = {

    // 注册worker
    case RegisterWorker(workerId, memory, cores)=>{
      //判断一下，是不是已经注册过
      if (!workerMap.contains(workerId)){
        val workerInfo = new WorkerInfo(workerId, memory, cores)
        workerMap(workerId) = workerInfo
        workerInfoSet += workerInfo
        sender ! RegisteredWorker(s"akka.tcp://MasterSystem@$masterHost:$masterPort/user/Master")
      }
    }
    // 接收worker发来的心跳，记录每个worker最后心跳的时间，这个名词叫   报活
    case Heartbeat(workerId) => {
      if (workerMap.contains(workerId)){
        val workerInfo = workerMap(workerId)
        // 当前时间
        val currentTime = System.currentTimeMillis()
        workerInfo.lastHeartbeatTime = currentTime
      }
    }
    // Master自己给自己发的定时消息，用于去检查Worker的心跳情况，把超时的杀了
    case CheckTimeOutWorker => {
      // 当前时间
      val currentTime = System.currentTimeMillis()
      // 过滤出来 超时的 worker
      val dieWorker = workerInfoSet.filter(x=>currentTime - x.lastHeartbeatTime > CHECK_INTERVAL)
      for (w <- dieWorker){
        workerInfoSet -= w
        workerMap -= w.workerId
      }
      println(workerInfoSet.size)
    }
  }
}



object Master{
  def main(args: Array[String]): Unit = {

    val host = args(0)
    val port = args(1).toInt
    val configStr = s"""
                   |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
                   |akka.remote.netty.tcp.hostname = "$host"
                   |akka.remote.netty.tcp.port = "$port"
      """.stripMargin
    val config = ConfigFactory.parseString(configStr)

    // ActorSystem老大，辅助创建和监控下面的Actor，他是单例的
    val actorSystem = ActorSystem("MasterSystem", config)

    // 创建Actor
    val master = actorSystem.actorOf(Props(new Master(host, port)), "Master")
    // 等待信号，优雅退出
    actorSystem.awaitTermination()
  }
}
