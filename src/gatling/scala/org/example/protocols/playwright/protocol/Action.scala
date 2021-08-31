package org.example.protocols.playwright.protocol

import io.gatling.commons.stats.{KO, OK}
import io.gatling.commons.util.Clock
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.action.{Action, ExitableAction}
import io.gatling.core.protocol.ProtocolComponentsRegistry
import io.gatling.core.session.Session
import io.gatling.core.stats.StatsEngine
import io.gatling.core.structure.ScenarioContext
import io.gatling.core.util.NameGen


class PlaywrightNavigateActionBuilder(functionName: String, path: String) extends ActionBuilder {

  private def components(protocolComponentsRegistry: ProtocolComponentsRegistry) =
    protocolComponentsRegistry.components(PlaywrightProtocol.UpperProtocolKey)

  override def build(ctx: ScenarioContext, next: Action): Action = {
    import ctx._
    val statsEngine = coreComponents.statsEngine
    val upperComponents = components(protocolComponentsRegistry)
    new PlaywrightNavigateAction(functionName, path, upperComponents.upperProtocol, statsEngine, coreComponents.clock, next)
  }
}

class PlaywrightNavigateAction(functionName: String,
                               path: String,
                               protocol: PlaywrightProtocol,
                               val statsEngine: StatsEngine,
                               val clock: Clock,
                               val next: Action) extends ExitableAction with NameGen {
  override def name: String = functionName

  @inline
  private def now() = clock.nowMillis // System.currentTimeMillis()

  override def execute(session: Session): Unit = {
    //val now: Long = clock.nowMillis
    val startTime = now()
    try {
      val (playwright, context) = new PlaywrightClient(protocol.browser, protocol.launchOptions).runContext
      val page = context.newPage()
      page.navigate(protocol.baseUrl + path)
      //page.click("*[title='NewArticle']")
      //page.`type`("#id_subject", "Test")
      //page.isVisible("text='Create Article'")
      //page.selectOption("#id_author", "1")
      //page.click("*[value='Save']")
      //page.isVisible("text='Article Body'")
      //Thread.sleep(2000)
      playwright.close()

      if (logger.underlying.isDebugEnabled) {
        logger.debug(s"Navigated to page ${protocol.baseUrl}$path  by ${session.userId}")
      }
      statsEngine.logResponse(session.scenario, session.groups, name, startTime, clock.nowMillis, OK, None, None)
    } catch {
      case e: Throwable =>
        logger.error(e.getMessage, e)
        statsEngine.logResponse(session.scenario, session.groups, name, startTime, clock.nowMillis, KO, Some("500"), Some(e.getMessage))
    }
    next ! session

  }


}
