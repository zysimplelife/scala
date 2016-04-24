/**
 * Created by ezhayog on 4/24/2016.
 *
 * First calls is the most important concept in scala
 * Following example show how to understand the concept
 *
 * More use case is needed in future
 */
object FirstClassFunction {

  def saySomething(method:() => String): Unit ={
    println(method())
  }

  class SayHelloClass extends Function0[String]{
    def apply = "Hello, I am a object"
  }

  class SayHelloClassSugar extends (() => String){
    def apply = "Hello, I am a object"
  }

  def main(args: Array[String]): Unit = {

    /**
     * Add sayHello as an variable
     */
    val sayHello = () => "Hello, world"
    println(sayHello())

    /**
     * pass the method object to the method
     */
    saySomething(sayHello)

    /**
     * The first level function is actually an object of an Function Class
     */
    println(new SayHelloClass()())
    /**
     * Tt can also be called as follow,  SayHelloClassSugar use grammar sugar to defined the object
     */
    println(new SayHelloClassSugar().apply)


    /**
     * A common use of first method is in collection, it will be in other examples
     * Here only provide the for each one
     */

    val sayHelloTo = (name:String) => { println(s"Hello {$name} and welcome!") }
    val nameSet = Seq("charlie","charlie B","Charlie c")
    nameSet.foreach(sayHelloTo)

    /**
     * It is equals to
     */
    nameSet.foreach(n => println(s"Hello {$n} and welcome!"))
  }

}


