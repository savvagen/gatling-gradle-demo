package org.example.cases

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import org.example.data.RandomData

object UserActions extends RandomData {



  def createUser(): ChainBuilder = {
    exec(http("POST /api/users").post("/api/users")
      .header("Authorization", "Bearer ${token}")
      .header("Content-Type", "application/json")
      .body(StringBody(randomUser())).asJson
      .check(status.is(201))
      .check(jsonPath("$.id").ofType[Int].saveAs("userId"))
      .check(jsonPath("$.email").ofType[String].saveAs("userEmail"))
    )
  }

  def getUsers: ChainBuilder = {
    exec(http("GET /api/users").get("/api/users")
      .header("Content-Type", "application/json")
      .check(status.is(200))
      .check(jsonPath("$[*]").count.gte(100))
      .check(jsonPath("$[*].id").findRandom.saveAs("userId"))
    )
  }

  def getRandomUser: ChainBuilder = {
    exec(http("GET /api/users/username={username}").get("/api/users")
      .header("Content-Type", "application/json")
      .queryParam("username", s"user_name${faker.random().nextInt(1, 100)}")
      .check(status.is(200))
      .check(jsonPath("$[0].id").saveAs("userId"))
      .check(jsonPath("$[0].email").findRandom.saveAs("userEmail"))
    )
  }


}
