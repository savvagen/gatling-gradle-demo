package org.example.cases

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import org.example.data.RandomData
import org.example.models.Comment

object CommentActions extends RandomData {

  def getComments: ChainBuilder = {
    exec(http("GET /api/comments").get("/api/comments")
      .header("Content-Type", "application/json")
      .check(status.is(200))
      .check(jsonPath("$[*]").count.gte(1000))
      .check(jsonPath("$[*].id").findRandom(3).saveAs("commentIds"))
    )
  }

  def createComment(comment: String = "Love It"): ChainBuilder = {
    exec(http("POST /api/comments").post("/api/comments")
      .header("Content-Type", "application/json")
      .body(StringBody(session => {
        objectMapper.writeValueAsString(gson.fromJson(randomComment(), classOf[Comment])
          .setPost(Integer.valueOf(session("postId").as[String]))
          .setEmail(session("userEmail").as[String])
          .setBody(comment))
      }))
      .check(status.is(201))
      .check(jsonPath("$.email").is("${userEmail}"))
      .check(jsonPath("$.id").saveAs("commentId"))
    )
  }

  def getComment(commentId: String): ChainBuilder = {
    exec(http("GET /api/comments").get(s"/api/comments/${commentId}")
      .header("Content-Type", "application/json")
      .check(status.is(200))
      .check(jsonPath("$.id").is(commentId))
    )
  }


}
