import Dependencies._

// lazy val jmpq = RootProject(uri("git://github.com/inwc3/JMPQ-v3.git"))
// lazy val jmpq = RootProject(file("../../JMPQ-v3"))

lazy val root = (project in file("."))
  // .dependsOn(jmpq)
  .settings(
    inThisBuild(List(
      organization := "de.inwc",
      scalaVersion := "2.12.2",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "MpqDiff",
    libraryDependencies += scalaTest % Test,

    // https://mvnrepository.com/artifact/systems.crigges/jmpq3
    libraryDependencies += "systems.crigges" % "jmpq3" % "1.0.5"
  )
