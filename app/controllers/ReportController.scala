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

  private def countryNames(): List[String] = {
    val sql: SqlQuery = SQL("select name from countries;")
    DB.withConnection { implicit connection =>
      sql().map(row => row[String]("name")).toList}
  }

  private def runwayTypes(): List[(String, String)] = {
    val sql: SqlQuery = SQL("""select distinct on (c.name, r.surface) c.name, r.surface from runways
                               r join airports a on r.faa=a.faa join countries c on a.country_code=c.code;""")
    DB.withConnection { implicit connection =>
      sql().map(row => (row[String]("name"), row[String]("surface"))).toList}
  }

  def print() = Action { implicit request =>
    val highest = numberOfAirports("desc")
    val lowest = numberOfAirports("asc")
    val countries = countryNames
    val runways = runwayTypes
    Ok(views.html.report(highest, lowest, countries, runways))
  }

}


