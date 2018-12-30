logLevel := Level.Warn

resolvers ++= Seq(
  Resolver.bintrayIvyRepo("scalameta", "maven"),
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

addSbtPlugin("pl.project13.scala" % "sbt-jmh" % "0.3.4")

addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "1.5.1")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.2")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0")

addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.2.7")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")
