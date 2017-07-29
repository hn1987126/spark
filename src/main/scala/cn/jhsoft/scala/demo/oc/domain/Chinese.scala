package cn.jhsoft.scala.demo.oc.domain

import cn.jhsoft.scala.demo.oc.abs.Human
import cn.jhsoft.scala.demo.oc.interface.Animal

/**
  * Created by chen on 2017/7/27.
  * 如果又要继承类，又要实现接口，那就需要把extends去继承类，用with去实现接口
  *
  */
//class Chinese extends Animal{
class Chinese extends Human with Animal{

  // 实现接口里的方法  override可以不写
  // 如果不重写这方法，那就会去调接口里实现的run，如果把下面这几行注释，那将打印接口里自己实现的方法  打印 animal run
  // 如果train里自己实现了run方法，那说明run方法是非抽象类方法，子类继承时，必须要加 override。如果接口里没有实现，那就是抽象方法，可以不用加 override
  override def run: Unit = {
    println("run")
  }

  override def sayHi: Unit = {
    println("say hi")
  }
}

object Chinese{
  def main(args: Array[String]): Unit = {
    val c = new Chinese
    c.run


  }
}
