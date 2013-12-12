package com.gu.util.liveblogs

import org.specs2.mutable.Specification
import org.joda.time.{DateTimeZone, DateTime}

class BlockSpec extends Specification {
  "fromNode" should {
    "correctly construct blog blocks from HTML" in new BlockFixtures {
      val block = Block.fromNode(blogBlockHtml)

      block.id mustEqual "block-5239b800e4b04c15d3d31503"
      block.title mustEqual None
      block.publishedDateTime mustEqual new DateTime(2013, 9, 18, 14, 27, 12, 0, DateTimeZone.UTC)
      block.lastUpdatedDateTime mustEqual None
      block.postType mustEqual Blog
      block.body must contain("just to score political points")
    }

    "correctly construct summary blocks from HTML" in new BlockFixtures {
      val block = Block.fromNode(summaryBlockHtml)

      block.id mustEqual "block-523aabb9e4b0b2ce000658b3"
      block.title mustEqual Some("No taper: What the papers say")
      block.publishedDateTime mustEqual new DateTime(2013, 9, 19, 8, 2, 34, 0, DateTimeZone.UTC)
      block.lastUpdatedDateTime mustEqual None
      block.postType mustEqual Summary
      block.body must contain("Photograph: City AM")
    }

    "correctly construct key events from HTML" in new BlockFixtures {
      val block = Block.fromNode(keyEventBlockHtml)

      block.id mustEqual "block-523aaf9be4b0107334baad7b"
      block.title mustEqual Some("Tensions high in Greece ahead of murdered rapper's funeral")
      block.publishedDateTime mustEqual new DateTime(2013, 9, 19, 8, 17, 56, 0, DateTimeZone.UTC)
      block.lastUpdatedDateTime mustEqual Some(new DateTime(2013, 9, 19, 8, 18, 14, 0, DateTimeZone.UTC))
      block.postType mustEqual KeyEvent
      block.body must contain("a huge turnout of anti-fascist friends")
    }
  }
}
