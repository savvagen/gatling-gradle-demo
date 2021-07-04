package org.example.protocols.playwright.protocol

import com.microsoft.playwright.BrowserType.LaunchOptions
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.{Protocol, ProtocolComponents, ProtocolKey}
import io.gatling.core.session.Session
import io.gatling.core.{CoreComponents, protocol}


object PlaywrightProtocol {
  def apply(baseUrl: String, browser: String, launchOptions: LaunchOptions) = new PlaywrightProtocol(baseUrl, browser, launchOptions)

  val UpperProtocolKey = new ProtocolKey[PlaywrightProtocol, PlaywrightComponents] {

    type Protocol = PlaywrightProtocol
    type Components = PlaywrightComponents

    override def protocolClass: Class[protocol.Protocol] = classOf[PlaywrightProtocol].asInstanceOf[Class[io.gatling.core.protocol.Protocol]]

    override def defaultProtocolValue(configuration: GatlingConfiguration): PlaywrightProtocol = throw new IllegalStateException("Can't provide a default value for UpperProtocol")

    override def newComponents(coreComponents: CoreComponents): PlaywrightProtocol => PlaywrightComponents = {
      playwrightProtocol => PlaywrightComponents(playwrightProtocol)
    }
  }
}

class PlaywrightProtocol(val baseUrl: String, val browser: String, val launchOptions: LaunchOptions) extends Protocol {
  type Components = PlaywrightComponents
}


case class PlaywrightComponents(upperProtocol: PlaywrightProtocol) extends ProtocolComponents {
  override def onStart: Session => Session = Session.Identity

  override def onExit: Session => Unit = ProtocolComponents.NoopOnExit

}


object  PlaywrightProtocolBuilder {
  def baseUrl(baseUrl: String) = PlaywrightProtocolBuilderBrowser(baseUrl)
}

case class  PlaywrightProtocolBuilderBrowser(baseUrl: String) {
  def browser(browser: String) = PlaywrightProtocolBuilderLaunchOptions(baseUrl, browser)
}

case class  PlaywrightProtocolBuilderLaunchOptions(baseUrl: String, browser: String) {
  def launchOptions(launchOptions: LaunchOptions) = PlaywrightProtocolBuilder(baseUrl, browser, launchOptions)
}

case class PlaywrightProtocolBuilder(baseUrl: String, browser: String, launchOptions: LaunchOptions)  {
  def build() = PlaywrightProtocol(
    baseUrl = baseUrl,
    browser = browser,
    launchOptions = launchOptions
  )
}

//object PlaywrightProtocolBuilder {
//  def endpoint(baseUrl: String, headless: Boolean)  = PlaywrightProtocolBuilder(baseUrl, headless)
//}
