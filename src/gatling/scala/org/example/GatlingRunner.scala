package org.example

import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import org.example.simulations.LoadSimulation

object GatlingRunner {

  def main(args: Array[String]): Unit = {

    val simulationClass = classOf[LoadSimulation].getName

    val props = new GatlingPropertiesBuilder
    props.simulationClass(simulationClass)

    Gatling.fromMap(props.build)

  }

}
