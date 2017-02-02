package models

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._

case class Airport (
  ident: String,
  category: String,
  name: String,
  latitude: String,
  longitude: String,
  elevation_ft: String,
  continent: String,
  iso_country: String,
  iso_region: String,
  municipality: String,
  scheduled_service: String,
  gps_code: String,
  iata_code: String,
  local_code: String,
  home_link: String,
  wikipedia_link: String,
  keywords: String)

object Airport {

  val airportParser: RowParser[Airport] = { 
    get[String]("ident") ~
    get[Option[String]]("type") ~
    get[String]("name") ~
    get[Option[String]]("latitude") ~
    get[Option[String]]("longitude") ~
    get[Option[String]]("elevation_ft") ~
    get[Option[String]]("continent") ~
    get[String]("iso_country") ~
    get[Option[String]]("iso_region") ~
    get[Option[String]]("municipality") ~
    get[Option[String]]("scheduled_service") ~
    get[Option[String]]("gps_code") ~
    get[Option[String]]("iata_code") ~
    get[Option[String]]("local_code") ~
    get[Option[String]]("home_link") ~
    get[Option[String]]("wikipedia_link") ~
    get[Option[String]]("keywords") map {
      case ident ~ category ~ name ~ latitude ~ longitude ~ elevation_ft ~
          continent ~ iso_country ~ iso_region ~ municipality ~ scheduled_service ~
          gps_code ~ iata_code ~ local_code ~ home_link ~ wikipedia_link ~ keywords =>
        Airport(ident, category.getOrElse(""), name, latitude.getOrElse(""), longitude.getOrElse(""),
          elevation_ft.getOrElse(""), continent.getOrElse(""), iso_country, iso_region.getOrElse(""),
          municipality.getOrElse(""), scheduled_service.getOrElse(""), gps_code.getOrElse(""),
          iata_code.getOrElse(""), local_code.getOrElse(""), home_link.getOrElse(""),
          wikipedia_link.getOrElse(""), keywords.getOrElse(""))
    }
  }

  def byIsoCountry(code: String, page: Int): List[Airport] = {
    val pagination = 50
    val offset = (page - 1) * pagination
    val sql: SqlQuery =
      SQL("select * from airports where iso_country = '" + code.toUpperCase
        + "' order by name asc LIMIT " + pagination + " OFFSET " + offset + ";")
    DB.withConnection { implicit connection =>
      sql.as(airportParser *)
    }
  }

  def lastPage(isoCountry: String): Int = {
    DB.withConnection { implicit connection =>
      SQL("select count(*) / 50 +1 from airports where iso_country='" + "US" + "';").as(SqlParser.scalar[Int].single)
    }
  }



}

