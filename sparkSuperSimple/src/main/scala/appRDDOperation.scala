import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by ezhayog on 2017/6/13.
 */
object appRDDOperation {
  def main(args: Array[String]) {
    val logFile = getClass().getResource("readme.txt").getPath // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val lineLengths = logData.map(s => s.length)

    /**
     * it will increase the speed after first reduce operation rom the Document
     * http://spark.apache.org/docs/2.1.0/programming-guide.html#rdd-operations
     * But I couldn't got the differece from log
     *
     * The log with comments
     *
     * Job 0 finished: reduce at app.scala:14, took 0.834920 s
     * ...
     * Job 1 finished: reduce at app.scala:15, took 0.047550 s
     * ...
     * Job 2 finished: reduce at app.scala:16, took 0.044603 s
     */
   // lineLengths.persist()
    println("Total Length is " + lineLengths.reduce((a, b) => a + b))
    println("Total Length is " + lineLengths.reduce((a, b) => a + b))
    println("Total Length is " + lineLengths.reduce((a, b) => a + b))
    println("Total Length is " + lineLengths.reduce((a, b) => a + b))
    sc.stop()
  }
}
