CREATE TABLE IF NOT EXISTS Url_Mapping (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  short_Url VARCHAR(250) NOT NULL,
  long_Url VARCHAR(250) NOT NULL,
  times_Clicked VARCHAR(250) DEFAULT NULL,
  date_Created DATE NOT NULL,
  last_Clicked DATE
);

INSERT INTO Url_Mapping (short_Url, long_Url, times_Clicked, date_Created) VALUES
  ('co21W', 'https://www.google.com', 0, CURRENT_DATE);