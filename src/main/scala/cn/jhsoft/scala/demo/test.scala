package cn.jhsoft.scala.demo

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.commons.lang3.time.FastDateFormat

/**
  * Created by chenyi9 on 2017/7/26.
  */
object test {

  def main(args: Array[String]): Unit = {


    println("hello")
    println("hello1")
    var a=0 to 10
    println(a.length)
    for (i <- a) println(i)
    var b = a.filter(_%2==0)
    var c = if(1>2)true else false
    println(b)
    println(c)

    for (i<-a; if i%2!=0) println(i)
    var d = for (i<-a) yield i*10
    println(d)

    for (i<-0 to a.length) println(i)
    println()
    for (i<-0 until a.length) println(i)

    println()
    println(m1(1, 2))

    println()
    var e = for (i<-a) yield {
      i*10
      i+10
    }
    println(e)

    println()
    println()

    val fun1 = (v3:Int, v4:Int) => {
      v3+v4
    }

    val a1 = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")
    val d1 = new Date()
    println(a1.format(d1))

    val format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val c1 = "2017-08-03 18:33:57"
    println(a1.parse(c1).getTime)

  }


  def m1 (v1:Int, v2:Int) = {
    v1*v2;
    v1+v2;
    v1;
  }

  def m2(f : (Int,String) => Int) : Int = {
    f(1,"a");
  }


}
