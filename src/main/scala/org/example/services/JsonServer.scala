package org.example.services

import io.restassured.RestAssured
import io.restassured.RestAssured._
import io.restassured.config.{HttpClientConfig, RestAssuredConfig}
import io.restassured.filter.log.{LogDetail, RequestLoggingFilter, ResponseLoggingFilter}
import io.restassured.http.ContentType._
import org.apache.http.params.CoreConnectionPNames._
import org.example.models.Comment

class JsonServer(val baseUrl: String, val username: String, val password: String) {

  val rsConfig: RestAssuredConfig = RestAssured.config
    .httpClient(HttpClientConfig.httpClientConfig
      .setParam(CONNECTION_TIMEOUT, 300000)
      .setParam(SO_TIMEOUT, 300000))

  def client = given()
    .relaxedHTTPSValidation()
    .filter(new RequestLoggingFilter(LogDetail.URI)) // LogDetail.URI
    .filter(new ResponseLoggingFilter(LogDetail.STATUS)) // LogDetail.STATUS
    .contentType(JSON)
    .accept(JSON)
    .config(rsConfig)
    .baseUri(baseUrl)

  def getComments() = {
    client.when().get("/api/comments")
      .`then`().assertThat().statusCode(200)
      .extract().body().as(classOf[List[Comment]])
  }

  def deleteComment(id: Integer) = {
    client.when()
      .delete(s"/api/comments/${id}")
      .`then`()
      .assertThat().statusCode(204)
  }

}
