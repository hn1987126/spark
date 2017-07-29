package cn.jhsoft.scala.rpc

/**
  * Created by chenyi9 on 2017/7/28.
  */
class WorkerInfo (val workerId : String, val memory : Int, val cares : Int) {
  //TODO 上一次心跳
  var lastHeartbeatTime : Long = _

}
