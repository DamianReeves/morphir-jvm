import sbt.Keys._
import sbt._

resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  Resolver.jcenterRepo
)

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    libraryDependencies ++= Dependencies.zioCommonDeps,
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )
  .aggregate(
    `morphir-ir`,
    `morphir-frontend-scala`
  )

lazy val commonSettings = Seq(
  name := "morphir-ir",
  version := "0.1.0",
  scalacOptions ++= Seq(
    "-deprecation",
    "-language:postfixOps",
    "-Ykind-projector",
    "-indent",
    "-Yexplicit-nulls",
    "-source",
    "future",
    "-Xfatal-warnings"
  ) ++ Seq("-rewrite", "-indent"),
  scalaVersion := "3.0.0"
)

lazy val `morphir-ir` = project
  .in(file("./morphir-ir"))
  .settings(
    name := "morphir-ir",
    version := "0.1.0",
    scalacOptions ++= Seq(
      "-language:postfixOps",
      "-Ykind-projector",
      "-Yexplicit-nulls",
      "-source",
      "future",
      "-Xfatal-warnings"
    ),
    libraryDependencies ++= Dependencies.zioCommonDeps ++ Seq(Dependencies.eu.timepit.refined),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework")),
    scalaVersion := "3.0.0"
  )

lazy val `morphir-frontend-scala` = project
  .in(file("./morphir-frontend/scala"))
  .dependsOn()
  .settings(
    name := "morphir-frontend-scala",
    version := "0.1.0",
    scalacOptions ++= Seq(
      "-language:postfixOps",
      "-Ykind-projector",
      "-Yexplicit-nulls",
      "-source",
      "future",
      "-Xfatal-warnings"
    ),
    libraryDependencies ++= Dependencies.zioCommonDeps,
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework")),
    scalaVersion := "3.0.0"
  )
