package com.gu.util.liveblogs.lib

import net.liftweb.util.Html5
import scala.xml.{Node, NodeSeq}

object Xml {
  implicit class EnrichedNodeSeq(node: NodeSeq) {
    def children = node.flatMap(_.child.toList)

    def toHtml5String: String = node.map(_.toHtml5String).mkString
  }

  implicit class HtmlNode(node: Node) {
    def hasClass(className: String) = node.classes.contains(className)

    def classes = node.attribute("class").map(_.text.split("\\s+").toList) getOrElse Nil

    /** As a valid HTML5 string */
    def toHtml5String: String = Html5.toString(node)
  }
}
