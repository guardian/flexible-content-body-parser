package com.gu.util.liveblogs

import org.joda.time.{DateTimeZone, DateTime}
import lib.Xml._
import scala.xml.Node
import scala.annotation.tailrec
import grizzled.slf4j.Logging
import com.gu.util.liveblogs.lib.TagSoupHelper
import org.joda.time.format.ISODateTimeFormat
import scala.util.Try

/** Live blogs are converted into a blob of HTML before being inserted into Content API. This will change in the future,
  * but for now we have to parse that blob to reconstruct the separate blocks.
  */
object Parser {
  def parse(body: String): Seq[Block] = for {
    bodyDiv <- TagSoupHelper.parseHtml(body) \\ "body" \ "div"
  } yield Block.fromNode(bodyDiv)
}

object BlockType {
  val ResolutionOrder = List(
    "is-key-event" -> KeyEvent,
    "is-summary" -> Summary
  )

  val Default = Blog

  def fromClasses(classes: List[String]) = {
    @tailrec def iter(resolvers: List[(String, BlockType)]): Option[BlockType] = resolvers match {
      case (className, blockType) :: _ if classes contains className => Some(blockType)
      case _ :: rest => iter(rest)
      case Nil => None
    }

    iter(ResolutionOrder) getOrElse Default
  }
}

sealed trait BlockType

case object Blog extends BlockType
case object KeyEvent extends BlockType
case object Summary extends BlockType

object Block extends Logging {
  private def extractTime(node: Node) = {
    val dateTimeString = (node \ "time" \ "@datetime").text

    Try {
      ISODateTimeFormat.dateTime().parseDateTime(dateTimeString)
    } orElse Try {
      ISODateTimeFormat.dateTimeNoMillis().parseDateTime(dateTimeString)
    } map {
      _.toDateTime(DateTimeZone.UTC).withMillisOfSecond(0)
    }
  }

  private[liveblogs] def fromNode(bodyDiv: Node): Block = {
    val Some(publishedAt) = (bodyDiv \ "p").find(_.hasClass("published-time")).map(extractTime)
    val updatedAt = (bodyDiv \ "p").find(_.hasClass("updated-time")).flatMap(extractTime(_).toOption)

    Block(
      (bodyDiv \ "@id").text,
      (bodyDiv \\ "h2").find(_.hasClass("block-title")).map(_.text),
      publishedAt.get,
      updatedAt,
      (bodyDiv \ "div").find(_.hasClass("block-elements")).map(_.children.toHtml5String) getOrElse {
        logger.error(s"No block-elements inside LiveBlog block: ${bodyDiv.toString()}")
        ""
      },
      BlockType.fromClasses(bodyDiv.classes)
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
