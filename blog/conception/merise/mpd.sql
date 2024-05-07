CREATE DATABASE IF NOT EXISTS BLOG;

USE BLOG;

CREATE TABLE T_User (
    U_id INT AUTO_INCREMENT, U_name VARCHAR(50) NOT NULL, U_email VARCHAR(50) NOT NULL, U_password VARCHAR(50) NOT NULL, U_role ENUM('ROLE_ADMIN', 'ROLE_USER') NOT NULL, PRIMARY KEY (U_id)
);

CREATE TABLE T_Article (
    A_id INT AUTO_INCREMENT, A_title VARCHAR(50) NOT NULL, A_image VARCHAR(50) NOT NULL, A_content TEXT NOT NULL, A_created_at DATE NOT NULL, U_id INT NOT NULL, PRIMARY KEY (A_id), FOREIGN KEY (U_id) REFERENCES T_User (U_id)
);

CREATE TABLE T_Category (
    C_id INT AUTO_INCREMENT, C_name VARCHAR(50) NOT NULL, PRIMARY KEY (C_id)
);

CREATE TABLE T_Comment (
    Co_id INT AUTO_INCREMENT, Co_content TEXT NOT NULL, Co_created_at DATE NOT NULL, U_id INT NOT NULL, A_id INT NOT NULL, PRIMARY KEY (Co_id), FOREIGN KEY (U_id) REFERENCES T_User (U_id), FOREIGN KEY (A_id) REFERENCES T_Article (A_id)
);

CREATE TABLE T_Association_Category_Article (
    A_id INT NOT NULL, C_id INT NOT NULL, PRIMARY KEY (A_id, C_id), FOREIGN KEY (A_id) REFERENCES T_Article (A_id), FOREIGN KEY (C_id) REFERENCES T_Category (C_id)
);

-- Ajout d'utilisateurs
INSERT INTO
    T_User (
        U_name, U_email, U_password, U_role
    )
VALUES (
        'Alice Johnson', 'alice@example.com', 'password123', 'ROLE_ADMIN'
    ),
    (
        'Bob Smith', 'bob@example.com', 'password456', 'ROLE_USER'
    );

-- Ajout de catégories liées aux animaux
INSERT INTO
    T_Category (C_name)
VALUES ('Chiens'),
    ('Chats'),
    ('Oiseaux');

-- Ajout d'articles sur les animaux
INSERT INTO
    T_Article (
        A_title, A_image, A_content, A_created_at, U_id
    )
VALUES (
        'Les races de chiens les plus populaires', 'dogs.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit...', '2024-05-01', 1
    ),
    (
        'Conseils pour prendre soin de votre chat', 'cat.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit...', '2024-04-25', 2
    ),
    (
        'Guide pour choisir le bon perroquet', 'parrot.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit...', '2024-04-20', 1
    );

-- Ajout de commentaires sur les articles d'animaux
INSERT INTO
    T_Comment (
        Co_content, Co_created_at, U_id, A_id
    )
VALUES (
        'Super article ! J\'ai appris beaucoup sur les chiens.', '2024-05-03', 2, 1
    ),
    (
        'Merci pour ces conseils utiles.', '2024-04-26', 1, 2
    ),
    (
        'Je suis passionné par les perroquets, merci pour ce guide.', '2024-04-22', 2, 3
    );

-- Association des articles sur les animaux avec des catégories
INSERT INTO
    T_Association_Category_Article (A_id, C_id)
VALUES (1, 1), -- Article 1 associé à la catégorie Chiens
    (2, 2), -- Article 2 associé à la catégorie Chats
    (3, 3);
-- Article 3 associé à la catégorie Oiseaux

SELECT
    A_title,
    A_content,
    A_created_at,
    U_name
FROM T_Article
    INNER JOIN T_User ON T_Article.U_id = T_User.U_id
WHERE
    U_name = 'Alice Johnson';

SELECT
    Co_content,
    Co_created_at,
    U_name
FROM T_Comment
    INNER JOIN T_User ON T_Comment.U_id = T_User.U_id
WHERE
    A_id = 1;