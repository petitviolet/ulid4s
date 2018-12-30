import xerial.sbt.Sonatype.GitHubHosting

sonatypeProfileName := "net.petitviolet"
organization := "net.petitviolet"

publishMavenStyle := true

val githubAccount = "petitviolet"
val githubProject = "ulid4s"

// Add sonatype repository settings
publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

sonatypeProjectHosting := Some(GitHubHosting(githubAccount, githubProject, "mail@petitviolet.net"))

homepage := Some(url(s"https://github.com/$githubAccount/$githubProject"))
scmInfo := Some(ScmInfo(url(s"https://github.com/$githubAccount/$githubProject"),
                            s"git@github.com:$githubAccount/$githubProject.git"))

developers := List(Developer("petitviolet",
                             "Hiroki Komurasaki",
                             "mail@petitviolet.net",
                             url("https://www.petitviolet.net")))
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
publishMavenStyle := true
