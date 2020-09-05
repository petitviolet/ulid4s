lazy val VERSION = "0.4.1"

def commonSettings(_name: String) = Seq(
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
  ),
  name := _name,
  organization := "net.petitviolet",
  version := VERSION,
  scalaVersion := "2.13.3",
  crossScalaVersions := Seq("2.11.12", "2.12.12", "2.13.3"),
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
    publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
    testOptions in Test += Tests.Argument(
      TestFrameworks.ScalaTest, "-u", {
        val dir = System.getenv("CI_REPORTS")
        if(dir == null) "target/reports" else dir
      })
  )

lazy val benchmark = (project in file("benchmark"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings("benchmark"))
  .dependsOn(ulid4s)


