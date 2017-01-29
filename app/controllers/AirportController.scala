package controllers

import javax.inject._
import play.api.mvc.{Controller, Action}

import models.Airport
import models.Runway

class AirportController @Inject() extends Controller {

  def byCountryCode(countryCode: String, page: Int) = Action { implicit request =>

    if (countryCode.toLowerCase == countryCode) {

      val airports = Airport.byCountryCode(countryCode, page)
      val runways = airports.map(airport => Runway.byFAA(airport.faa)).flatten

      Ok(views.html.airports(airports, runways))

    } else {
      Redirect(routes.AirportController.byCountryCode(countryCode.toLowerCase))
    }
  }

}

