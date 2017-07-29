package cn.jhsoft.scala.implic.fan

/**
  * Created by chenyi9 on 2017/7/28.
  * 要求 传过来的T，最高只能是实现了 Comparable 接口的类
  */
class Pair[T <: Comparable[T]] {

  def bigger(first :T, second :T) : T = {
    // 因为 实现这类 Comparable 接口或类的 ，都默认就有 compareTo方法，
    if (first.compareTo(second) > 0) first else second
  }

}

object Pair {
  def main(args: Array[String]): Unit = {
    val p = new Pair[String]
    val res = p.bigger("hadoop", "spark")
    println(res)
  }
}