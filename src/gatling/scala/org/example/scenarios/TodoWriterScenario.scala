package org.example.scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import org.example.cases.FailActions.exitIfFailed
import org.example.cases.TodoActions.createTodo
import org.example.cases.TokenActions.getUserToken
import org.example.cases.UserActions.createUser
import org.example.scenarios.MetricsExporterScn.todoCreationTime
import org.example.scenarios.PostWriterScenario.setUpScn

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object TodoWriterScenario {

  def todoWriterScn(username: String, password: String): ChainBuilder = {
    exec(setUpScn(username, password))
      .exec(getUserToken("${username}", "${password}"))
      .exec(exitIfFailed)
      .pause(1.second)
      .exec(createUser()).exec(exitIfFailed)
      .pause(1 second)
      .repeat(5, "todoIndex") {
        exec(_.set("startTime", System.currentTimeMillis()))
          .exec(createTodo())
          .pause(1)
          .exec(_.set("endTime", System.currentTimeMillis()))
          .exec(session=> { session.set("buildTime", session("endTime").as[Long] - session("startTime").as[Long]) })
          .randomSwitch(10.0-> exec(todoCreationTime("${buildTime}")))
      }
  }

}
