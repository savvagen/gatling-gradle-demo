package org.example.scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import org.example.cases.TokenActions.getUserToken
import org.example.cases.UserActions.getUsers
import org.example.cases.CommentActions.getComment
import org.example.cases.PostActions.{getPost, getPosts, getPostsByCategory}

import scala.concurrent.duration.DurationInt

object PostReaderScenario extends BaseScn {

  def postReaderScn(username: String, password: String): ChainBuilder = {
    exec(setUpScn(username, password))
      .exec(getUserToken("${username}", "${password}"))
      .pause(500.milliseconds)
      .exec(getUsers).exitHereIfFailed
      .pause(1.second)
      .exec(getPosts)
      .pause(1.second)
      .exec(getPostsByCategory("cats")).exitHereIfFailed
      .pause(1.second)
      .exec(getPost("${postId}")).exitHereIfFailed
      .pause(1)
      .foreach("${commentIds}", "comment"){
        exec(getComment("${comment}"))
          .pause(1)
      }

  }

}
