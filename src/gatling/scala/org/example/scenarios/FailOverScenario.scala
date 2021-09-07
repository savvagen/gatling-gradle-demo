package org.example.scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import java.util.concurrent.atomic.AtomicBoolean
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps


object FailOverScenario {

  var exitFlag = new AtomicBoolean(false)

  val failAction: ChainBuilder =
    doIf(session => exitFlag.get()) {
      pause(10 seconds)
        .exec(s => {
          println("Found failed HTTP requests.\nExiting!")
          System.exit(1)
          s
        })
    }


}
