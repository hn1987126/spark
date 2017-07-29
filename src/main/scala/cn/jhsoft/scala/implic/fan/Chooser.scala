package cn.jhsoft.scala.implic.fan

import cn.jhsoft.scala.implic.MyPredef._

/**
  * Created by chenyi9 on 2017/7/28.
  */
class Chooser [T <% Ordered[T]] {
  def chooser (first :T, second :T): T ={
    if (first > second) first else second
  }
}

class Chooser1 [T : Ordering] {
  def chooser (first :T, second :T): T ={
    val ord = implicitly[Ordering[T]]
    if (ord.gt(first, second)) first else second
  }
}

object Chooser{
  def main(args: Array[String]): Unit = {
    val c = new Chooser[Girl]
    val g1 = new Girl("cy", 30)
    val g2 = new Girl("likai", 32)

    val g = c.chooser(g1, g2)
    println(g.name)
  }
}
