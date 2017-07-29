package cn.jhsoft.scala.implic
import java.io.File
import scala.io.Source


/**
  * Created by root on 2016/5/13.
  */
object MyPredef1 {
  implicit def fileToRichFile(file: File) = new RichFile(file)
}


class RichFile(val f: File) {
  def read() = Source.fromFile(f).mkString
}

object RichFile {
  def main(args: Array[String]) {
    val f = new File("c://sys001.log")
    // 这是没有用装饰的情况这样读取
    val content = new RichFile(f).read();
    println(content)
    // 使用装饰
    import MyPredef1._
    val content1 = f.read()
    println(content1)
  }
}


