import spray.revolver.RevolverPlugin._
import AssemblyKeys._

name := "akka-training"

version := "0.0.1"

scalaVersion := "2.10.4"

val sprayV = "1.3.2"

resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases" at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq(
  "com.typesafe"            % "config"                    % "1.2.1",
  "com.typesafe.akka"      %% "akka-actor"                % "2.3.6",
  "com.typesafe.akka"      %% "akka-cluster"              % "2.3.6",
  "com.typesafe.akka"      %% "akka-slf4j"                % "2.3.6",
  "ch.qos.logback"          % "logback-classic"           % "1.1.2",
  "com.typesafe.akka"      %% "akka-testkit"              % "2.3.6"   % "test",
  "org.scalatest" 	    % "scalatest_2.10"            % "2.2.1"   % "test",
  "junit"                   % "junit"                     % "4.11"    % "test",
  "io.spray"            %%  "spray-can"     % sprayV,
  "io.spray"            %%  "spray-routing" % sprayV,
  "io.spray"            %%  "spray-testkit" % sprayV  % "test"
)


seq(Revolver.settings: _*)

assemblySettings

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture"
)
