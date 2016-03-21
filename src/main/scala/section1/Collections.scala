package section1

import scala.annotation.tailrec

object Collections {

  /**
    * Converts every repeated parameter to its upper case representation
    * and returns them in a Sequence of Strings.
    *
    * @param input The repeated parameter is also a `Seq[String]`.
    * @return `Seq` ist the base class of Array or List.
    */
  def toUpper(input: String*): Seq[String] = input.map(_.toUpperCase)

  /**
    * Concatenates every String in the parameter array with the
    * separator character.
    *
    * @param input
    * @param separator
    * @return The concatenated String
    */
  def concatenate(input: Array[String], separator: Char): String = input.mkString(separator.toString)

  /**
    * Sums all integers of the `values` list.
    *
    * @param values
    * @return
    */
  def sum(values: List[Int]): Int = values.sum

  /**
    * `fold` takes a list of integers and a concatenation function, so that
    * all elements are fold together to one resulting integer. e.g.:
    * A List(2, 4, 8) and a function (a, b) => a + b should result in 2 + 4 + 8 = 14:
    *
    * @param values
    * @param concat
    * @return
    */
  def fold(values: List[Int], concat: (Int, Int) => Int): Int = {

    @tailrec
    def step(acc: Int, list: List[Int]): Int = {
      if(list.isEmpty) acc
      else step(concat(acc, list.head), list.tail)
    }

    step(values.head, values.tail)
  }

  /**
    * Extracts the words from a sequence of strings. e.g.:
    * `Seq("Hello world", "Hello friend")` results in `Seq("Hello", "world", "Hello", "friend")`
    *
    * @param strings
    * @return
    */
  def extractWords(strings: Seq[String]): Seq[String] = strings.flatMap(s => s.split(" "))

  /**
    * Counts the words of all strings in the `strings` sequence. e.g:
    * `Seq("Hello world", "Hello friend")` results in `Map("Hello" -> 2, "world" -> 1, "friend" -> 1)`
    *
    * Hint: Sequence has the `groupBy` method.
    *
    * @param strings
    * @return
    */
  def wordCount(strings: Seq[String]): Map[String, Int] = {
    extractWords(strings)
      .groupBy(s => s)
      .mapValues(list => list.size)
  }
}
