package section1

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

object FuncSet {

  /**
    * Der Typalias `Set` ist definiert als eine Funktion Int auf Boolean
    */
  type Set = Int => Boolean

  /**
    * Das leere Set ist definiert als eine Funktion, die immer false liefert.
    * Der Eingabeparameter kann hier ignoriert werden. Daher können wir `_`
    * schreiben.
    *
    * @return
    */
  def emptySet: Set = _ => false

  /**
    * Ein Set der Größe 1 kann als Funktion definiert werden,
    * welche ein übergebenes Element mit einem definierten
    * Wert vergleicht.
    *
    * @param element
    * @return
    */
  def singleSet(element: Int): Set = i => i == element

  /**
    * Contains liefert true, wenn ein Set ein Element beinhaltet,
    * false, wenn es das nicht tut.
    *
    * @param s
    * @param element
    * @return
    */
  def contains(s: Set, element: Int): Boolean = s(element)

  /**
    * Union gibt ein neues Set zurück, welches alle Elemente
    * aus den beiden übergebenen Sets beinhaltet.
    *
    * @param s
    * @param t
    * @return
    */
  def union(s: Set, t: Set): Set = i => s(i) || t(i)

  /**
    * Intersect gibt ein neues Set zurück, welches
    * alle Elemente beinhaltet, die in beiden übergebenen
    * Sets vorkommen.
    *
    * @param s
    * @param t
    * @return
    */
  def intersect(s: Set, t: Set): Set = i => s(i) && t(i)

  /**
    * Diff gibt ein neues Set zurück, welches alle Elemente
    * beinhaltet, die in `s` vorkommen, aber nicht in `t`.
    *
    * @param s
    * @param t
    * @return
    */
  def diff(s: Set, t: Set): Set = i => s(i) && !t(i)

  /**
    * Filter gibt ein neues Set zurück, welches alle Elemente
    * beinhaltet, für die die Funktion `f` true zurück gibt.
    *
    * @param s
    * @param f
    * @return
    */
  def filter(s: Set, f: Int => Boolean): Set = i => s(i) && f(i)


  /**
    * Da wir nicht auf einer "richtigen" ;-) Datenstruktur arbeiten,
    * müssen wir für die folgenen Methoden einen kleinen Trick anwenden
    * und führen einen maximalen und minimalen Wertebereich für das Set
    * ein. -/+ 1000
    */
  val bound = 1000

  /**
    * Forall prüft ob alle Elemente im Set auf das Prädikat `p` passt.
    * Hinweis: Zu prüfen ist im Wertebereich -/+ `bound`
    *
    * @param s
    * @param p
    * @return
    */
  def forall(s: Set, p: Int => Boolean): Boolean = {
    @tailrec
    def step(acc: Boolean, iter: Int): Boolean = {
      if(iter >= bound) acc
      else if(contains(s, iter)) step(acc && p(iter), iter + 1)
      else step(acc, iter + 1)
    }

    step(true, -bound)
  }

  /**
    * Exists prüft ob mindestens ein Element im Set auf das Prädikat
    * `p` passt. Hinweis: Zu prüfen ist im Wertebereich -/+ `bound`.
    *
    * @param s
    * @param p
    * @return
    */
  def exists(s: Set, p: Int => Boolean): Boolean = {
    @tailrec
    def step(acc: Boolean, iter: Int): Boolean = {
      if(iter >= bound) acc
      else if(contains(s, iter)) step(acc || p(iter), iter + 1)
      else step(acc, iter + 1)
    }

    step(false, -bound)
  }


  /**
    * Map gibt ein neues Set zurück, bei dem jedes Element im Set
    * mit der Funktion `f` transfomiert wurde.
    * Hinweis: Zu prüfen ist im Wertebereich -/+ `bound`.
    *
    * @param s
    * @param f
    * @return
    */
  def map(s: Set, f: Int => Int): Set = {
    @tailrec
    def step(acc: Set, iter: Int): Set = {
      if(iter >= bound) acc
      else if(contains(s, iter)) step(union(acc, singleSet(f(iter))), iter + 1)
      else step(acc, iter + 1)
    }

    step(emptySet, -bound)
  }

  /**
    * toString liefert eine Stringrepräsentation des Sets.
    * Hinweis: Zu prüfen ist im Wertebereich -/+ `bound`.
    *
    * @param s
    * @return
    */
  def createString(s: Set): String = {
    @tailrec
    def step(acc: ListBuffer[String], iter: Int): ListBuffer[String] = {
      if (iter >= bound) acc
      else if (contains(s, iter)) step(acc += iter.toString, iter + 1)
      else step(acc, iter + 1)
    }

    "{" + step(new ListBuffer(), -bound).mkString(", ") + "}"
  }

}
