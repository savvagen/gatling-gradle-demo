package org.example

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._

class MyFirstSimulation extends BaseSimulation {


  def myFirstScn: ChainBuilder =
    exec(
      http("Get Articles").get("/api/articles/")
        .header("Content Type", "application/json")
        .check(status.is(200))
    ).pause(1.second)


  val scn = scenario("Articles Scn").exec(myFirstScn)

  setUp(
    scn.inject(constantUsersPerSec(1).during(15 seconds)).protocols(httpConfig)
  )


}
