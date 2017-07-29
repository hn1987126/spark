package cn.jhsoft.scala.demo

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
