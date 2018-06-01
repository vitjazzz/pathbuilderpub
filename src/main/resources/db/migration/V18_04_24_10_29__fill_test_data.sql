#table venue
INSERT INTO venue (id, name, lat, lon) VALUES
  ('1_venue',	'1',	50.44176389056172,	30.48809051513672);
INSERT INTO venue (id, name, lat, lon) VALUES
  ('2_venue',	'2',	50.477958757450864,	30.467831801961438);
INSERT INTO venue (id, name, lat, lon) VALUES
  ('3_venue',	'3',	50.441895476004284,	30.541302871297376);
INSERT INTO venue (id, name, lat, lon) VALUES
  ('4_venue',	'4',	50.451952246130936,	30.518231585901958);
INSERT INTO venue (id, name, lat, lon) VALUES
  ('5_venue',	'5',	50.411952246130936,	30.558231585901958);
INSERT INTO venue (id, name, lat, lon) VALUES
  ('6_venue',	'6',	50.491952246130936,	30.508231585901958);
INSERT INTO venue (id, name, lat, lon) VALUES
  ('7_venue',	'7',	50.451952246130936,	30.498231585901958);
INSERT INTO venue (id, name, lat, lon) VALUES
  ('8_venue',	'8',	50.481952246130936,	30.518231585901958);
INSERT INTO venue (id, name, lat, lon) VALUES
  ('9_venue',	'9',	50.411952246130936,	30.538231585901958);
INSERT INTO venue (id, name, lat, lon) VALUES
  ('10_venue',	'10',	50.421952246130936,	30.528231585901958);

#table user
INSERT INTO user (id, email, first_name, last_name, password, role) VALUES
  ('admin_id', 'admin@gmail.com', 'admin', 'admin', '$2a$10$OlFa4VACEqqSeA.ImqPeA.XwJoHEJI6iCLvXCiuUurMgs594PkPPq', 2);
INSERT INTO user (id, email, first_name, last_name, password, role) VALUES
  ('some_id', 'dan@bridge18.com', 'Dan', 'John', '$2a$10$OlFa4VACEqqSeA.ImqPeA.XwJoHEJI6iCLvXCiuUurMgs594PkPPq', 0);
INSERT INTO user (id, email, first_name, last_name, password, role) VALUES
  ('1', '1', '1', '1', '$2a$10$OlFa4VACEqqSeA.ImqPeA.XwJoHEJI6iCLvXCiuUurMgs594PkPPq', 0);
INSERT INTO user (id, email, first_name, last_name, password, role) VALUES
  ('2', '2', '2', '2', '$2a$10$OlFa4VACEqqSeA.ImqPeA.XwJoHEJI6iCLvXCiuUurMgs594PkPPq', 0);
INSERT INTO user (id, email, first_name, last_name, password, role) VALUES
  ('3', '3', '3', '3', '$2a$10$OlFa4VACEqqSeA.ImqPeA.XwJoHEJI6iCLvXCiuUurMgs594PkPPq', 0);
INSERT INTO user (id, email, first_name, last_name, password, role) VALUES
  ('4', '4', '4', '4', '$2a$10$OlFa4VACEqqSeA.ImqPeA.XwJoHEJI6iCLvXCiuUurMgs594PkPPq', 0);
INSERT INTO user (id, email, first_name, last_name, password, role) VALUES
  ('test', 'test', 'test', 'test', '$2a$10$HLaRdDti7HcuLu1cBjtT3e9Jki/g42wZfmF69WI6MX4ZshxW9gSBG', 1);
#test pass - test
#basic pass - password

#table event
INSERT INTO event(id, venue_id, name, description, start_date, end_date) VALUES
  ('1_event', '1_venue', '1 Event', '', '2018-05-05 18:19:03', '2018-05-06 19:19:03');
INSERT INTO event(id, venue_id, name, description, start_date, end_date) VALUES
  ('2_event', '2_venue', '1 Event', '', '2018-05-08 18:19:03', '2018-05-09 19:19:03');
INSERT INTO event(id, venue_id, name, description, start_date, end_date) VALUES
  ('3_event', '4_venue', '1 Event', '', '2018-06-05 18:19:03', '2018-06-06 19:19:03');
INSERT INTO event(id, venue_id, name, description, start_date, end_date) VALUES
  ('4_event', '5_venue', '1 Event', '', '2018-05-05 18:19:03', '2018-05-05 19:19:03');
INSERT INTO event(id, venue_id, name, description, start_date, end_date) VALUES
  ('5_event', '6_venue', '1 Event', '', '2018-05-09 18:19:03', '2018-05-010 19:19:03');
INSERT INTO event(id, venue_id, name, description, start_date, end_date) VALUES
  ('6_event', '6_venue', '1 Event', '', '2018-05-11 18:19:03', '2018-05-12 19:19:03');
INSERT INTO event(id, venue_id, name, description, start_date, end_date) VALUES
  ('7_event', '7_venue', '1 Event', '', '2018-05-11 18:19:03', '2018-05-11 19:19:03');

#table rating
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('1', '1_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('1', '2_venue', 4);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('1', '3_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('1', '4_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('1', '5_venue', 3);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('1', '6_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('1', '7_venue', 4);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('2', '1_venue', 4);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('2', '2_venue', 3);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('2', '3_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('2', '4_venue', 3);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('2', '5_venue', 4);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('2', '6_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('2', '7_venue', 4);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('3', '1_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('3', '2_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('3', '3_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('3', '4_venue', 4);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('3', '5_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('3', '6_venue', 4);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('3', '7_venue', 3);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('4', '1_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('4', '2_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('4', '3_venue', 3);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('4', '4_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('4', '5_venue', 5);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('4', '6_venue', 4);
INSERT INTO rating(user_id, venue_id, rating) VALUES
  ('4', '7_venue', 5);