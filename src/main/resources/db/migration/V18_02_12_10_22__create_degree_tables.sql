CREATE TABLE IF NOT EXISTS user (
  id          VARCHAR(100)  NOT NULL,
  first_name  VARCHAR(45),
  last_name   VARCHAR(45),
  email       VARCHAR(45),
  password    VARCHAR(100)          DEFAULT NULL,
  role        INT(11) NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
CREATE UNIQUE INDEX user_email_uindex ON degree.user (email);

CREATE TABLE IF NOT EXISTS venue (
  id          VARCHAR(100)  NOT NULL,
  name        VARCHAR(100)   NOT NULL,
  lat         DOUBLE        NOT NULL,
  lon         DOUBLE        NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
CREATE UNIQUE INDEX venue_name_uindex ON degree.venue (name);

CREATE TABLE IF NOT EXISTS rating (
  user_id     VARCHAR(100)  NOT NULL,
  venue_id    VARCHAR(100)  NOT NULL,
  rating      INT           NOT NULL,
  INDEX user_par_id (user_id),
  INDEX venue_par_id (venue_id),
  FOREIGN KEY (user_id)
    REFERENCES user(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (venue_id)
    REFERENCES venue(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  PRIMARY KEY (user_id,venue_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS venue_manager (
  user_id     VARCHAR(100)  NOT NULL,
  venue_id    VARCHAR(100)  NOT NULL,
  INDEX user_manager_id (user_id),
  INDEX venue_manager_id (venue_id),
  FOREIGN KEY (user_id)
    REFERENCES user(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (venue_id)
    REFERENCES venue(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  PRIMARY KEY (user_id, venue_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS event (
  id          VARCHAR(100)  NOT NULL,
  venue_id    VARCHAR(100)  NOT NULL,
  name        VARCHAR(100)  NOT NULL,
  description VARCHAR(500),
  start_date  TIMESTAMP,
  end_date    TIMESTAMP,
  INDEX venue_event_id (venue_id),
  FOREIGN KEY (venue_id)
    REFERENCES venue(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  PRIMARY KEY (id)
)