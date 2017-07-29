package cn.jhsoft.scala.implic.fan

/**
  * Created by chenyi9 on 2017/7/28.
  */
class BoyTest[T]{
  def choose[T <: Comparable[T]](first: T, second: T): T = {
    if(first.compareTo(second) > 0) first else second
  }
}

object BoyTest {

  def main(args: Array[String]): Unit = {

    val b1 = new Boy("cy", 30)
    val b2 = new Boy("likai", 32)

    val arr = Array[Boy](b1, b2)
    println(arr.toBuffer)
    val arr2 = arr.sortBy(x=>x)
    for (b <- arr2){
      println(b.age)
    }

    val mr = new BoyTest[Boy]
    val b = mr.choose(b1, b2)
    println(b.name)
  }

}
