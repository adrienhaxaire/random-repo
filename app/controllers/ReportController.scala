package controllers

import javax.inject._

import play.api.mvc.{Controller, Action}
import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._

import models.Airport
import models.Runway

class ReportController @Inject() extends Controller {

  private def numberOfAirports(ordering: String): List[(String, Int)] = {
    val sql: SqlQuery = SQL("""select count(iso_country) as cnt, countries.name from airports 
                               inner join countries on airports.iso_country=countries.code 
                               group by countries.name order by cnt """ + ordering + " limit 10;")
    DB.withConnection { implicit connection =>
      sql().map(row => (row[String]("name"), row[Int]("cnt"))).toList}
  }

  private def countryNames(): List[String] = {
    val sql: SqlQuery = SQL("select name from countries;")
    DB.withConnection { implicit connection =>
      sql().map(row => row[String]("name")).toList}
  }

  private val runwayTypesParser: RowParser[(String, String)] = {
    get[String]("name") ~
    get[Option[String]]("surface") map {
      case name ~ surface =>
        (name, surface.getOrElse("U"))
    }
  }

  private def runwayTypes(): List[(String, String)] = {
    val sql: SqlQuery = SQL("""select distinct on (c.name, r.surface) c.name, r.surface from runways
                               r join airports a on r.airport_ident=a.ident join countries c on a.iso_country=c.code;""")
    DB.withConnection { implicit connection =>
      sql.as(runwayTypesParser *)
    }
  }

  def print() = Action { implicit request =>
    val highest = numberOfAirports("desc")
    val lowest = numberOfAirports("asc")
    val countries = countryNames
    val runways = runwayTypes
    Ok(views.html.report(highest, lowest, countries, runways))
  }

}


