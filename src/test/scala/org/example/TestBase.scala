package org.example

class TestBase {

  def getProperty(propertyName: String, defaultValue: String) = {
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

  def baseUrl: String = getProperty("BASE_URL", "http://localhost:3001")
  def username: String = getProperty("USERNAME", "test")
  def password: String = getProperty("PASSWORD", "test")

  // val jsonServer = new JsonServer(baseUrl, username, password)

}
