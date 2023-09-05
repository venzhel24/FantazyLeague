INSERT INTO users (id, username, email, password, role) VALUES
(1, 'user1', 'user1@example.com', 'password1', 'USER'),
(2, 'user2', 'user2@example.com', 'password2', 'USER'),
(3, 'admin', 'admin@example.com', 'adminpassword', 'ADMIN');

INSERT INTO athletes (name, country) VALUES
('Athlete1', 'Country1'),
('Athlete2', 'Country2'),
('Athlete3', 'Country3'),
('Athlete4', 'Country4'),
('Athlete5', 'Country5'),
('Athlete6', 'Country6'),
('Athlete7', 'Country7'),
('Athlete8', 'Country8');

INSERT INTO events (id, city, start_date, end_date) VALUES
(1, 'FinishedEventCity', DATEADD(DAY, -10, CURRENT_DATE), DATEADD(DAY, -7, CURRENT_DATE)),
(2, 'CurrentEventCity', DATEADD(DAY, -2, CURRENT_DATE), DATEADD(DAY, 2, CURRENT_DATE)),
(3, 'FutureEventCity', DATEADD(DAY, 5, CURRENT_DATE), DATEADD(DAY, 10, CURRENT_DATE));

INSERT INTO races (id, race_type, distance, date, event_id) VALUES
(1, 'SPRINT', '10KM', '2023-09-12', 1),
(2, 'INDIVIDUAL', '20KM', '2023-09-14', 1),
(3, 'MASS_START', '15KM', '2023-10-08', 1),
(4, 'PURSUIT', '12.5KM', '2023-10-12', 1),
(5, 'SPRINT', '10KM', '2023-11-22', 2),
(6, 'INDIVIDUAL', '20KM', '2023-11-25', 2),
(7, 'MASS_START', '15KM', '2023-12-18', 2),
(8, 'PURSUIT', '12.5KM', '2023-12-22', 2);

INSERT INTO results (id, race_id, athlete_id, rank, bib, prone_shooting, standing_shooting, total_misses, points, behind)
VALUES
(1, 1, 1, 1, 2, 0, 0, 0, 100, '0.0'),
(2, 1, 2, 2, 8, 1, 1, 2, 95, '+29.0'),
(3, 1, 3, 3, 5, 1, 1, 2, 90, '+30.5'),
(4, 1, 4, 4, 4, 1, 2, 3, 85, '+41.7'),
(5, 1, 5, 5, 1, 1, 2, 3, 80, '+44.5'),
(6, 1, 6, 6, 6, 1, 2, 3, 75, '+47.4'),
(7, 1, 7, 7, 7, 1, 3, 4, 70, '+58.9'),
(8, 1, 8, 8, 3, 3, 4, 7, 60, '+1:55.3');

INSERT INTO teams (id, name, user_id, event_id, athlete1_id, athlete2_id, athlete3_id) VALUES
(1, 'Team1', 1, 1, 1, 2, 3),
(2, 'Team2', 2, 1, 2, 3, 4),
(3, 'Team3', 3, 1, 3, 4, 5);