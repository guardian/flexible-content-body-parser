package com.gu.util.liveblogs.lib

import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
import java.io.StringReader

object TagSoupHelper {
  val Features: Seq[(String, Boolean)] = Seq(
    "http://www.ccil.org/~cowan/tagsoup/features/default-attributes" -> false,
    "http://www.ccil.org/~cowan/tagsoup/features/restart-elements" -> false
  )

  /** Use TagSoup to parse (and fix) real world HTML, converting it to Scala's internal XML format */
  def parseHtml(html: String) = {
    val factory = new SAXFactoryImpl()

    for ((k, v) <- Features) factory.setFeature(k, v)

    val parser = factory.newSAXParser()

    val source = new org.xml.sax.InputSource(new StringReader(html))
    val adapter = new scala.xml.parsing.NoBindingFactoryAdapter
    adapter.loadXML(source, parser)
  }
}