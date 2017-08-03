package cn.jhsoft.spark.project.utils

import java.text.SimpleDateFormat
import java.util.Calendar

/**
  * Created by chen on 2017/8/3.
  */
object TimeUtils {

  val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  val calendar = Calendar.getInstance()

  def apply(time: String) = {
    calendar.setTime(simpleDateFormat.parse(time))
    calendar.getTimeInMillis
  }

  def getCertainDayTime(amount: Int): Long ={
    calendar.add(Calendar.DATE, amount)
    val time = calendar.getTimeInMillis
    calendar.add(Calendar.DATE, -amount)
    time
  }

}
