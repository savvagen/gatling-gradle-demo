package org.example.protocols.playwright.protocol

import com.microsoft.playwright.BrowserType.LaunchOptions
import com.microsoft.playwright.{BrowserContext, Playwright}

class PlaywrightClient(browserType: String, launchOptions: LaunchOptions) {
  def runContext: (Playwright, BrowserContext) = {
    val playwright = Playwright.create()
    val browserContext: BrowserContext = browserType match {
      case "chromium" => playwright.chromium().launch(launchOptions).newContext()
      case "firefox" => playwright.firefox().launch(launchOptions).newContext()
    }
    (playwright, browserContext)
  }



}