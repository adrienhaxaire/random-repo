package controllers

import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

import play.api.Application
import play.api.test.FakeRequest

class ReportControllerSpec extends PlaySpec with OneAppPerTest {

  "ReportController GET" should {

    "render the report page from a new instance of controller" in {
      val controller = new ReportController
      val report = controller.print().apply(FakeRequest())

      status(report) mustBe OK
      contentType(report) mustBe Some("text/html")
      contentAsString(report) must include ("Top 10")
      contentAsString(report) must include ("Bottom 10")
      contentAsString(report) must include ("Country")
    }

    "render the index page from the application" in {
      val controller = app.injector.instanceOf[ReportController]
      val report = controller.print().apply(FakeRequest())

      status(report) mustBe OK
      contentType(report) mustBe Some("text/html")
      contentAsString(report) must include ("Top 10")
      contentAsString(report) must include ("Bottom 10")
      contentAsString(report) must include ("Country")
    }

  }
}
