package cn.jhsoft.scala.demo.oc

/**
  * Created by chen on 2017/7/26.
  */
class Person {
  val id="9527"
  var name = "俊航"
  // 这个只能在此类和伴生对象中用，在其他类和其他伴生对象中不能用
  private var gender = "sb"
  // 这个只能在当前类中能用，其他全不能用
  private[this] var pop:String = _

  // 方法只能在此类和伴生对象中访问
  def printPop : Unit = {
    println(pop)
  }



}

// 伴生对象，里面定义的是静态变量和方法
object Person{
  def main(args: Array[String]): Unit = {
    val p = new Person
    p.name = "sb"
    println(p.id, p.name)

    println(p.printPop)
  }
}

// 前面的private 是包访问权限，只能在jhsoft这个包里访问，
private[jhsoft] class Gril{

}

// 后面的 private 是私有的构造方法，只能在他的伴生对象中访问，其他地方不能访问
class Boy private{

}
