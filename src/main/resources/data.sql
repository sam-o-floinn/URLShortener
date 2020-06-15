--DROP TABLE IF EXISTS Url_Object;

CREATE TABLE IF NOT EXISTS Url_Object (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  short_Url VARCHAR(250) NOT NULL,
  long_Url VARCHAR(250) NOT NULL,
  times_Clicked VARCHAR(250) DEFAULT NULL
);

INSERT INTO Url_Object (short_Url, long_Url, times_Clicked) VALUES
  ('co21W', 'https://www.google.com', 0);

INSERT INTO Code_Object(id, code) VALUES (0, 'co21W');

--  ('b1tl9', 'https://www.facebook.com/messages', 1);