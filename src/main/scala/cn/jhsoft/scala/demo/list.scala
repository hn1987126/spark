package cn.jhsoft.scala.demo

import scala.collection.mutable.ListBuffer

/**
  * Created by chenyi9 on 2017/7/26.
  */
object list {

  def main(args: Array[String]): Unit = {
    val list = ListBuffer(1,2,3)
    list(1) = 200
    println(list)

    println(list.map(_*100))
    println()

    // 往前插入
    val list1 = List(1,2,3)
    val list2 = 0 :: list1
    println(list2)
    println(0 +: list1)
    println(list1.::(0))
    println(list1.+:(0))


    //创建一个不可变的集合
    val lst1 = List(1,2,3)
    //将0插入到lst1的前面生成一个新的List
    val lst2 = 0 :: lst1
    val lst3 = lst1.::(0)
    val lst4 = 0 +: lst1
    val lst5 = lst1.+:(0)

    //将一个元素添加到lst1的后面产生一个新的集合
    val lst6 = lst1 :+ 3
    println(lst6)

    val lst0 = List(4,5,6)
    //将2个list合并成一个新的List
    val lst7 = lst1 ++ lst0
    //将lst1插入到lst0前面生成一个新的集合
    val lst8 = lst1 ++: lst0

    //将lst0插入到lst1前面生成一个新的集合
    val lst9 = lst1.:::(lst0)
    println()
    println()
    println()
    println()



    //#### 可变的,+ 是加元素，如果要加集合，需要 ++
    val ll = ListBuffer(1,2,3);
    ll += 5   // 等同 ll.append(5)
    println(ll)
    ll.append(11,12,13,15)
    ll += (8, 9)
    println(ll)
    val ll2 = ListBuffer(100,102)
    ll ++= ll2
    println(ll)
    val ll3 = ll ++ ll2
    println(ll3)
    println()
    println()
    println()

    // List分组
    val list21 = List(1,3,5,6,4,2,8,7)
    println(list21.sorted)
    println(list21.grouped(5))
    println(list21.grouped(5).toList)
    println(list21.grouped(5).toList.toIterator)
    // 将多个list压扁成一个List   (把list分组的压平，也就是还原)
    println(list21.grouped(5).toList.flatten)


    // 单机版的wordCount
    val lines = List("i love ght", "love jd", "love sina");
    // 循环每行，按空格来切，最后压成一个List
    val words = lines.map(_.split(" ")).flatten
    println(words)
    // 上面也相当于 这样写
    val words1 = lines.flatMap(_.split(" "))
    println(words1)
    // 上面得到这样的结果  List(i, love, ght, love, jd, love, sina)

    // 做单词统计，第一步，每个单词一个元组，得到List((i,1), (love,1), (ght,1), (love,1), (jd,1), (love,1), (sina,1))
    val wordsAndOne = words.map((_, 1))
    println(wordsAndOne)
    // 分组(类似reduces里的输入)，得到 Map(love -> List((love,1), (love,1), (love,1)), ght -> List((ght,1)), sina -> List((sina,1)), jd -> List((jd,1)), i -> List((i,1)))
    // 用元组里的第一个，也就是word来分组
    val wordsGroup = wordsAndOne.groupBy(_._1)
    println(wordsGroup)
    // 得到的结果  Map(ght -> 1, sina -> 1, jd -> 1, i -> 1, love -> 3)
    val result = wordsGroup.map(t=>(t._1 -> t._2.size))
    println(result)
    // 排序
    val finalResult = result.toList.sortBy(_._2).reverse;
    println(finalResult)


    // 另外一种方式：
    val result1 = wordsGroup.mapValues(x => x.size)
    println(result1)

    // reduce 对每个元素分别进行相加或相减，reduce方法默认是调reduceLeft，从左往右相加相减
    val a = List(1,2,3,4,5,6)
    println(a.reduce(_+_))
    println(a.reduce(_-_))
    println(a.reduceRight(_-_))

    // par可以把数组转为 并行处理的数组，也就是起多个线程
    println(a.par.reduce(_+_))

    // 与reduce对应的，有个fold【折叠】方法，他们类似，但是fold可以加初始值
    println(a.fold(10)(_+_))
    // 也可以并行，但是并行的结果不一定对，因为每个进程处理都有个初始值，所以多加了几个初始值。这时就需要初始值为0
    println(a.par.fold(10)(_+_))
    println(a.par.fold(0)(_+_))


    // 对wordcount再进行别的处理方式，用fold方法，每个元素进行累加
    val result2 = wordsGroup.mapValues(_.foldLeft(0)(_+_._2))
    println(result2)


    //    聚合 aggregate
    val a1 = List(List(1,2,3,4), List(5,6), List(7))
    // 第一个_代表初始值或累加值，第二个_代表每一个元素，最后两个_代表对前面的结果进行累加
    println(a1.aggregate(0)(_+_.sum, _+_))

    println()
    println()
    val a2 = List(1,2,3,4)
    val a3 = List(5,6,4)
    // 并集
    println(a2.union(a3))
    // 交集
    println(a2.intersect(a3))
    // 差集
    println(a2.diff(a3))

  }

}
