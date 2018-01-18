import sbt._

object Dependencies {

  val Version = "0.1-SNAPSHOT"
  val Service = Seq(
    Libs.`junit` % Test,
    Libs.`junit-interface` % Test,
    Libs.`mockito-core` % Test,
    Libs.`scalatest` % Test,
    Libs.`derby`,
    Libs.`artemis-client`,
    Libs.`jms`,
    Libs.`qpid`,
    Libs.`qpid-aqmp-client`,
    Libs.`qpid-jms-client`,
    Libs.`rabbitmq-aqmp-client`,
    Libs.`jedis`
  )
}