name := "dev"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.32"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.15"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.15"
libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % "2.5.21"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.0"
libraryDependencies += "org.apache.spark" %% "spark-hive" % "2.3.0"

libraryDependencies += "com.lihaoyi" %% "ujson" % "1.4.3"
libraryDependencies += "com.password4j" % "password4j" % "1.5.4"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"