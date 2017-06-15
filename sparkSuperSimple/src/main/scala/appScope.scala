import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by ezhayog on 2017/6/14.
 */
object appScope {
  def main(args: Array[String]) {
    val logFile = getClass().getResource("readme.txt").getPath // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()

    var sum = 0;

    val logLength = logData.map(l => l.length)

    logLength.foreach(x => {
      sum += x
      println(s"log length is $sum")
    })

    /**
     * The result is what we expected
     * Spark will create closure for each executor .
     */
    println(s"log length is $sum")
    sc.stop()
  }
}
