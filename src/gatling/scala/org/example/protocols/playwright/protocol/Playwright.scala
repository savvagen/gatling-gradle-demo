package org.example.protocols.playwright.protocol

class Playwright(functionName: String) {

  def navigate(path: String) = new PlaywrightNavigateActionBuilder(functionName, path)

}
