package cn.jhsoft.scala.demo.oc

/**
  * Created by chen on 2017/7/27.
  */
class Cat {

  val id : Int = 0
  var name : String = _

  /**
    * 辅助构造器(重载)
    * @param id
    * @param name
    */
  def this(id:Int, name:String){
    // 重载的第一行，必须要调父构造器，假如类后面有参数的那种主构造器，这里也需要传参数
    this()
    this.name = name
  }

}

object Cat{
  def main(args: Array[String]): Unit = {
    val cat = new Cat(1, "ght")
    println(cat.name)
  }
}
