package de.tarent

import org.scalatra._

class MyScalatraServlet extends ScalatraworkshopStack {

  get("/") {
    """
      |{
      |  "hello": "world"
      |}
    """.stripMargin
  }

  post("/foobar") {

  }

}
