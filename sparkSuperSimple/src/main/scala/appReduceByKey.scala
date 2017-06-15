import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by ezhayog on 2017/6/14.
 */
object appReduceByKey {
  def main(args: Array[String]) {
    val logFile = getClass().getResource("readme.txt").getPath // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()

    val pairs = logData.map(s => (s, 1))
    val counts = pairs.reduceByKey((a, b) => a + b)

    counts.foreach(println)

    sc.stop()

  }
}
