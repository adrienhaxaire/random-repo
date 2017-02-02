package controllers

import javax.inject._
import play.api.mvc.{Controller, Action}

import models.Airport
import models.Runway

class AirportController @Inject() extends Controller {

  def byIsoCountry(isoCountry: String, page: Int) = Action { implicit request =>

    if (isoCountry.toLowerCase == isoCountry) {

      val airports = Airport.byIsoCountry(isoCountry, page)
      val runways = airports.map(airport => Runway.byIdent(airport.ident)).flatten
      val lastPage = Airport.lastPage(isoCountry.toUpperCase)

      Ok(views.html.airports(airports, runways, (page, lastPage)))

    } else {
      Redirect(routes.AirportController.byIsoCountry(isoCountry.toLowerCase))
    }
  }

}

