package models

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import anorm.SqlQuery

import models.Runway

case class Airport (faa: String, name: String, latitude: Double, longitude: Double, countryCode: String)

object Airport {

  def byCountryCode(code: String): List[Airport] = {
    val sql: SqlQuery = SQL("select * from airports where country_code = '" + code + "';")
    DB.withConnection { implicit connection =>
      sql().map(row =>
        Airport(row[String]("faa"),
          row[String]("name"),
          row[Double]("latitude"),
          row[Double]("longitude"),
          row[String]("country_code"))).toList}
  }

}

