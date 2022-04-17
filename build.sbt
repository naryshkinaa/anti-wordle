name := "anti-wordle"

version := "0.1"

scalaVersion := "2.13.8"

val AkkaVersion = "2.6.18"
val AkkaHttpVersion = "10.2.9"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.1.4",
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.2"
)
