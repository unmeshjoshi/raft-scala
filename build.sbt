import Settings._

val `raft-scala` = project
  .in(file("."))
  .enablePlugins(DeployApp, DockerPlugin)
  .settings(defaultSettings: _*)
  .settings(
    libraryDependencies ++= Dependencies.Service
  )


