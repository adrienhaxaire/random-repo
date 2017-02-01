create table airports (id text,
                       ident text unique not null,
                       type text,
                       name text not null,
                       latitude text,
                       longitude text,
                       elevation_ft text,
                       continent text,
                       iso_country text,
                       iso_region text,
                       municipality text,
                       scheduled_service text,
                       gps_code text,
                       iata_code text,
                       local_code text,
                       home_link text,
                       wikipedia_link text,
                       keywords text,
                       primary key (ident));
