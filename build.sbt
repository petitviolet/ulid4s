organization := "net.petitviolet"
name := "ulid4s"
version := "0.1.0"

scalaVersion := "2.12.8"
crossScalaVersions := Seq("2.11.11", "2.12.8")
scalafmtOnCompile := true
scalafmtSbtCheck := true

publishConfiguration := publishConfiguration.value.withOverwrite(true)
publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)
