package org.example.simulations

import com.microsoft.playwright.BrowserType.LaunchOptions
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import org.example.protocols.playwright.Predef._
import org.example.protocols.playwright.protocol.PlaywrightProtocol

import scala.language.postfixOps

class PlaywrightPluginSimulation extends Simulation {

  val playwrightProtocol: PlaywrightProtocol = pl
    .baseUrl("http://127.0.0.1:8001")
    .browser("chromium")
    .launchOptions(new LaunchOptions().setHeadless(false).setTimeout(30000).setSlowMo(1000))

  val scn: ScenarioBuilder = scenario("test")
    .exec(playwright("Articles Page").navigate("/articles/"))
    .pause(2)
    .exec(playwright("Authors Page").navigate("/articles/1"))

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(playwrightProtocol)

}
