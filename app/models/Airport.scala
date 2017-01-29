package models


case class Airport (ident: String, name: String, municipality: String, countryCode: String)

object Airport {

  var airports = Set(
    Airport("FR-0001", "Centre Hospitalier De Moenchsberg Heliport", "Mulhouse", "FR"),
    Airport("FR-0002", "Centre Hospitalier Hautepierre Heliport", "Strasbourg", "FR"),
    Airport("FR-0003", "Centre Hospitalier Heliport", "Haguenau", "FR"),
    Airport("FR-0004", "Centre Hospitalier Heliport", "Ingwiller", "FR")
  )

  def byCountryCode(code: String) = airports.toList.filter(_.countryCode == code)
}


