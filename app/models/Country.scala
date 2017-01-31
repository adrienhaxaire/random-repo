package models

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import anorm.SqlQuery

case class Country(code: String)

object Country {

  def fromCode(code: String): Country = Country(code)

  def fromName(name: String): Country = {
    val sql: SqlQuery = SQL("select code from countries where name = '" + name + "';")
    DB.withConnection { implicit connection =>
      sql().map(row => Country(row[String]("code"))).toList.head}
  }

}
