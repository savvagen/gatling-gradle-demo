package org.example.cases

import io.gatling.commons.stats.KO
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import org.example.scenarios.FailOverScenario

object FailActions {

  def exitSimulationOnFailure: ChainBuilder = {
    exec((session: io.gatling.core.session.Session) => {
      if(session.status == KO) {
        FailOverScenario.exitFlag.set(true)
      }
      session
    })
  }

  def exitIfFailed: ChainBuilder = exec(exitSimulationOnFailure).exitHereIfFailed


}
