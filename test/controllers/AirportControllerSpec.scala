package controllers

import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

import play.api.Application
import play.api.test.FakeRequest

class AirportControllerSpec extends PlaySpec with OneAppPerTest {

  "AirportController GET" should {

    "render the airports page from a new instance of controller" in {
      val controller = new AirportController
      val report = controller.byIsoCountry("nl", 1).apply(FakeRequest())

      status(report) mustBe OK
      contentType(report) mustBe Some("text/html")
      contentAsString(report) must include ("Schiphol")
      contentAsString(report) must include ("http://www.schiphol.nl/")
      contentAsString(report) must include ("-13")  // elevation of runway 04
    }

    // TODO: redirect uppercase to lower case

  }
}
