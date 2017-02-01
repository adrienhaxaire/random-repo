package models

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._

case class Runway (
  id: String,
  airport_ref: String,
  airport_ident: String,
  length_ft: String,
  width_ft: String,
  surface: String,
  lighted: String,
  closed: String,
  le_ident: String,
  le_latitude_deg: String,
  le_longitude_deg: String,
  le_elevation_ft: String,
  le_heading_degT: String,
  le_displaced_threshold_ft: String,
  he_ident: String,
  he_latitude_deg: String,
  he_longitude_deg: String,
  he_elevation_ft: String,
  he_heading_degT: String,
  he_displaced_threshold_ft: String)

object Runway {

  val runwayParser: RowParser[Runway] = { 
    get[String]("id") ~
    get[String]("airport_ref") ~
    get[String]("airport_ident") ~
    get[Option[String]]("length_ft") ~
    get[Option[String]]("width_ft") ~
    get[Option[String]]("surface") ~
    get[Option[String]]("lighted") ~
    get[Option[String]]("closed") ~
    get[Option[String]]("le_ident") ~
    get[Option[String]]("le_latitude_deg") ~
    get[Option[String]]("le_longitude_deg") ~
    get[Option[String]]("le_elevation_ft") ~
    get[Option[String]]("le_heading_degT") ~
    get[Option[String]]("le_displaced_threshold_ft") ~
    get[Option[String]]("he_ident") ~
    get[Option[String]]("he_latitude_deg") ~
    get[Option[String]]("he_longitude_deg") ~
    get[Option[String]]("he_elevation_ft") ~
    get[Option[String]]("he_heading_degT") ~
    get[Option[String]]("he_displaced_threshold_ft") map {
      case id ~ airport_ref ~ airport_ident ~ length_ft ~ width_ft ~ surface ~ lighted ~ closed ~
          le_ident ~ le_latitude_deg ~ le_longitude_deg ~ le_elevation_ft ~ le_heading_degT ~ le_displaced_threshold_ft ~
          he_ident ~ he_latitude_deg ~ he_longitude_deg ~ he_elevation_ft ~ he_heading_degT ~ he_displaced_threshold_ft =>
        Runway(id, airport_ref, airport_ident, length_ft.getOrElse(""), width_ft.getOrElse(""), surface.getOrElse(""),
          lighted.getOrElse(""), closed.getOrElse(""), le_ident.getOrElse(""), le_latitude_deg.getOrElse(""),
          le_longitude_deg.getOrElse(""), le_elevation_ft.getOrElse(""), le_heading_degT.getOrElse(""),
          le_displaced_threshold_ft.getOrElse(""), he_ident.getOrElse(""), he_latitude_deg.getOrElse(""),
          he_longitude_deg.getOrElse(""), he_elevation_ft.getOrElse(""), he_heading_degT.getOrElse(""),
          he_displaced_threshold_ft.getOrElse(""))
    }
  }

  def byIdent(ident: String): List[Runway] = {
    val sql: SqlQuery = SQL("select * from runways where airport_ident = '" + ident + "';")
    DB.withConnection { implicit connection =>
      sql.as(runwayParser *)
    }
  }
}


