package com.gu.util.liveblogs.lib

import org.specs2.mutable.Specification
import scala.xml.Text

class TagSoupHelperSpec extends Specification {
  "parse" should {
    "be able to read broken HTML" in {
      val parsed = TagSoupHelper.parseHtml(
        """
          |<p>Hello world
          |<p>This is a new line
          |<p>And so is this
        """.stripMargin)

      val paragraphs = parsed \\ "p"

      paragraphs.length mustEqual 3

      val expectedParagraphStrings = List(
        "Hello world",
        "This is a new line",
        "And so is this"
      )

      (for ((<p>{node: Text}</p>, string) <- paragraphs zip expectedParagraphStrings) yield {
        node.text must contain(string)
      }).reduce(_ and _)
    }
  }
}
