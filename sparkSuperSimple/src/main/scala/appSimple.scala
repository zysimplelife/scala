import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by ezhayog on 2017/6/13.
 */
object appSimple {
  def main(args: Array[String]) {
    val logFile = getClass().getResource("readme.txt").getPath // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    sc.stop()
  }
}
