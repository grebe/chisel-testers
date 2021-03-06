organization := "edu.berkeley.cs"
version := "1.2-SNAPSHOT"
name := "Chisel.iotesters"

scalaVersion := "2.11.7"

// Provide a managed dependency on X if -DXVersion="" is supplied on the command line.
// The following are the default development versions, not the "release" versions.
val defaultVersions = Map(
  "chisel3" -> "3.1-SNAPSHOT",
  "firrtl" -> "1.1-SNAPSHOT",
  "firrtl-interpreter" -> "1.1-SNAPSHOT"
  )

libraryDependencies ++= Seq("chisel3","firrtl","firrtl-interpreter").map { dep: String =>
    "edu.berkeley.cs" %% dep % sys.props.getOrElse(dep + "Version", defaultVersions(dep))
}

libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "2.2.4",
                            "org.scalacheck" %% "scalacheck" % "1.12.4",
                            "com.github.scopt" %% "scopt" % "3.4.0")
    
publishMavenStyle := true

publishArtifact in Test := false
pomIncludeRepository := { x => false }

pomExtra := (<url>http://chisel.eecs.berkeley.edu/</url>
<licenses>
  <license>
    <name>BSD-style</name>
    <url>http://www.opensource.org/licenses/bsd-license.php</url>
    <distribution>repo</distribution>
  </license>
</licenses>
<scm>
  <url>https://github.com/ucb-bar/chisel-testers.git</url>
  <connection>scm:git:github.com/ucb-bar/chisel-testers.git</connection>
</scm>
<developers>
  <developer>
    <id>jackbackrack</id>
    <name>Jonathan Bachrach</name>
    <url>http://www.eecs.berkeley.edu/~jrb/</url>
  </developer>
</developers>)


publishTo <<= version { v: String =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) {
    Some("snapshots" at nexus + "content/repositories/snapshots")
  }
  else {
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }
}


resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

scalacOptions in (Compile, doc) <++= (baseDirectory, version) map { (bd, v) =>
  Seq("-diagrams", "-diagrams-max-classes", "25", "-sourcepath", bd.getAbsolutePath, "-doc-source-url", "https://github.com/ucb-bar/chisel-testers/tree/master/€{FILE_PATH}.scala")
}
