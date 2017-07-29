package cn.jhsoft.scala.implic

import java.io.File

import cn.jhsoft.scala.implic.fan.Girl

/**
  * Created by root on 2016/5/13.
  */
object MyPredef {
  implicit def fileToRichFile(f: File) = new RichFile(f)

  implicit def girlToOrdered (g:Girl) = new Ordered[Girl]{
    override def compare(that: Girl): Int = {
      // g是当前的，that是来进行比较的，这种当前的在前，拿来比的放后，就是顺序比较
      g.age - that.age
    }
  }

  implicit def girlToOrdering = new Ordering[Girl]{
    override def compare(x: Girl, y: Girl): Int = {
      x.age - y.age
    }
  }

}
