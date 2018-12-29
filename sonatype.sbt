import xerial.sbt.Sonatype.GitHubHosting

sonatypeProfileName := "net.petitviolet"
organization := "net.petitviolet"

publishMavenStyle := true

publishTo in ThisBuild := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

sonatypeProjectHosting := Some(GitHubHosting("petitviolet", "ulid4s", "mail@petitviolet.net"))

developers := List(
  Developer("net.petitviolet", "Hiroki Komurasaki", "mail@petitviolet.net", url("https://www.petitviolet.net"))
)

// To sync with Maven central, you need to supply the following information:
pomExtra in Global := {
  <url>https://github.com/petitviolet/ulid4s</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>
  <scm>
    <connection>scm:git:github.com/petitviolet/ulid4s</connection>
    <developerConnection>scm:git:git@github.com:petitviolet/ulid4s</developerConnection>
    <url>github.com/petitviolet/ulid4s</url>
  </scm>
  <developers>
    <developer>
      <id>net.petitviolet</id>
      <name>Hiroki Komurasaki</name>
      <url>https://www.petitviolet.net</url>
    </developer>
  </developers>
}
