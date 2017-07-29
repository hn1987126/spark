package cn.jhsoft.scala.implic

/**
  * Created by chenyi9 on 2017/7/28.
  */
object HighFunc {
  val func: Int => Int = {x => x * x}

  def multiply(x: Int) : Int = x * x

  def m1(x: Int)(y: Int) = x * y

  def m2(x: Int) = (y: Int) => x * y

  def multi() = (x: Int) => {
    x * x
  }

  def main(args: Array[String]) {
    println(multiply(5))

    val arr = Array(1,2,3,4,5)
    println(arr.map(multi()).toBuffer)
//
//    val a1 = arr.map(multi())
//
//    println(a1.toBuffer)

  }
}
