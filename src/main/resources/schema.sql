CREATE TABLE IF NOT EXISTS ITEM (
  id bigint(20) AUTO_INCREMENT,
  name varchar(256) not null,
  price INT not null,
  type varchar(20) not null,
  CONSTRAINT pk_item PRIMARY KEY ( id )
)
ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS COFFEE (
  id bigint(20) AUTO_INCREMENT,
  name varchar(256) not null,
  price INT not null,
  CONSTRAINT pk_coffee PRIMARY KEY ( id )
)
ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS TEA (
  id bigint(20) AUTO_INCREMENT,
  name varchar(256) not null,
  price INT not null,
  CONSTRAINT pk_tea PRIMARY KEY ( id )
)
ENGINE = InnoDB DEFAULT CHARSET=utf8;