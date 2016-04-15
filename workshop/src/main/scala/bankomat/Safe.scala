package bankomat

import java.io.{File, PrintWriter}

import net.liftweb.json.{Serialization, _}

import scala.io.Source

class Safe(private val file: File) {
  implicit val formats = DefaultFormats + new BigDecimalSerializer

  def persist(account: Map[String, Account]) = {
    new PrintWriter(file) {
      write(Serialization.writePretty(account))
      close()
    }
  }

  def load: Map[String, Account] = {
    if(file.exists()) {
      val json = Source.fromFile(file).getLines().reduceLeft( _ + _ )
      Serialization.read[Map[String, Account]](json)
    } else {
      file.createNewFile()
      persist(Map())
      load
    }
  }
}
