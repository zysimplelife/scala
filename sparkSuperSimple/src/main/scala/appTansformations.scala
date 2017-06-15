import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by ezhayog on 2017/6/14.
 */
object appTansformations {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)

    val logFile = getClass().getResource("readme.txt").getPath // Should be some file on your system
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
    printResult("flatMapPartitionRet", logData.mapPartitions(lines => List(lines.next).iterator).collect())

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


    val userFile = getClass().getResource("user.txt").getPath // Should be some file on your system
    val user = sc.textFile(userFile, 2).cache()
    val rows = user.map(line => line.split(","))
    val jobToName = rows.map(line => (line(3), line(0)))

    printResult("groupBykey", jobToName.groupByKey.collect)
    printResult("reduceByKey", jobToName.reduceByKey((v1,v2) => v1 + " and " + v2 ).collect)
    // skip aggregateByKey because I haven't understand what it used for
    //printResult("reduceByKey", jobToName.aggregateByKey((v1,v2) => v1 + " and " + v2 ).collect)
    printResult("sortByKey",jobToName.sortByKey().collect())


    val addressFile = getClass().getResource("address.txt").getPath // Should be some file on your system
    val address = sc.textFile(addressFile, 2).cache()
    val rowsUser = user.map(line => line.split(","))
    val nameToInfo = rowsUser.map(line => (line(0), (line(1),line(2),line(3))))
    val rowAddress = address.map(line => line.split(","))
    val nameToAddress = rowAddress.map(line=>(line(0),line(1)))
    printResult("join",nameToInfo.join(nameToAddress).collect())


    val rdd1 = sc.parallelize(List("abe", "abby", "apple"))
    val rdd2 = sc.parallelize(List("apple", "beatty", "beatrice"))
    printResult("cartisian",rdd1.cartesian(rdd2).collect())
    
    sc.stop()
  }

  def printResult[A,B](name: A, rows: Array[B]) {
    println(s"$name=")
    rows.foreach(row => print(row + ","))
    println("")
  }



}
