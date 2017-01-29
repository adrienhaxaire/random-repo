package models

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import anorm.SqlQuery

case class Airport (ident: String, name: String, municipality: String, countryCode: String)

object Airport {

  def byCountryCode(code: String): List[Airport] = {
    val sql: SqlQuery = SQL("select * from airports")
    DB.withConnection { implicit connection =>
      sql().map(row =>
        Airport(row[String]("ident"),
          row[String]("name"),
          row[String]("municipality"),
          row[String]("country_code"))).toList}
  }

}

