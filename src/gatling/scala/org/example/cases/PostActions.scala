package org.example.cases

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import org.example.data.RandomData
import org.example.models.Post

import java.util

object PostActions extends RandomData {

  def createPost(): ChainBuilder = {
    exec(http("POST /api/posts").post("/api/posts")
      .header("Content-Type", "application/json")
      .body(StringBody(session => {
        objectMapper.writeValueAsString(gson.fromJson(randomPost(), classOf[Post])
          .setUser(Integer.valueOf(session("userId").as[String])))
          /*.setComments(session("commentIdsComasep").as[String]
            .split("\\s*,\\s*")
            .map(id=>Integer.valueOf(id)).toList.asJava // .toList.asJava //===> import scala.jdk.CollectionConverters._ <=== # https://stackoverflow.com/questions/53917117/how-to-convert-scala-array-to-java-list
          ))*/
      }))
      .check(status.is(201))
      .check(jsonPath("$.user").is("${userId}"))
      .check(jsonPath("$.id").saveAs("postId"))
    )
  }

  def getPostsByCategory(category: String="cats"): ChainBuilder ={
    exec(http("GET /api/posts?category={category}").get("/api/posts")
      .header("Content-Type", "application/json")
      .queryParam("category", category)
      .check(status.is(200))
      .check(jsonPath("$[*]").count.gte(1))
      .check(jsonPath("$[*].id").findRandom.saveAs("postId"))
    )
  }

  def getPosts: ChainBuilder ={
    exec(http("GET /api/posts").get("/api/posts")
      .header("Content-Type", "application/json")
      .check(status.is(200))
      .check(jsonPath("$[*]").count.gte(100))
    )
  }

  def getPost(postId: String): ChainBuilder = {
    exec(http("GET /api/posts/{id}").get(s"/api/posts/${postId}")
      .header("Content-Type", "application/json")
      .check(status.is(200))
      .check(jsonPath("$.id").is(postId))
      .check(jsonPath("$.comments[*]").findAll.saveAs("commentIds"))
      .check(jsonPath("$.comments[*]").findAll.transform(_.mkString(",")).saveAs("commentIdsComasep"))
    )
  }

  def updatePostComments(postId: String): ChainBuilder = {
    exec(http("PATCH /api/posts").patch(s"/api/posts/${postId}")
      .header("Content-Type", "application/json")
      .body(StringBody(session => {objectMapper.writeValueAsString(new Post()
        .setUser(Integer.valueOf(session("userId").as[String]))
        .setComments(util.Arrays.asList(
          s"${session("commentIdsComasep").as[String]},${session("commentId").as[String]}".split("\\s*,\\s*").map(id=>Integer.valueOf(id)):_*,
        )))
      }))
      .check(status.is(200))
      .check(jsonPath("$.user").is("${userId}"))
      .check(jsonPath("$.id").saveAs("postId"))
    )
  }

}
