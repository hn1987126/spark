package cn.jhsoft.scala.demo.oc

import scala.io.Source

/**
  * Created by chen on 2017/7/27.
  * 类构造里的东西都会被执行，除了方法
  */
class MissRight {

  println("hi")

  try {
    val con = Source.fromFile("/wordcount/input/a.txt").mkString
    println(con)
  } catch {
    case e:Exception => e.printStackTrace()
  }finally {
    println("finally")
  }

  println("hi".toUpperCase())

  def sayHi: Unit = {
    println("hihi")
  }

}


object MissRight{
  val oc = "oc"

  def apply(): Unit = {
    println("no params")

  }

  // * 代表可变参数，可以传多个，java里是...
  def apply(str : String*): MissRight = {
    println(str(0))
    new MissRight
  }

  def main(args: Array[String]): Unit = {
    val m = new MissRight
    println(m)
    println()
    println()
    println()
    println()

    // 单例不需要 new
    val m1 = MissRight()
    println(m1)
    println()
    println()
    println()
    println()

    val m2 = MissRight("this is apply params")
    println(m2)

  }
}