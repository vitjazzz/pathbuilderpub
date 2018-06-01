CREATE TABLE IF NOT EXISTS review (
  user_id     VARCHAR(100)  NOT NULL,
  venue_id    VARCHAR(100)  NOT NULL,
  review      VARCHAR(1000) NOT NULL,
  INDEX user_review_id (user_id),
  INDEX venue_review_id (venue_id),
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

#table review
INSERT INTO review(user_id, venue_id, review) VALUES
  ('1', '1_venue', 'some random text 1');
INSERT INTO review(user_id, venue_id, review) VALUES
  ('1', '2_venue', 'some random text 2');
INSERT INTO review(user_id, venue_id, review) VALUES
  ('1', '3_venue', 'some random text 3');
INSERT INTO review(user_id, venue_id, review) VALUES
  ('1', '4_venue', 'some random text 4');
INSERT INTO review(user_id, venue_id, review) VALUES
  ('1', '5_venue', 'some random text 5');
INSERT INTO review(user_id, venue_id, review) VALUES
  ('2', '6_venue', 'some random text 6');
INSERT INTO review(user_id, venue_id, review) VALUES
  ('2', '7_venue', 'some random text 7');
INSERT INTO review(user_id, venue_id, review) VALUES
  ('3', '1_venue', 'some random text 8');
INSERT INTO review(user_id, venue_id, review) VALUES
  ('3', '2_venue', 'some random text 9');
INSERT INTO review(user_id, venue_id, review) VALUES
  ('3', '3_venue', 'some random text 10');
INSERT INTO review(user_id, venue_id, review) VALUES
  ('3', '4_venue', 'some random text 11');
INSERT INTO review(user_id, venue_id, review) VALUES
  ('3', '5_venue', 'some random text 12');