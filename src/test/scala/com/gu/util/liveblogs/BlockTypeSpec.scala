package com.gu.util.liveblogs

import org.specs2.mutable.Specification

class BlockTypeSpec extends Specification {
  "fromClasses" should {
    "create a KeyEvent if the class list contains key-event" in {
      val classes = List("is-key-event", "is-summary", "bob")

      BlockType.fromClasses(classes) mustEqual KeyEvent
    }

    "create a Summary if the class list contains is-summary but not key-event" in {
      val classes = List("whatever", "is-summary")

      BlockType.fromClasses(classes) mustEqual Summary
    }

    "create a BlogPost if neither key-event nor is-summary is present in classes" in {
      BlockType.fromClasses(List("hello", "world")) mustEqual Blog
    }
  }
}
