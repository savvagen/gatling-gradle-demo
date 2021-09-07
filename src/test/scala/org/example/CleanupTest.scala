package org.example

import io.restassured.RestAssured
import org.example.services.JsonServer
import org.junit.jupiter.api.{AfterEach, BeforeEach, Tag, Test}

@Tag("gatlingCleanup")
class CleanupTest extends TestBase {

  @BeforeEach
  def beforeEach(): Unit ={
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    println("Cleaning Up Generated Data.")
  }

  @AfterEach
  def afterEach(): Unit ={
    println("Clean Up Finished.")
  }

  @Test
  @Tag("gatlingCleanup")
  def cleanUpProject(): Unit ={
    println("Clean Up Data.")
    val jsonServer = new JsonServer(baseUrl, username, password)
    jsonServer.getComments().foreach(comment => {
      jsonServer.deleteComment(comment.getId())
    })
  }

}
