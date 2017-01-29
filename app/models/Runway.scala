package models

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import anorm.SqlQuery

case class Runway (faa: String, surface: String, common_identification: String)

object Runway {

  def byFAA(faa: String): List[Runway] = {
    val sql: SqlQuery = SQL("select * from runways where faa = '" + faa + "';")
    DB.withConnection { implicit connection =>
      sql().map(row =>
        Runway(faa, row[String]("surface"), row[String]("common_identification"))).toList}
  }
}


