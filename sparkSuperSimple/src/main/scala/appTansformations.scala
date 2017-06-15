import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by ezhayog on 2017/6/14.
 */
object appTansformations {
  def main(args: Array[String]) {
    val logFile = getClass().getResource("readme.txt").getPath // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()

    printResult("rows", logData.map(line => "hello" + line).collect())

    /**
     * flatMap is helpful with nested datasets.
     * It may be beneficial to think of the RDD source as hierarchical JSON (which may
     * have been converted to case classes or nested collections).
     * This is unlike CSV which has no hierarchical structural.
     */

    // result is (List(line1,line1),List(line2,line2)...)
    printResult("mapRet", logData.flatMap(line => List(line, line)).collect())

    //result is (line1,line1,line2,line2...)
    printResult("flatMapRet", logData.flatMap(line => List(line, line)).collect())


    /**
     * collect the first element of this partition
     */
    printResult("flatMapPartitionRet",  logData.mapPartitions(lines => List(lines.next).iterator).collect())
    /**
     * Collect all element appending index
     */
    printResult("flatMapPartitionWithIndexRet", logData.mapPartitionsWithIndex((index: Int, it: Iterator[String]) => it.toList.map(x => index + ", " + x).iterator).collect())

    printResult("sampleResult", logData.sample(true, .2).collect)


    val par1 = sc.parallelize(1 to 9)
    val par2 = sc.parallelize(5 to 15)
    printResult("union", par1.union(par2).collect)
    printResult("intersection", par1.intersection(par2).collect)
    printResult("distinct", par1.union(par2).distinct.collect)


    sc.stop()
  }

  def printResult(name: String, rows: Array[String]) {
    println(s"$name=")
    rows.foreach(row => print(row + ","))
    println("")
  }

  def printResult(name: String, rows: Array[Int]) {
    println(s"$name=")
    rows.foreach(row => print(row + ","))
    println("")
  }
}
