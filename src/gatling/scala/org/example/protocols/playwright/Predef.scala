package org.example.protocols.playwright

import org.example.protocols.playwright.protocol.{Playwright, PlaywrightProtocol, PlaywrightProtocolBuilder}

object Predef {

  val pl = PlaywrightProtocolBuilder

  implicit def playwrightBuilderToProtocol(builder: PlaywrightProtocolBuilder): PlaywrightProtocol = builder.build()

  def playwright(functionName: String) = new Playwright(functionName)


}
