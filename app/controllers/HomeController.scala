package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import play.api.Play.current
import play.api.i18n.Messages.Implicits._

import models.Country

@Singleton
class HomeController @Inject() extends Controller {

  private val countryForm: Form[Country] = Form(
    mapping("code" -> text)(Country.apply)(Country.unapply)
  )

  def index = Action { implicit request =>
    Ok(views.html.index(countryForm))
  }

  def query = Action { implicit request =>
    val newCountryForm = countryForm.bindFromRequest()

    newCountryForm.fold(
      hasErrors = {form => Redirect(routes.HomeController.index())},
      success = {country => Redirect(routes.AirportController.byCountryCode(country.code))})
  }

}
