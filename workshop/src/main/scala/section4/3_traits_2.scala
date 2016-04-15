package section4

// An expample of a standard scala library trait to let compare our own types
case class Rational(n: Int, d: Int) extends Ordered[Rational] {
  def compare(that: Rational): Int = (this.n * that.d) - (that.n * this.d)
}

object ### extends App {
  val x = Rational(2, 3)
  val y = Rational(1, 3)

  println(x > y)
  println(x < y)
  println(x == y)
  println(x == x)
}