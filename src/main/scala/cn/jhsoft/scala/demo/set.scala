package cn.jhsoft.scala.demo

import scala.collection.mutable.HashSet


/**
  * Created by chenyi9 on 2017/7/26.
  *
  * 序列主要有 List
  * Scala的集合有三大类：序列Seq、集Set、映射Map，所有的集合都扩展自Iterable特质
  * 在Scala中集合有可变（mutable）和不可变（immutable）两种类型，immutable类型的集合初始化后就不能改变了（注意与val修饰的变量进行区别）
  *
  */
object set {

  def main(args: Array[String]): Unit = {

    // 这是不可变Set的情况
    val s = new scala.collection.immutable.HashSet[Int]
    val s1 = s + 1
    val s2 = scala.collection.immutable.HashSet(2,3,4,5)
    println(s1)
    println(s1 ++ s2)
    println(s1 ++ Set(100, 101))
    println()
    println()
    println()
    println()

    // 可变set
    val set1 = new HashSet[Int]()
    println(set1 += 1)
    println(set1 ++= Set(1,2,3))

    // 删除元素，map中也可以删除
    println(set1 -= 1)
    set1.remove(2)
    println(set1)

  }

}
