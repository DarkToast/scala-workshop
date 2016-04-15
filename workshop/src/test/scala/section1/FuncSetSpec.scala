package section1

import org.scalatest.FunSuite

class FuncSetSpec extends FunSuite {

  import FuncSet._

  val a: Set = singleSet(10)
  val b: Set = singleSet(15)
  val c: Set = singleSet(-150)


  test("contains works with custom Set") {
    val s: Set = x => x == 12
    assert( contains(s, 12) === true )
    assert( contains(s, 30) === false )
  }

  test("A new singleSet contains 10") {
    val s: Set = singleSet(10)
    assert( contains(s, 10) === true)
    assert( contains(s, -5) === false)
  }

  test("union of 'a' and 'b' contains both elements") {
    val s: Set = union(a, b)
    assert( contains(s, 10) === true)
    assert( contains(s, 15) === true)
  }

  test("union of 'a' and 'b' does not contains 'c'") {
    val s: Set = union(a, b)
    assert( contains(s, -150) === false)
  }

  test("intersect should only contains the elements, which are in both sets") {
    //(10, 15, 30)
    val s1: Set = union(union(a, b), singleSet(30))
    //(10, 30)
    val s2: Set = union(a, singleSet(30))

    val s: Set = intersect(s1, s2)
    assert( contains(s, 10) === true)
    assert( contains(s, 15) === false)
    assert( contains(s, 30) === true)
  }

  test("diff should only contains the elements, which are not in both sets") {
    //(10, 15, 30)
    val s1: Set = union(union(a, b), singleSet(30))
    //(10, 30)
    val s2: Set = union(a, singleSet(30))

    val s: Set = diff(s1, s2)
    assert( contains(s, 10) === false)
    assert( contains(s, 15) === true)
    assert( contains(s, 30) === false)
  }

  test("filter contains only a subset, which does match the filter") {
    //(10, 15, 30)
    val s1: Set = union(union(a, b), singleSet(30))
    val filter1: Int => Boolean = i => i >= 15

    val s: Set = filter(s1, filter1)
    assert( contains(s, 10) === false)
    assert( contains(s, 15) === true)
    assert( contains(s, 30) === true)
  }

  test("forAll returns true") {
    // (10, 15, -150)
    val s1: Set = union(union(a, b), c)
    val uQuantifier: Int => Boolean = i => i <= 15

    assert( forall(s1, uQuantifier) === true )
  }

  test("forAll returns false") {
    // (10, 15, -150)
    val s1: Set = union(union(a, b), c)
    val uQuantifier: Int => Boolean = i => i > 14

    assert( forall(s1, uQuantifier) === false )
  }

  test("exists returns true") {
    // (10, 15, -150)
    val s1: Set = union(union(a, b), c)
    val eQuantifier: Int => Boolean = i => i == 15

    assert( exists(s1, eQuantifier) === true )
  }

  test("exists returns false") {
    // (10, 15, -150)
    val s1: Set = union(union(a, b), c)
    val eQuantifier: Int => Boolean = i => i == 0

    assert( exists(s1, eQuantifier) === false )
  }

  test("map transforms every element in the Set") {
    val s1: Set = union(a, union(b, c))
    val transform = (i: Int) => i * 2

    val mapped: Set = map(s1, transform)

    assert( contains(mapped, 20) === true)
    assert( contains(mapped, 30) === true)
    assert( contains(mapped, -300) === true)
  }

  test("toString returns a nice string representation of the Set") {
    val s1: Set = union(a, union(b, c))

    assert( createString(s1) === "{-150, 10, 15}")
  }
}
