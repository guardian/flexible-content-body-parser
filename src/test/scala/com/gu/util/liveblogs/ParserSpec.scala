package com.gu.util.liveblogs

import org.specs2.mutable.Specification
import org.joda.time.{DateTimeZone, DateTime}

class ParserSpec extends Specification {
  "parse" should {
    "correctly convert Live Blog HTML into blocks" in new BlockFixtures {
      val blocks = Parser.parse(liveBlogHtml.toString())

      blocks.length mustEqual 3
      blocks(0).postType mustEqual Blog
      blocks(1).postType mustEqual Summary
      blocks(2).postType mustEqual KeyEvent
    }

    "correctly parse datetimes" in new BlockFixtures {
      val blocks = Parser.parse(liveBlogHtml.toString())

      blocks(0).publishedDateTime.withMillisOfSecond(0) mustEqual
        new DateTime(2013, 9, 18, 14, 27, 12, DateTimeZone.UTC)

      blocks(1).publishedDateTime.withMillisOfSecond(0) mustEqual
        new DateTime(2013, 9, 19, 9, 2, 34, DateTimeZone.UTC)
    }
  }
}
