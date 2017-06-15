package functions

/**
 * Created by ezhayog on 2017/6/13.
 * Spark recommend to use a singleton object to hold functions.
 * http://spark.apache.org/docs/2.1.0/programming-guide.html#rdd-operations
 */
object MyFunctions {
  object MyFunctions {
    def func1(s: String): String = {
      val msg: String = "Hello " + s
      println(msg)
      return msg }
  }
}
