package cn.jhsoft.scala.implic.fan

/**
  * Created by chenyi9 on 2017/7/28.
  */
class Boy(val name : String, val age : Int) extends Comparable[Boy] {
  override def compareTo(o: Boy): Int = {
    this.age.compareTo(o.age)
    //this.age - o.age
  }
}
