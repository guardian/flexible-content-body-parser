package com.gu.util.liveblogs

import org.specs2.specification.Scope

trait BlockFixtures extends Scope {
  val blogBlockHtml = <div id="block-5239b800e4b04c15d3d31503" class="block">
    <p class="block-time published-time">
      <time datetime="2013-09-18T15:27:12.156+01:00">3.27pm <span class="timezone">BST</span></time>
    </p>
    <div class="block-elements">
      <p>Clegg says Labour are miserable when there is good news on the economy. They would rather it was bad,
        just to score political points.</p>
    </div>
  </div>

  val summaryBlockHtml = <div id="block-523aabb9e4b0b2ce000658b3" class="block is-summary">
    <p class="block-time published-time">
      <time datetime="2013-09-19T09:02:34.353Z">9.02am <span class="timezone">BST</span></time></p>
    <h2 class="block-title">No taper: What the papers say</h2>
    <div class="block-elements">
      <figure class="element element-image" data-media-id="gu-fc-d31c76f8-357f-47b4-9779-00acfe6f404d">
        <img src="http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2013/9/19/1379577672372/e05a8f27
                      -38d1-464e-9a00-5b239407363d-460x400.png"
             alt="City AM, front page, Sept 19 2013"
             width="460"
             height="400"
             class="gu-image"
        />
        <figcaption>Photograph: City AM</figcaption>
      </figure>
    </div>
  </div>

  val keyEventBlockHtml = <div id="block-523aaf9be4b0107334baad7b" class="block is-key-event">
    <p class="block-time published-time">
      <time datetime="2013-09-19T09:17:56.274+01:00">9.17am <span class="timezone">BST</span></time>
    </p>
    <h2 class="block-title">Tensions high in Greece ahead of murdered rapper's funeral</h2>
    <div class="block-elements">
      <p><strong>Greek media are reporting that the funeral of murdered hip hop artist Pavlos Fyssas is about to
        take place in Athens</strong>.</p>
      <p><strong>Helena Smith</strong>, our correspondent, says a huge turnout of anti-fascist friends and
        supporters is expected following a night of violent clashes across Greece.&nbsp;</p>
      <p>This video clip shows a group of men throwing stones, while standing <strong>alongside</strong>
        Greek riot policemen:</p>
      <figure class="element element-video" data-canonical-url="http://www.youtube.com/watch?v=fH9CLuJNmtE">
        <iframe width="460"
                height="345"
                src="http://www.youtube.com/embed/fH9CLuJNmtE?wmode=opaque&amp;feature=oembed"
                frameborder="0"
                allowfullscreen=""></iframe>
      </figure>
    </div>
    <p class="block-time updated-time">Updated <time datetime="2013-09-19T09:18:14.877+01:00">at 9.18am BST</time>
    </p>
  </div>

  val liveBlogHtml = <html><body>{ blogBlockHtml ++ summaryBlockHtml ++ keyEventBlockHtml }</body></html>
}
