import Dependencies._

organization := "com.gu"

name := "flexible-content-body-parser"

version := "0.3-SNAPSHOT"

scalaVersion := "2.11.1"

resolvers ++= Seq(
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  jSoup,
  nscalaTime,
  clapper,
  playJson,
  specs2 % "test"
)

publishTo <<= (version) { version: String =>
  val publishType = if (version.endsWith("SNAPSHOT")) "snapshots" else "releases"
  Some(
    Resolver.file(
      "guardian github " + publishType,
      file(System.getProperty("user.home") + "/guardian.github.com/maven/repo-" + publishType)
    )
  )
}
