package section1

import org.scalatest.FunSuite

class CollectionsSpec extends FunSuite {

  import Collections._

  test("toUpper converts every parameter to its upper case representation") {
    val result = toUpper("Hi", "there", "how", "are", "you")
    assert(result === Seq("HI", "THERE", "HOW", "ARE", "YOU"))
  }

  test("concatenates all elements of the array separated with an empty character") {
    val result = concatenate(Array("Hi", "there", "how", "are", "you"), ' ')
    assert(result === "Hi there how are you")
  }

  test("concatenates all elements of the array separated with a dot character") {
    val result = concatenate(Array("Hi", "there", "how", "are", "you"), '.')
    assert(result === "Hi.there.how.are.you")
  }

  test("sum returns the sum of all elements") {
    val result = sum(List(3, 4, -7))
    assert(result === 0)
  }

  test("fold sums all elements with a sum function") {
    val result = fold(List(2, 5, 8, 10), (x, y) => x + y)
    assert(result === 25)
  }

  test("fold calculates the product of all elements") {
    val result = fold(List(2, 5, 8), (x, y) => x * y)
    assert(result === 80)
  }

  test("fold calculates a sum with a maximum of 9") {
    val result = fold(List(2, 5, 8, 10), (x, y) => (x + y) % 10)
    assert(result === 5)
  }

  test("`extractWords` extract words from a list of Strings") {
    val result = extractWords(Seq("Scala is fun", "fun is good"))
    assert(result === Seq("Scala", "is", "fun", "fun", "is", "good"))
  }

  test("wordCounts counts the number of occurrences words from a strings") {
    val result: Map[String, Int] = wordCount(Seq("Programming is fun"))
    assert(result === Map("Programming" -> 1, "is" -> 1, "fun" -> 1))
  }

  test("wordCounts counts the number of occurrences of words from many strings") {
    val result: Map[String, Int] = wordCount(
      Seq("Programming is fun", "Programming is even more of it", "fun is more", "more is less")
    )

    assert(result === Map("is" -> 4, "less" -> 1, "it" -> 1, "even" -> 1, "fun" -> 2, "more" -> 3, "Programming" -> 2, "of" -> 1))
  }

}
