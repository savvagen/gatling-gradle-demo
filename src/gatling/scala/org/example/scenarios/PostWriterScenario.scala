package org.example.scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import org.example.cases.CommentActions.{createComment, getComments}
import org.example.cases.FailActions.exitIfFailed
import org.example.cases.PostActions.{createPost, getPost, getPostsByCategory, updatePostComments}
import org.example.cases.TokenActions.getUserToken
import org.example.cases.UserActions.{createUser, getRandomUser, getUsers}

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object PostWriterScenario extends BaseScn {

  def postWriterScn(username: String, password: String, postsRange: Range = 3 to 4, commentsNumber: Int = 5): ChainBuilder = {
    exec(setUpScn(username, password))
      .exec(getUserToken("${username}", "${password}")).exec(exitIfFailed)
      .pause(500.milliseconds)
      .exec(createUser()).exec(exitIfFailed)
      .pause(1.second)
      .exec(_.set("times", postsRange))
      .repeat("${times.random()}", "postIndex"){ // https://gatling.io/docs/gatling/reference/current/session/expression_el/
        exec(getComments).exec(exitIfFailed)
          .pause(500.millis)
          .exec(createPost()).exec(exitIfFailed)
          .pause(1.second)
          .repeat(commentsNumber, "commentIndex"){
            exec(createComment()).exec(exitIfFailed)
              .pause(1.seconds)
          }
          //.exec(session => { session.set("total", session("postIndex").as[Int] + 1) })
      }
      //.exec(session => {println(s"Created Posts number: ${session("total").as[String]}"); session})
  }

  def commentWriterScn(): ChainBuilder = {
    exec(getUsers)
      .exec(getRandomUser)
      .pause(1)
      .exec(getPostsByCategory("cats")).exec(exitIfFailed)
      .pause(1)
      .repeat(5){
        exec(createComment()).exec(exitIfFailed)
          .pause(50.milliseconds)
          .exec(getPost("${postId}")).exec(exitIfFailed)
          .pause(50.milliseconds)
          .exec(updatePostComments("${postId}")).exec(exitIfFailed)
          //.exec(session => {println(session("commentIdsComasep").as[String]); session })
          .pause(1)
      }

  }


}
