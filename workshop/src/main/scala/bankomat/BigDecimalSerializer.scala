package bankomat

import net.liftweb.json.{MappingException, _}

import scala.math.BigDecimal.RoundingMode

/**
  * Created by Christian on 03.04.2016.
  */
class BigDecimalSerializer extends Serializer[BigDecimal] {
  val BigDecimalClass = classOf[BigDecimal]

  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), BigDecimal] = {

    case (TypeInfo(BigDecimalClass, _), JObject(fields)) => {
      fields match {
        case JField("scale", JInt(scale)) :: JField("value", JString(v)) :: Nil => {
          BigDecimal(v).setScale(scale.toInt, RoundingMode.HALF_UP)
        }
        case _ => throw new MappingException("Could not deserialize BigDecimal.")
      }
    }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case decimal: BigDecimal => JObject(
      JField("scale", JInt(decimal.scale)) :: JField("value", JString(decimal.toString())) :: Nil
    )
  }
}
