package controllers

import javax.inject._
import play.api.mvc._

import models.Airport
import models.Runway

@Singleton
class AirportController @Inject() extends Controller {

  def byCountryCode(countryCode: String) = Action { implicit request =>

    if (countryCode.toLowerCase == countryCode) {

      val airports = Airport.byCountryCode(countryCode)
      val runways = airports.map(airport => Runway.byFAA(airport.faa)).flatten

      Ok(views.html.airports(airports, runways))

    } else {
      Redirect(routes.AirportController.byCountryCode(countryCode.toLowerCase))
    }
  }
}

