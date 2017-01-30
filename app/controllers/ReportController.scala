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

  private def runwayTypes(countryCode: String): List[String] = {
    val sql: SqlQuery = SQL("""select distinct r.surface from runways r 
                               join airports a on r.faa=a.faa 
                               join countries c on a.country_code=c.code 
                               where c.code = '""" + countryCode + "';")
    DB.withConnection { implicit connection =>
      sql().map(row => row[String]("surface")).toList}
  }

  private def countryCodes(): List[String] = {
    val sql: SqlQuery = SQL("select code from countries;")
    DB.withConnection { implicit connection =>
      sql().map(row => row[String]("code")).toList}
  }

  private def toName(countryCode: String): String = {
    val sql: SqlQuery = SQL("select name from countries where code='" + countryCode + "';")
    DB.withConnection { implicit connection =>
      sql().map(row => row[String]("name")).toList.head}
  }

  def print() = Action { implicit request =>
    val highest = numberOfAirports("desc")
    val lowest = numberOfAirports("asc")

    var runwaysPerCountry = scala.collection.mutable.Map[String, List[String]]()
    for (code <- countryCodes) {
      runwaysPerCountry(toName(code)) = runwayTypes(code)
    }
    Ok(views.html.report(highest, lowest, runwaysPerCountry))
  }

}


