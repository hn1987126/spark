package cn.jhsoft.scala.demo.oc.interface

/**
  * Created by chen on 2017/7/27.
  */
trait Animal {
  // 接口里的可实现，可不实现
  def run: Unit = {
    println("Animal run")
  }

  def sayHi
}
