package org.example.scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import org.example.data.RandomData

class BaseScn extends RandomData {

  def setUpScn(username: String,
               password: String): ChainBuilder = {
    exec(_.set("username", username))
      .exec(_.set("password", password))
      //.exec(_.set("userId", faker.random().nextInt(1, 100)))
  }

}
