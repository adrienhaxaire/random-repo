package controllers

import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

import play.api.Application
import play.api.test.FakeRequest
import play.filters.csrf.CSRF.Token
import play.filters.csrf.{CSRFConfigProvider, CSRFFilter}

import scala.language.postfixOps

// taken from http://stackoverflow.com/questions/40251368/testing-request-with-csrf-token-in-play-framework-2-5-scala
trait CSRFTest {
  def addToken[T](fakeRequest: FakeRequest[T])(implicit app: Application) = {
    val csrfConfig     = app.injector.instanceOf[CSRFConfigProvider].get
    val csrfFilter     = app.injector.instanceOf[CSRFFilter]
    val token          = csrfFilter.tokenProvider.generateToken

    fakeRequest.copyFakeRequest(tags = fakeRequest.tags ++ Map(
      Token.NameRequestTag  -> csrfConfig.tokenName,
      Token.RequestTag      -> token
    )).withHeaders((csrfConfig.headerName, token))
  }
}

class HomeControllerSpec extends PlaySpec with OneAppPerTest with CSRFTest {

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val controller = new HomeController
      val home = controller.index().apply(addToken(FakeRequest()))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("airports")
    }

    "render the index page from the application" in {
      val controller = app.injector.instanceOf[HomeController]
      val home = controller.index().apply(addToken(FakeRequest()))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("airports")
    }

    // "render the index page from the router" in {
    //   // Need to specify Host header to get through AllowedHostsFilter
    //   val request = FakeRequest(GET, "/").withHeaders("Host" -> "localhost")
    //   val home = route(app, request).get

    //   status(home) mustBe OK
    //   contentType(home) mustBe Some("text/html")
    //   contentAsString(home) must include ("Welcome to Play")
    // }
  }
}
