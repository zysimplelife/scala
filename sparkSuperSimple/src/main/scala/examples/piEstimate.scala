package examples

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by ezhayog on 2017/6/13.
 */
object piEstimate {
  def main(args: Array[String]) {


    val conf = new SparkConf().setAppName("wordCount").setMaster("local")
    val sc = new SparkContext(conf)

    val num_samples = 5*1000*1000
    val count = sc.parallelize(1 to num_samples).filter { _ =>
      val x = math.random
      val y = math.random
      x * x + y * y < 1
    }.count()
    println(s"Pi is roughly ${4.0 * count / num_samples}")


    sc.stop()

  }
}
