package cn.jhsoft.scala.demo

import scala.collection.mutable.ArrayBuffer

/**
  * Created by chenyi9 on 2017/7/26.
  */
object array {

  val fun1 = (x:Int, s:String) => {x+s}

  def m1(f : (Int, String) => String) : String = {
    f(1, "ght")
  }

  val fun2 : (Int, String) => String = {
    (a, b) => {
      a+b
    }
  }

  def main(args: Array[String]): Unit = {
//    println(m1(fun1))
//
//    val arr = Array(1,2,3,4,5)
//    val arr1 = arr.map((x: Int) => x*10)
//    // 由于已经数组里的值是整型，所以可以省略为
//    var arr2 = arr.map(x => x+5)
//    println(arr1.toBuffer)
//    println(arr2.toBuffer)
//    println(arr.toBuffer)

//    print(m3("ght"));

    val arr3 = new ArrayBuffer[Int]();
    arr3 += 1;
    arr3 += (1,2,3,4,5)
    arr3 ++= Array(6,7)
    arr3 ++= ArrayBuffer(8,9)
    // 在脚标为1的位置插入，100和1000
    arr3.insert(1, 100, 1000)
    // 在脚标为1的位置删除2个元素
    arr3.remove(1, 2)
    println(arr3)

    // 遍历数组
    for(i <- arr3){
      print(i+" ")
    }
    println()

    // 脚标until方法
    for (i<- 0 until arr3.length){
      print(i+",")
    }
    println()

    // 脚标until方法，并结果反转，也就是反着输出
    for (i<- (0 until arr3.length).reverse){
      print(i+",")
    }
    println()
    println(arr3.max)
    println(arr3.sum)
    println(arr3.sorted)
    println(arr3.sorted.reverse)
    // 由大到小排
    println(arr3.sortWith(_>_))
    // 由小到大排
    println(arr3.sortWith(_<_))

    println(fun2(1,"abc"))
    println()

    val a1 = Array(1,3,4,5,8,9,10,7,6)
    val a2 = a1.filter(x=>x%2==0)
    val a3 = a1.map(x=>x*10)
    println(a1.toBuffer,a2.toBuffer,a3.toBuffer)

    // foreach与map方法的区别 就是不会产生新的数组
    a1.foreach(_+10)
    println(a1.toBuffer)

  }

  def m2(str:String): Unit ={
    println(str)
  }

  def m3(str:String) ={
    println(str)
  }

}
