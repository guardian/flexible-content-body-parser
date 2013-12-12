package com.gu.util.liveblogs

import org.specs2.mutable.Specification

class ParserSpec extends Specification {
  "parse" should {
    "correctly convert Live Blog HTML into blocks" in new BlockFixtures {
      val blocks = Parser.parse(liveBlogHtml.toString())

      blocks.length mustEqual 3
      blocks(0).postType mustEqual Blog
      blocks(1).postType mustEqual Summary
      blocks(2).postType mustEqual KeyEvent
    }
  }
}
