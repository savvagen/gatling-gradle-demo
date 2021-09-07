package org.example.simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder
import org.example.protocols.http.BaseSimulation
import org.example.scenarios.MetricsExporterScn

import scala.concurrent.duration._

class PromSimulation extends BaseSimulation {

  var httpProtocol: HttpProtocolBuilder = http.baseUrl("http://localhost:9091")
    .acceptCharsetHeader("utf-8")
    .disableWarmUp
    .disableCaching


  val metricsPublishScn: ScenarioBuilder = scenario("Pushgateway scenario")
    .exec(MetricsExporterScn.metricsExportScn())

  setUp(metricsPublishScn.inject(
    constantConcurrentUsers(1) during 30.minutes )
  ).protocols(httpProtocol)

}
