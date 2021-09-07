package org.example.scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

object MetricsExporterScn extends BaseScn {

  def pushMetricsToPrometheus(): ChainBuilder ={
    exec(http("Publish Report Metric").post("http://localhost:9091/metrics/job/test_job/instance/test_instance")
      .body(StringBody(
        """
          |# TYPE todo_exporter_build_time gauge
          |todo_exporter_build_time{env="localhost", slug="todo-report"} 55
          |""".stripMargin)).asMultipartForm
      .check(status.is(200))
    )
  }

  def todoCreationTime(buildTime: String,
                      env: String = "localhost",
                      job: String = "todo_exporter",
                      instance: String = "todo_exporter:9991"): ChainBuilder ={
    exec(http("PUSHGATEWAY: Todo Creation Metric").post(s"http://localhost:9091/metrics/job/${job}/instance/${instance}")
      .body(StringBody(
        s"""
           |# TYPE todo_exporter_build_time gauge
           |todo_exporter_build_time{environment="${env}"} ${buildTime}
           |""".stripMargin)).asMultipartForm
      .check(status.is(200))
    )
  }

  def metricsExportScn(): ChainBuilder = {
    exec(_.set("startTime", System.currentTimeMillis()))
      .pause(1.seconds, 5.seconds)
      .exec(_.set("endTime", System.currentTimeMillis()))
      .exec(session=> {
        val startTime = session("startTime").as[Long]
        val endTime = session("endTime").as[Long]
        val time = (endTime - startTime)
        session.set("buildTime", time)
      })
      .exec(todoCreationTime("${buildTime}"))
  }



}
