package de.tarent

import org.scalatra._
import scalate.ScalateSupport
import org.fusesource.scalate.{Binding, TemplateEngine}
import org.fusesource.scalate.layout.DefaultLayoutStrategy
import javax.servlet.http.HttpServletRequest

import org.scalatra.json.JacksonJsonSupport

import collection.mutable

trait ScalatraworkshopStack extends ScalatraServlet with JacksonJsonSupport {

  case class Error(msg: String)

  before() {
    contentType = formats("json")
  }

  notFound {
    response.setStatus(404)

  }

}
