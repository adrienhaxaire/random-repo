package models

import play.api.Play.current
import play.api.db.DB
import anorm.SqlParser._
import anorm._

case class Country(code: String) {

  def codeExists(code: String): Boolean = { 
    DB.withConnection { implicit connection =>
      SQL("select exists(select * from countries where lower(code)=lower('" + code + "'));")
        .as(scalar[Boolean].single)}
  }

  def nameExists(name: String): Boolean = { 
    DB.withConnection { implicit connection =>
      SQL("select exists(select * from countries where lower(name)=lower('" + name + "'));")
        .as(scalar[Boolean].single)}
  }

  def fromName(name: String): Country = {
    val sql: SqlQuery = SQL("select code from countries where lower(name)=lower('" + name + "');")
    DB.withConnection { implicit connection =>
      sql().map(row => Country(row[String]("code"))).toList.head}
  }

}



object Country {

  def fromCode(code: String): Country = Country(code)


}
