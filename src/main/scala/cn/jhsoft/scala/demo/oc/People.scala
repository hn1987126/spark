package cn.jhsoft.scala.demo.oc

/**
  * Created by chen on 2017/7/27.
  * gender:String 相当于 private[this]
  * var age : Int = 18 是有默认值，这样的话，伴生对象构造初始化它的时候，可以不传这个参数
  * 构造器
  */
class People(val id : Int, var name : String, gender : String, var age : Int = 18) {

}

object People{
  def main(args: Array[String]): Unit = {
    val p = new People(1, "ght", "f")

    println(p.id,p.name)

  }
}