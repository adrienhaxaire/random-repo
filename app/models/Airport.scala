package models

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import anorm.SqlQuery

case class Airport (faa: String, name: String, latitude: Double, longitude: Double, countryCode: String)

object Airport {

  def byCountryCode(code: String, page: Int): List[Airport] = {
    val pagination = 50
    val offset = (page - 1) * pagination
    val sql: SqlQuery =
      SQL("select * from airports where country_code = '" + code.toUpperCase
        + "' LIMIT " + pagination + " OFFSET " + offset + ";")
    DB.withConnection { implicit connection =>
      sql().map(row =>
        Airport(row[String]("faa"),
          row[String]("name"),
          row[Double]("latitude"),
          row[Double]("longitude"),
          row[String]("country_code"))).toList}
  }

}

