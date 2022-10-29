INSERT INTO roles VALUES (null, 'ROLE_ADMIN');
INSERT INTO roles VALUES (null, 'ROLE_LIBRARIAN');
INSERT INTO roles VALUES (null, 'ROLE_MEMBER');
INSERT INTO users VALUES (null, b'1',  b'1',  b'1', 'go.admin@library.com', b'1', 'Ana', 'Test', 'Admin', '$2a$10$ogq095yZjfs9P2hWAufFdO0lQ3sxvQhGLlScDqnP0LPqzCO3MnR0y', 'go.admin@library.com');
INSERT INTO users_roles VALUES (1, 1);
INSERT INTO users_roles VALUES (1, 2);
INSERT INTO users_roles VALUES (1, 3);
INSERT INTO users VALUES (null,  b'1',  b'1',  b'1', 'go.librarian@library.com', b'1', 'Bob', 'Test', 'Librarian', '$2a$10$ogq095yZjfs9P2hWAufFdO0lQ3sxvQhGLlScDqnP0LPqzCO3MnR0y', 'go.librarian@library.com');
INSERT INTO users_roles VALUES (2, 2);
INSERT INTO users VALUES (null,  b'1',  b'1',  b'1', 'go.member@library.com', b'1', 'Carlos', 'Test', 'Member', '$2a$10$ogq095yZjfs9P2hWAufFdO0lQ3sxvQhGLlScDqnP0LPqzCO3MnR0y', 'go.member@library.com');
INSERT INTO users_roles VALUES (3, 3);

INSERT INTO library_member_type VALUES (null, 21, 'basic', 0.25);
INSERT INTO library_member_type VALUES (null, 21, 'staff', 0.10);
INSERT INTO library_member_type VALUES (null, 42, 'senior', 0.05);
INSERT INTO library_member_type VALUES (null, 50, 'flat', 0.15);

INSERT INTO library_member VALUES (null, 'Kaka', 'Ricardo', '10001', '51312345', null, 1);
INSERT INTO library_member VALUES (null, 'Messi', 'Lionel', '10002', '51312346', null, 2);
INSERT INTO library_member VALUES (null, 'Ronaldo', 'Cr', '10003', '51312347', null, 3);

INSERT INTO book VALUES (null, 'Leo Tolstoy', 3, '1111', 'A1', 'Penguin and Random House', 'War and Peace', 3);
INSERT INTO book VALUES (null, 'F. Scott Fitzgerald', 5, '2222', 'A2', 'Harper Collins', 'The Great Gatsby', 5);
INSERT INTO book VALUES (null, 'George Eliot', 2, '3333', 'A3', 'Macmillan', 'Middlemarch', 2);
INSERT INTO book VALUES (null, 'Mark Twain', 3, '4444', 'A4', 'Simon and Schuster', 'The Adventures of Huckleberry Finn', 3);
INSERT INTO book VALUES (null, 'Weini Asfaha', 100, '1234', 'A5', 'Simon and Schuster', 'Ertirean Struggle', 100);