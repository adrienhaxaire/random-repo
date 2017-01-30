package controllers

import javax.inject._

import play.api.mvc.{Controller, Action}
import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import anorm.SqlQuery

import models.Airport
import models.Runway

class ReportController @Inject() extends Controller {

  private def numberOfAirports(ordering: String): List[(String, Int)] = {
    val sql: SqlQuery = SQL("""select count(country_code) as cnt, countries.name from airports 
                               inner join countries on airports.country_code=countries.code 
                               group by countries.name order by cnt """ + ordering + " limit 10;")
    DB.withConnection { implicit connection =>
      sql().map(row => (row[String]("name"), row[Int]("cnt"))).toList}
  }

  def print() = Action { implicit request =>
    val highest = numberOfAirports("desc")
    val lowest = numberOfAirports("asc")

    Ok(views.html.report(highest, lowest))
  }

}


