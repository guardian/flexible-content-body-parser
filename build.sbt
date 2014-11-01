import sbtrelease._
import ReleaseStateTransformations._
import Dependencies._

releaseSettings

sonatypeSettings

organization := "com.gu"

name := "flexible-content-body-parser"

scalaVersion := "2.11.4"

resolvers ++= Seq(
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  jSoup,
  nscalaTime,
  clapper,
  specs2 % "test"
)

description := "Scala library for converting flexible content blobs into structured content"

scmInfo := Some(ScmInfo(
  url("https://github.com/guardian/flexible-content-body-parser"),
  "scm:git:git@github.com:guardian/flexible-content-body-parser.git"
))

pomExtra := (
  <url>https://github.com/guardian/flexible-content-body-parser</url>
    <developers>
      <developer>
        <id>robertberry</id>
        <name>Robert Berry</name>
        <url>https://github.com/robertberry</url>
      </developer>
    </developers>
  )

licenses := Seq("Apache V2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

ReleaseKeys.crossBuild := true

ReleaseKeys.releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  ReleaseStep(
    action = state => Project.extract(state).runTask(PgpKeys.publishSigned, state)._1,
    enableCrossBuild = true
  ),
  setNextVersion,
  commitNextVersion,
  ReleaseStep(state => Project.extract(state).runTask(SonatypeKeys.sonatypeReleaseAll, state)._1),
  pushChanges
)
