//package cn.jhsoft.scala.demo.actor
//
//import scala.actors._
//import scala.collection.mutable.{HashSet, ListBuffer}
//import scala.io.Source
//
///**
//  * Created by chen on 2017/7/27.
//  */
//class ActorWordCount extends Actor{
//  override def act(): Unit = {
//
//    loop{
//      react{
//        // 提交任务
//        case SubmitTask(filename) => {
//          // 这个得到结果  Map(ght -> 1, sina -> 1, jd -> 1, i -> 1, love -> 3)
//          val result = Source.fromFile(filename).getLines().flatMap(_.split(" ")).map((_, 1)).toList.groupBy(_._1).mapValues(_.size)
//          sender ! ResultTask(result)
//        }
//
//        case StopTask => {
//          exit()
//        }
//      }
//    }
//
//  }
//}
//
//// ---  这叫样例类
//// 提交case
//case class SubmitTask(filename : String)
//// 返回结果case
//case class ResultTask(result: Map[String, Int])
//// 结束线程 case
//case object StopTask
//
//
//
//
//object ActorWordCount{
//
//  def main(args: Array[String]): Unit = {
//
//    // 保存每一个进程的结果，一个文件一个进程
//    val replySet = new HashSet[Future[Any]]()
//    // 已完成的异步请求的结果，里面存的是ResultTask，也就是Map
//    val resultList = new ListBuffer[ResultTask]()
//
//    val files = Array("/wordcount/input/a.txt", "/wordcount/input/a.txt.1")
//    for (f<-files){
//      val actor = new ActorWordCount
//      // !! 是代表异步提交请求，并需要得到返回值
//      val reply = actor.start() !! SubmitTask(f)
//      // 是否处理完，reply都有值，只不过里面有个 isSet 来标识他是否完成
//      replySet += reply
//    }
//
//    // 循环所有异步提交请求的结果
//    while (replySet.size > 0){
//      // 是是否完成， toCompute 是完成的 replySet
//      val computeSet = replySet.filter(_.isSet)
//      for(set<-computeSet){
//        // f.apply 相当于 java里的get 方法，获取线程里返回的结果
//        // asInstanceOf 是类型转换，把他转为 ResultTask类型，也就是个 Map
//        var result = set.apply().asInstanceOf[ResultTask]
//        resultList += result
//
//        // 把已完成 的 replySet 删除掉，这样整个循环才能结束
//        replySet -= set
//      }
//      // 等待别的任务 完成
//      Thread.sleep(100)
//    }
//
//
//    // 汇总功能
//    // 到这里，上面的循环是结束了，也就代表着异步请求里的任务都已经完成
//    // resultList 的值   ListBuffer(ResultTask(Map(ght -> 1, tt -> 1, i -> 1, me -> 1, love -> 1)), ResultTask(Map(ght -> 1, tt -> 1, i -> 1, me -> 1, love -> 1)))
//    // resultList.map(_.result)  的结果为  ListBuffer(Map(ght -> 1, tt -> 1, i -> 1, me -> 1, love -> 1), Map(ght -> 1, tt -> 1, i -> 1, me -> 1, love -> 1))
//    // 扁平后得到的结果为  ListBuffer((ght,1), (tt,1), (i,1), (me,1), (love,1), (ght,1), (tt,1), (i,1), (me,1), (love,1))
//    // groupBy的结果为  Map(ght -> ListBuffer((ght,1), (ght,1)), tt -> ListBuffer((tt,1), (tt,1)), i -> ListBuffer((i,1), (i,1)), me -> ListBuffer((me,1), (me,1)), love -> ListBuffer((love,1), (love,1)))
//    val res = resultList.flatMap(_.result).groupBy(_._1).mapValues(_.foldLeft(0)(_+_._2))
//    println(res)
//  }
//
//}