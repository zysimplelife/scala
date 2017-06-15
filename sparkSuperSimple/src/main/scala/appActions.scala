import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by ezhayog on 2017/6/14.
 */
object appActions {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)

    val logFile = getClass().getResource("readme.txt").getPath // Should be some file on your system
    val logData = sc.textFile(logFile, 2).cache()

    printResult("reduce",logData.reduce((a,b) => a + " and " + b))
    printResult("count",logData.count())
    printResult("first",logData.first())
    printResult("take",logData.take(5))
    printResult("takeSample",logData.takeSample(false,5))
    printResult("countByValue",logData.countByValue())

    sc.stop()
  }

  def printResult[A,B](name: A, result:B) {
    println(s"$name = $result")
    println("")
  }



}
