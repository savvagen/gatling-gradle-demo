package org.example.cases

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import org.example.data.RandomData

object TokenActions extends RandomData {

  def getUserToken(login: String, password: String): ChainBuilder = {
    exec(http("GET /api/get_token").get("/api/get_token")
      .basicAuth(login, password)
      .header("Content-Type", "application/json")
      .check(status.is(200))
      .check(jsonPath("$.token").notNull)
      .check(jsonPath("$.token").saveAs("token")))
  }

}
