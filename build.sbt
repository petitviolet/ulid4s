lazy val VERSION = "0.2.0"

def commonSettings(_name: String) = Seq(
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
  ),
  name := _name,
  organization := "net.petitviolet",
  version := "0.2.0",
  scalaVersion := "2.12.8",
  crossScalaVersions := Seq("2.11.11", "2.12.8"),
  scalafmtSbtCheck := true,
  scalafmtConfig := Some(file(".scalafmt.conf")),
  scalafmtOnCompile := true,
)

lazy val root = (project in file("."))
  .settings(commonSettings("ulid4s-root"))
  .aggregate(ulid4s)

lazy val ulid4s = (project in file("ulid4s"))
  .settings(commonSettings("ulid4s"))
  .settings(
    publishConfiguration := publishConfiguration.value.withOverwrite(true),
    publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)
  )

lazy val benchmark = (project in file("benchmark"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings("benchmark"))
  .dependsOn(ulid4s)


