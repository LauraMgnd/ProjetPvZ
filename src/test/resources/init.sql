
-- Créer la table "map"
CREATE TABLE Map (
     id_map INT AUTO_INCREMENT PRIMARY KEY,
     ligne INT NOT NULL,
     colonne INT NOT NULL,
     chemin_image VARCHAR(255) DEFAULT NULL
);

-- Créer la table "plante"
CREATE TABLE Plante (
    id_plante INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    point_de_vie INT NOT NULL,
    attaque_par_seconde DECIMAL(5, 2) DEFAULT 0.00,
    degat_attaque INT NOT NULL,
    cout INT NOT NULL,
    soleil_par_seconde DECIMAL(5, 2) DEFAULT 0.00,
    effet ENUM('normal', 'slow low', 'slow medium', 'slow stop') DEFAULT 'normal',
    chemin_image VARCHAR(255) DEFAULT NULL
);

-- Créer la table "zombie"
CREATE TABLE Zombie (
    id_zombie INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    point_de_vie INT NOT NULL,
    attaque_par_seconde DECIMAL(5, 2) DEFAULT 0.00,
    degat_attaque INT NOT NULL,
    vitesse_de_deplacement DECIMAL(5, 2) DEFAULT 0.00,
    chemin_image VARCHAR(255) DEFAULT NULL,
    id_map INT,
    CONSTRAINT fk_zombie_map FOREIGN KEY (id_map) REFERENCES Map(id_map)
);
