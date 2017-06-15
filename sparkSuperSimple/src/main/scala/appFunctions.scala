import functions.MyFunctions.MyFunctions
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by ezhayog on 2017/6/13.
 */
object appFunctions {
  def main(args: Array[String]) {
    val logFile = getClass().getResource("readme.txt").getPath // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()

    /**
     * From the log, we can found spark will handle the RDD parallelly
     */

    val lineLengths = logData.map(MyFunctions.func1)
    lineLengths.reduce((a, b) => a + b)
    sc.stop()
  }
}
