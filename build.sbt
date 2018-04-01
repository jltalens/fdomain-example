import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import scalariform.formatter.preferences._

lazy val appVersion = "0.1"

lazy val scalaV = "2.12.4"

lazy val scalaC = "-Ypartial-unification"

val Versions = new {
  val cats = "1.0.1"
  val monix = "3.0.0-M3"
  val http4s = "0.18.3"
  val circe = "0.9.1"
  val fs2 = "0.10.3"
}

lazy val deps = Seq(
  "org.typelevel" %% "cats-core" % Versions.cats,
  "org.typelevel" %% "cats-free" % Versions.cats,
  "io.monix" %% "monix" % Versions.monix,
  "org.http4s" %% "http4s-dsl" % Versions.http4s,
  "org.http4s" %% "http4s-blaze-server" % Versions.http4s,
  "org.http4s" %% "http4s-blaze-client" % Versions.http4s,
  "org.http4s" %% "http4s-circe" % Versions.http4s,
  "io.circe" %% "circe-core" % Versions.circe,
  "io.circe" %% "circe-generic" % Versions.circe,
  "io.circe" %% "circe-parser" % Versions.circe,
  "io.circe" %% "circe-java8" % Versions.circe,
  "co.fs2" %% "fs2-core" % Versions.fs2,
  "co.fs2" %% "fs2-io" % Versions.fs2
)

lazy val `fdomain-example` = (project in file("."))
  .settings(
    name := "fdomain-example",
    scalaVersion := scalaV,
    version := appVersion,
    scalacOptions += scalaC,
    ScalariformKeys.preferences := PreferencesImporterExporter.loadPreferences((file(".") / "formatter.preferences").getPath),
    libraryDependencies ++= deps
  )
