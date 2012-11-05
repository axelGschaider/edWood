name := "edWood"

version := "0.1"

scalaVersion := "2.9.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers ++= Seq(
  "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
)

resolvers += "Sonatype OSS Releases" at 
  "http://oss.sonatype.org/content/repositories/releases/"

libraryDependencies +=   "com.typesafe.akka" % "akka-actor" % "2.0.3"

libraryDependencies +=   "com.typesafe.akka" % "akka-testkit" % "2.0.3"

libraryDependencies +=   "log4j" % "log4j" % "1.2.16"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "6.0.3"

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.10.0" % "test"
)

//libraryDependencies +=
//  "org.scalamock" %% "scalamock-scalatest-support" % "latest.integration"


mainClass := Some("edWood.EdWood")

