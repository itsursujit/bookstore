import sbt.Keys.version

name := "bookstore"
lazy val commonSettings = Seq(
  organization := "me.archdev",
  version := "1.0.0",
  scalaVersion := "2.12.3",

libraryDependencies ++= {
    val akkHttpV = "10.0.10"
    val akkaV = "2.5.6"
    val scalaTestV = "3.0.4"
    val slickVersion = "3.2.1"
    val circeV = "0.8.0"
    val sttpV = "0.0.20"
    Seq(
      //Akka
      "com.typesafe.akka" %% "akka-actor" % akkaV,
      "com.typesafe.akka" %% "akka-testkit" % akkaV,
      "com.typesafe.akka" %% "akka-stream" % akkaV,
      "com.typesafe.akka" %% "akka-stream-testkit" % akkaV % Test,

      // HTTP server
      "com.typesafe.akka" %% "akka-http" % akkHttpV,
      "com.typesafe.akka" %% "akka-http-core" % akkHttpV,



      // Support of CORS requests, version depends on akka-http
      "ch.megard" %% "akka-http-cors" % "0.2.2",

      // SQL generator
      "com.typesafe.slick" %% "slick" % slickVersion,

      // Postgres driver
      "org.postgresql" % "postgresql" % "42.1.4",

      //MySql Driver

      "mysql" % "mysql-connector-java" % "5.1.44",

      // Migration for SQL databases
      "org.flywaydb" % "flyway-core" % "4.2.0",

      // Connection pool for database
      "com.zaxxer" % "HikariCP" % "2.7.0",

      // Encoding decoding sugar, used in passwords hashing
      "com.roundeights" %% "hasher" % "1.2.0",

      // Parsing and generating of JWT tokens
      "com.pauldijou" %% "jwt-core" % "0.14.0",

      // Config file parser
      "com.github.pureconfig" %% "pureconfig" % "0.8.0",

      // JSON serialization library
      "io.circe" %% "circe-core" % circeV,
      "io.circe" %% "circe-generic" % circeV,
      "io.circe" %% "circe-parser" % circeV,

      // Sugar for serialization and deserialization in akka-http with circe
      "de.heikoseeberger" %% "akka-http-circe" % "1.18.0",

      // Validation library
      "com.wix" %% "accord-core" % "0.7.1",

      // Http client, used currently only for IT test
      "com.softwaremill.sttp" %% "core" % sttpV % Test,
      "com.softwaremill.sttp" %% "akka-http-backend" % sttpV % Test,
      "com.softwaremill.akka-http-session" %% "core" % "0.5.1",
      "com.softwaremill.akka-http-session" %% "jwt"  % "0.5.1",

      "org.scalatest" %% "scalatest" % scalaTestV % Test,
      "com.typesafe.akka" %% "akka-http-testkit" % akkHttpV % Test,
      "ru.yandex.qatools.embed" % "postgresql-embedded" % "2.4" % Test,
      "org.mockito" % "mockito-all" % "1.9.5" % Test,

      //Typesafe config
      "com.iheart" %% "ficus" % "1.4.2"
    )
  }
)

lazy val root = (project in file(".")).
  aggregate(common, bookServices, userServices, creditServices, orderServices, server)

lazy val common = (project in file("common")).
  settings(commonSettings: _*)

lazy val bookServices = (project in file("book-services")).
  settings(commonSettings: _*).
  dependsOn(common)

lazy val server = (project in file("server")).
  settings(commonSettings: _*).
  dependsOn(common)

lazy val userServices = (project in file("user-services")).
  settings(commonSettings: _*).
  dependsOn(common)

lazy val creditServices = (project in file("credit-services")).
  settings(commonSettings: _*).
  dependsOn(common)

lazy val orderServices = (project in file("order-services")).
  settings(commonSettings: _*).
  dependsOn(common)



enablePlugins(UniversalPlugin)
enablePlugins(DockerPlugin)

