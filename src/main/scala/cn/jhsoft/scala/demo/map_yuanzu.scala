package cn.jhsoft.scala.demo

import scala.collection.mutable
import scala.collection.mutable.Map;

/**
  * Created by chenyi9 on 2017/7/26.
  * 在scala中，Map叫映射
  */
object map_yuanzu {

  def main(args: Array[String]): Unit = {
    val m = Map("a"->1, "b"->2);
    println(m)
    m.put("c", 3)
    println(m)

    // 用元组的方式。
    val m1 = Map(("a",1), ("b",2), "d"->4);
    println(m1)

    // 元组，下标从1开始，不是0
    var t = (1, "str", m, m1)
    println(t._1)
    println(t._3)

    // 对偶元组，及对偶元组加入到map中。
    var pair = ("e",100)
    m += pair;
    println(m)
    m += (("f", 101), ("g", 102))
    println(m)

    // 元组更牛的用法，定义元组，并把里面的元素分别赋值
    val tt,(x,y,z) = (1, "spark", 2.0)
    println(tt, x, y, z)

    // 对偶元组转map
    val arr = Array(("a",1), ("b",2), ("c",3));
    println(arr.toMap);
    println()

    // 拉链 zip，会生成一份新的，对偶数组，可以直接转换成Map。
    var a = Array("b", "b", "c");
    var b = Array(1,2,3,4);
    println(a.zip(b).toBuffer)
    println()
    println()
    println()

    //## hashMap
    val map1 = new mutable.HashMap[String, Int]
    //向map中添加数据
    map1("spark") = 1
    map1 += (("hadoop", 2))
    map1.put("storm", 3)
    println(map1)

    //从map中移除元素
    map1 -= "spark"
    map1.remove("hadoop")
    println(map1)


    println()
    println()
    println()
    val lines = List(Map("a"->1, "b"->2), Map("c"->3, "d"->4));
    // 循环每行，按空格来切，最后压成一个List
    val words = lines.flatten
    println(words)

  }

}
