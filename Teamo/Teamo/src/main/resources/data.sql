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


-- Test/Placeholder Users
MERGE INTO users (email, password, first_name, last_name, title, bio, profile_picture_url, banner_url) KEY(email) VALUES
('admin@gmail.com', 'admin123', 'Alice', 'Johnson', 'Frontend Developer', 'Loves building sleek user interfaces.', 'https://i.pravatar.cc/150?img=1', 'https://placehold.co/1000x400'),
('tester@yahoo.com', 'tester123', 'Bob', 'Smith', 'Backend Engineer', 'Passionate about server-side logic.', 'https://i.pravatar.cc/150?img=2','https://placehold.co/1000x400'),
('cr123@gmail.com','rose123', 'Charlie', 'Rose', 'UX Designer', 'Crafts experiences with empath.', 'https://i.pravatar.cc/150?img=3', 'https://placehold.co/1000x400'),
('dwUI@gmail.com', 'dw123', 'Dana', 'Waterboard', 'UI Designer', 'Crafts visuals yada yada.','https://i.pravatar.cc/150?img=4','https://placehold.co/1000x400' ),
('es123@gmail.com', 'es123', 'Eli', 'Steverstein', 'Game Dev', 'Crafts experiences with hamsters.', 'https://i.pravatar.cc/150?img=5','https://placehold.co/1000x400');

MERGE INTO portfolio_items (user_id, title, description, image_url) KEY(user_id, title) VALUES
((SELECT id FROM users WHERE email = 'admin@gmail.com'), 'Website 1', 'test', 'https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'admin@gmail.com'), 'Website 2', 'test','https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'admin@gmail.com'), 'Website 3', 'test','https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'tester@yahoo.com'), 'Project 1', 'test','https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'tester@yahoo.com'), 'Website 1', 'test','https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'tester@yahoo.com'), 'Game 1', 'test','https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'tester@yahoo.com'), 'Project 2', 'test','https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'tester@yahoo.com'), 'Project 3', 'test','https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'cr123@gmail.com'), 'Website 1', 'test','https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'cr123@gmail.com'), 'Website 2', 'test','https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'dwUI@gmail.com'), 'Website 1', 'test','https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'es123@gmail.com'), 'Project 1', 'test','https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'es123@gmail.com'), 'Project 2', 'test','https://placehold.co/600x400'),
((SELECT id FROM users WHERE email = 'es123@gmail.com'), 'Project 3', 'test','https://placehold.co/600x400');
