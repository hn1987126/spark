package cn.jhsoft.scala.demo.oc

import scala.util.Random

/**
  * Created by chen on 2017/7/27.
  * 继承App，可以不用写main方法，直接就可以运行。
  */
object AppDemo extends App{
  println("extends app, no main")

  val arr = Array("hi ght", "hi jd", "hi sina")
  val name = arr(Random.nextInt(arr.length))
  println(name)

  name match {
    case "hi ght" => println("hi hi ght")
    case "jd" => println("i love jd")
    case _ => "i dont know"
  }
  println()
  println()
  println()


  // case按类型来判断 而且还可以在case里加条件
  var arr2 = Array("hello", 1, -2.0, AppDemo)
  val name2 = arr2(Random.nextInt(arr2.length))
  name2 match {
    case x : Int => println("Int" + x)
    case y : String => println("String" + y)
    case z : Double if (z >= 0)  => println("Double")
    case _ => throw new Exception("no find")
  }

  val a=1
  val b=2
  println(s"$a,$b")

  println()
  println()
  println()



}
