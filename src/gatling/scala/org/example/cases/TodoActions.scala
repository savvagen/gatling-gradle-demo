package org.example.cases

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import org.example.data.RandomData
import org.example.models.Todo

object TodoActions extends RandomData {

  def createTodo(): ChainBuilder = {
    exec(http("POST /api/todos").post("/api/todos")
      .header("Content-Type", "application/json")
      .body(StringBody(session => {
        objectMapper.writeValueAsString(gson.fromJson(randomToDo(), classOf[Todo])
          .setUser(Integer.valueOf(session("userId").as[String])))
      }))
      .check(status.is(201))
      .check(jsonPath("$.user").is("${userId}"))
      .check(jsonPath("$.id").saveAs("todoId"))
    )
  }

  def getTodo(todoId: String): ChainBuilder = {
    exec(http("GET /api/todos/{id}").get(s"/api/posts/${todoId}")
      .header("Content-Type", "application/json")
      .check(status.is(200))
      .check(jsonPath("$.id").is(todoId))
      .check(jsonPath("$.comments[*]").findAll.saveAs("postCommentIds"))
      .check(jsonPath("$.comments[*]").findAll.transform(_.mkString(",")).saveAs("commentIdsComasep"))
    )
  }

}
