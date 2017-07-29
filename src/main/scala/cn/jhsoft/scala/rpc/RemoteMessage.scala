package cn.jhsoft.scala.rpc

/**
  * Created by chenyi9 on 2017/7/28.
  */
trait RemoteMessage extends Serializable

// worker to Master  注册worker
case class RegisterWorker(id : String, memory : Int, cores : Int) extends RemoteMessage

// Master to worker 注册结果反馈
case class RegisteredWorker(masterUrl : String) extends RemoteMessage

// Worker给自己发心跳
case object SendHeartbeat

// Worker给master发心跳信息,id是指worker的 id
case class Heartbeat(id:String) extends RemoteMessage

// Master给自己发消息，用于去检查Worker的心跳时间
case object CheckTimeOutWorker