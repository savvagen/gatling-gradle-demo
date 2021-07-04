package org.example

import com.microsoft.playwright.BrowserType.LaunchOptions
import com.microsoft.playwright.Playwright

import scala.util.Using


object TestPlaywright {

  def main(args: Array[String]): Unit = {
    Using(Playwright.create()){ playwright =>
      val browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false))
      val context = browser.newContext()
      val page = context.newPage()
      page.navigate("http://127.0.0.1:8001")
    }

  }

}
