name := "questionnaire-flow"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

mainClass in (Compile, run) := Some("com.dreweaster.questionnaire.Main")