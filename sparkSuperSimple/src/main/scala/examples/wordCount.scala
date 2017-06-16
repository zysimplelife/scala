package examples

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by ezhayog on 2017/6/13.
 */
object wordCount {
  def main(args: Array[String]) {


    val conf = new SparkConf().setAppName("wordCount").setMaster("local")
    val sc = new SparkContext(conf)

    val path = getClass().getResource("../howToLeanEnglish.txt").getPath // Should be some file on your system
    val textFile = sc.textFile(path, 2)

    /**
     * Using flat map help to put all word in to list
     */
    val counts = textFile.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    val result = counts.sortBy(_._2,false).collect()
    println(s"count is ready ! ")
    result.foreach(println)

    //counts.saveAsTextFile("target/wordCount.txt")


    sc.stop()

  }
}
