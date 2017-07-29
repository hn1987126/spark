package cn.jhsoft.scala.demo.oc

/**
  * Created by chen on 2017/7/27.
  */
class Case {

}

object Case extends App{

  // option模式匹配,Some是去map里取值，如果取到了则返回那个值
  var map = Map("a"->1, "b"->2)
  val v = map.get("b") match{
    case Some(i) => i
    case None => 0
  }
  println(v)

  // 更好的方式
  val v1 = map.getOrElse("b", 0)
  println(v1)
  println()
  println()
  println()
  println()


  // 偏函数  PartialFunction[String , Int]  代表输入String，输出Int
  def func1 : PartialFunction[String , Int] = {
    case "one" => 1
    case "two" => {
      println("this is two")
      2
    }
    case _ => -1
  }

  // 根上面的 偏函数 功能一模一样。传入String，输出Int,根据传过来的num 进行匹配。
  def func2(num:String):Int = num match {
    case "one" => 1
    case "two" =>2
    case _ => -1
  }

  println(func1("two"));
  println()
  println()
  println()
  println(func2("three"));

}
