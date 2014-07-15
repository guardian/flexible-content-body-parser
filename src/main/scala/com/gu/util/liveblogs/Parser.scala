package com.gu.util.liveblogs

import org.joda.time.{DateTimeZone, DateTime}
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import grizzled.slf4j.Logging
import org.joda.time.format.ISODateTimeFormat

import scala.collection.JavaConversions._
import scala.util.Try

/** Live blogs are converted into a blob of HTML before being inserted into Content API. This will change in the future,
  * but for now we have to parse that blob to reconstruct the separate blocks.
  */
object Parser {
  def parse(body: String): Seq[Block] = {
    val html = Jsoup.parseBodyFragment(body)

    (for {
      element <- html.select("div.block").iterator()
    } yield Block.fromElement(element)).toSeq
  }
}

object BlockType {
  val ClassMap = Map(
    "is-key-event" -> KeyEvent,
    "is-summary" -> Summary
  )

  val Default = Blog

  def fromClasses(classes: List[String]) = {
    classes collectFirst {
      case klass if ClassMap contains klass => ClassMap(klass)
    } getOrElse Default
  }
}

sealed trait BlockType

case object Blog extends BlockType
case object KeyEvent extends BlockType
case object Summary extends BlockType

object Block extends Logging {
  private def extractTime(node: Element) = {
    val dateTimeString = node.select("time").get(0).attr("datetime")

    Try {
      ISODateTimeFormat.dateTime().parseDateTime(dateTimeString)
    } orElse Try {
      ISODateTimeFormat.dateTimeNoMillis().parseDateTime(dateTimeString)
    } map {
      _.toDateTime(DateTimeZone.UTC).withMillisOfSecond(0)
    }
  }

  private[liveblogs] def fromElement(bodyDiv: Element): Block = {
    val Some(publishedAt) = Try(bodyDiv.select("p.published-time").get(0)).flatMap(extractTime).toOption
    val updatedAt = Try(bodyDiv.select("p.updated-time").get(0)).flatMap(extractTime).toOption

    Block(
      bodyDiv.id(),
      Try(bodyDiv.select("h2.block-title")).toOption.map(_.text()).filterNot(_.isEmpty),
      publishedAt,
      updatedAt,
      bodyDiv.select("div.block-elements").map(_.html()) mkString "\n",
      BlockType.fromClasses(bodyDiv.attr("class").split("\\s+").toList)
    )
  }
}

case class Block(
    id: String,
    title: Option[String],
    publishedDateTime: DateTime,
    lastUpdatedDateTime: Option[DateTime],
    body: String,
    postType: BlockType
)
