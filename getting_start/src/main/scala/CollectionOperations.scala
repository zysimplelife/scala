/**
 * Created by ezhayog on 4/27/2016.
 */
object CollectionOperations {

  abstract class ExceptionBase

  case class ExceptionA(code: Int) extends ExceptionBase

  case class ExceptionB(code: Int, msg: String) extends ExceptionBase

  case class ExceptionC(code: Int, msg: String, details: String) extends ExceptionBase


  def exceptionList = Seq.range(0, 100, 5).map {
    case i: Int if i % 3 == 0 => ExceptionA(i)
    case i: Int if i % 3 == 1 => ExceptionB(i, s"got exception $i")
    case i => ExceptionC(i, s"got exception $i", s"details $i")
  }


  def main(args: Array[String]): Unit = {
    /**
     * US:  I want to get all ExceptionB
     */

    // Method 1,  do not recommend,  isInstanceOf more like a java way.
    exceptionList.filter(_.isInstanceOf[ExceptionB]).foreach(print)

    println

    // Method 2,  collect means filter and map, quite useful
    exceptionList.collect({ case e: ExceptionB => e}).foreach(print)

    println

    //Method 3, not work, filter will throw exception
    try {
      exceptionList.filter({ case e: ExceptionB => true}).foreach(print)
    } catch {
      case e: MatchError => print("expect Match Exception Error")
    }

    println
    //Method 4,  work, filter with default value
    exceptionList.filter({
      case e: ExceptionB => true
      case _ => false
    }).foreach(print)


    /**
     * US:  I want to have all ExceptionC details
     */
    println
    // collect means filter and map, quite useful
    exceptionList.collect({ case ExceptionC(_,_,d) => d}).foreach(print)


    /**
     * US I want to have all ExceptionA error code be converted to another one
     */
    println


    val converter = (i:Int) => s"102$i"  // function
    exceptionList.collect({ case ExceptionA(c) => converter(c)}).foreach(println)


  }




}
