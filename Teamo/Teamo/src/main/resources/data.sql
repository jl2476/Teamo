-- Creative Fields
MERGE INTO tags (name, category) KEY(name) VALUES
('Illustration', 'Creative'),
('Digital Art', 'Creative'),
('Graphic Design', 'Creative'),
('Animation', 'Creative'),
('Photography', 'Creative'),
('Film & Video', 'Creative'),
('Music Production', 'Creative'),
('Creative Writing', 'Creative'),
('Fashion Design', 'Creative'),
('Game Design', 'Creative');
-- Technology & Development
MERGE INTO tags (name, category) KEY(name) VALUES
('Software Development', 'Technology'),
('Web Development', 'Technology'),
('Mobile Apps', 'Technology'),
('UI/UX Design', 'Technology'),
('Data Science', 'Technology'),
('Artificial Intelligence', 'Technology'),
('Machine Learning', 'Technology'),
('Cybersecurity', 'Technology'),
('Cloud Computing', 'Technology'),
('AR/VR Development', 'Technology');
-- Performing Arts
MERGE INTO tags (name, category) KEY(name) VALUES
('Singing', 'Performing Arts'),
('Songwriting', 'Performing Arts'),
('Instrumental Music', 'Performing Arts'),
('Dance', 'Performing Arts'),
('Acting', 'Performing Arts'),
('Theater Production', 'Performing Arts'),
('Voice Acting', 'Performing Arts'),
('Choreography', 'Performing Arts'),
('Stand-up Comedy', 'Performing Arts'),
('Spoken Word Poetry', 'Performing Arts');
-- Writing & Publishing
MERGE INTO tags (name, category) KEY(name) VALUES
('Poetry', 'Writing'),
('Fiction', 'Writing'),
('Non-fiction', 'Writing'),
('Screenwriting', 'Writing'),
('Blogging', 'Writing'),
('Journalism', 'Writing'),
('Copywriting', 'Writing'),
('Technical Writing', 'Writing'),
('Editing', 'Writing'),
('Publishing', 'Writing');
-- Community & Collaboration
MERGE INTO tags (name, category) KEY(name) VALUES
('Open Source', 'Community'),
('Hackathons', 'Community'),
('Workshops', 'Community'),
('Mentorship', 'Community'),
('Collaboration', 'Community'),
('Networking', 'Community'),
('Freelancing', 'Community'),
('Entrepreneurship', 'Community'),
('Startups', 'Community'),
('Nonprofits', 'Community');
-- Gaming & Interactive Media
MERGE INTO tags (name, category) KEY(name) VALUES
('Game Development', 'Gaming'),
('Game Art', 'Gaming'),
('Esports', 'Gaming'),
('Streaming', 'Gaming'),
('Level Design', 'Gaming'),
('Narrative Design', 'Gaming'),
('Game Music', 'Gaming'),
('Game Voice Acting', 'Gaming'),
('Modding', 'Gaming'),
('Interactive Storytelling', 'Gaming');
-- Themes & Genres
MERGE INTO tags (name, category) KEY(name) VALUES
('Fantasy', 'Genre'),
('Science Fiction', 'Genre'),
('Romance', 'Genre'),
('Horror', 'Genre'),
('Mystery', 'Genre'),
('Thriller', 'Genre'),
('Comedy', 'Genre'),
('Slice of Life', 'Genre'),
('Adventure', 'Genre'),
('Historical Fiction', 'Genre'),
('Cozy', 'Genre');


--Test/Placeholder Users
MERGE INTO users (email, password, firstName, lastName) KEY(email) VALUES
('admin@gmail.com', 'admin123', 'Admin', 'Account'),
('tester@yahoo.com', 'tester123', 'Tester', 'Account'),
('sj123@gmail.com','steve123', 'Steve', 'Jobs');
