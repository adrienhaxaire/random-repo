package controllers

import javax.inject._
import play.api.mvc._

import models.Airport

@Singleton
class AirportController @Inject() extends Controller {

  def byCountryCode(countryCode: String) = Action { implicit request =>
    val airports = Airport.byCountryCode(countryCode)

    Ok(views.html.airports(airports))
  }
}

