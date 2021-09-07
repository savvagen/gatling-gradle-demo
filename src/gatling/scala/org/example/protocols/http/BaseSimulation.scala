package org.example.protocols.http

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class BaseSimulation extends Simulation {

  def getProperty(propertyName: String, defaultValue: String): String = {
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

  // Required Vars
  def baseUrl: String        = getProperty("BASE_URL", "http://localhost:3001")
  def username: String       = getProperty("USERNAME", "test")
  def password: String       = getProperty("PASSWORD", "test")
  def exitOnFailure: Boolean = getProperty("EXIT_ON_FAILURE", "false").toBoolean

  var httpConf: HttpProtocolBuilder = http.baseUrl(baseUrl)
    .acceptCharsetHeader("utf-8")
    .acceptHeader("application/json")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.106 Safari/537.36")
    .disableWarmUp
    .disableCaching
  //.proxy(Proxy("localhost", 8888).httpsPort(8888)) // Send All requests through proxy (Fiddler or Charles, etc...)


}
