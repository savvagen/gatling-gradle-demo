package org.example.simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import org.example.protocols.http.BaseSimulation

import scala.concurrent.duration._
import scala.language.postfixOps

class FirstSimulation extends BaseSimulation {


  def myFirstScn: ChainBuilder =
    exec(
      http("Get Articles").get("/api/articles/")
        .header("Content Type", "application/json")
        .check(status.is(200))
    ).pause(1.second)


  val scn: ScenarioBuilder = scenario("Articles Scn").exec(myFirstScn)

  setUp(
    scn.inject(constantUsersPerSec(1).during(15 seconds)).protocols(httpConfig)
  )


}
