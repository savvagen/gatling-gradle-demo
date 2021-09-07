package org.example.simulations

import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.core.structure.{PopulationBuilder, ScenarioBuilder}
import org.example.protocols.http.BaseSimulation
import org.example.scenarios.{FailOverScenario, PostReaderScenario, PostWriterScenario, TodoWriterScenario}

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class LoadSimulation extends BaseSimulation {

  val configName: String = getProperty("CONFIG", "configs/load")
  val config: Config = ConfigFactory.load(configName).withFallback(ConfigFactory.load("configs/load"))
  // Load Model
  val rampUpTime: Int = config.getInt("performance.rampUpTime")
  val testDuration: Int = config.getInt("performance.testDuration")
  val maxDuration: Int = config.getInt("performance.maxDuration")
  val maxUsers: Int = config.getInt("performance.maxUsers")
  val scenarioPostNumber: Int = config.getInt("performance.scenario.postsNumber")
  val scenarioCommentsNumber: Int = config.getInt("performance.scenario.commentsNumber")
  // Assertions
  val responseTimeMaxLte: Int           = config.getInt("performance.global.assertions.responseTime.max.lte")
  val failedRequestsPercentLte: Int     = config.getInt("performance.global.assertions.failedRequests.percent.lte")
  val successfulRequestsPercentGte: Int = config.getInt("performance.global.assertions.successfulRequests.percent.gte")


  val failOverScenario: ScenarioBuilder = scenario("Fail Over Scn")
    .exec(FailOverScenario.failAction)

  val postWriterScn: ScenarioBuilder = scenario("Post Writer Scn")
    .exec(PostWriterScenario.postWriterScn(username, password, postsRange = (3 to 5)))

  val commentWriterScn: ScenarioBuilder = scenario("Comment Writer Scn")
    .exec(PostWriterScenario.commentWriterScn())

  val postReaderScn: ScenarioBuilder = scenario("Post Reader Scn")
    .exec(PostReaderScenario.postReaderScn(username, password))

  val todoWriterScn: ScenarioBuilder = scenario("ToDo Writer Scn")
    .exec(TodoWriterScenario.todoWriterScn(username, password))

  def loadProfile(): List[PopulationBuilder] = {
    val scenarioList = List(
      postWriterScn.inject(
        rampConcurrentUsers(0) to maxUsers during (rampUpTime seconds),
        constantConcurrentUsers(maxUsers) during(maxDuration seconds)
      ),
      postReaderScn.inject(
        rampConcurrentUsers(0) to maxUsers during (rampUpTime seconds),
        constantConcurrentUsers(maxUsers) during(maxDuration seconds)
      ),
      commentWriterScn.inject(
        rampConcurrentUsers(0) to (maxUsers/2) during (rampUpTime seconds),
        constantConcurrentUsers(maxUsers/2) during(maxDuration seconds)
      ),
      todoWriterScn.inject(
        rampConcurrentUsers(0) to (maxUsers/3) during (rampUpTime seconds),
        constantConcurrentUsers(maxUsers/3) during(maxDuration seconds)
      )
    )
    if (exitOnFailure) return scenarioList.::(failOverScenario.inject(constantUsersPerSec(1).during(maxDuration.seconds)))
    else return scenarioList
  }

  setUp(loadProfile())
    .maxDuration(maxDuration.seconds)
    .assertions(
      global.responseTime.mean.lt(responseTimeMaxLte),
      global.responseTime.percentile4.lt(responseTimeMaxLte),
      global.successfulRequests.percent.gte(successfulRequestsPercentGte),
      global.failedRequests.percent.lte(failedRequestsPercentLte),
      details("GET /api/get_token").responseTime.max.lt(responseTimeMaxLte),
      details("GET /api/comments").responseTime.max.lt(responseTimeMaxLte),
      details("GET /api/comments").responseTime.percentile4.lt(responseTimeMaxLte)
    )
    .protocols(httpConf)

}
