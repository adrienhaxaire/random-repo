## Requirements

- ask the user for two options: Query or Reports
- Query option:
  - ask the user for the country name OR code (the input can be country code or country name)
  - print the airports
  - print the runways at each airport
  - bonus: make the test partial/fuzzy, e.g. entering zimb will result in Zimbabwe
- Report option:
  - 10 countries with highest number of airports (with count) and countries with lowest number of airports
  - type of runways (as indicated in "surface" column) per country
  - bonus: print the top 10 most common runway identifications (indicated in "le_ident" column)

## Notes

- the last change from http://ourairports.com/data/ is from 2009
- a build on Circle CI takes 5 minutes, total build and deployment time around 12 I guess
- to convert airports.csv into a sql script:
    - the first line is the table creation: `create table airports (faa text unique not null, name text not null, latitude double precision not null, longitude double precision not null, country_code text, primary key (faa));`
    - then copy-paste the csv lines after the first one into the sql file
    - replace all single quotes with two single quotes, to escape them in postgres
    - run this regexp on the lines: `[0-9]+,"\([0-9A-Za-z-_]+\)","[a-zA-Z_ ]+","\(.*\)",\([0-9\.-]+\),\([0-9\.-]+\),["NA0-9-]*,"[A-Z- ]+","\([A-Z]+\)","[A-Z0-9-]+",.*` to `insert into airports(faa, name, latitude, longitude, country_code) values ('\1', '\2', \3, \4, '\5');`
- had to use pagination as some countries have a lot of airports, like the US who have 21501 in this data set

## Ideas

- provide a REST API
- link the latitude and longitude to Google Maps
- use interactive graphs in the report
- provide length and width for the runways
- search for an airport
