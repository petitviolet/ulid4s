import xerial.sbt.Sonatype.GitHubHosting

sonatypeProfileName := "net.petitviolet"
organization := "net.petitviolet"

publishMavenStyle in Global := true

val githubAccount = "petitviolet"
val githubProject = "ulid4s"

// Add sonatype repository settings
publishTo in Global := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

licenses in Global := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

sonatypeProjectHosting in Global := Some(GitHubHosting(githubAccount, githubProject, "mail@petitviolet.net"))

homepage in Global := Some(url(s"https://github.com/$githubAccount/$githubProject"))
scmInfo in Global := Some(ScmInfo(url(s"https://github.com/$githubAccount/$githubProject"),
                            s"git@github.com:$githubAccount/$githubProject.git"))
developers in Global := List(Developer("petitviolet",
                             "Hiroki Komurasaki",
                             "mail@petitviolet.net",
                             url("https://www.petitviolet.net")))
