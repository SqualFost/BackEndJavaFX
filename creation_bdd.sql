-- --------------------------------------------------
-- Nettoyage préalable (pour éviter les erreurs si on relance)
-- --------------------------------------------------
DROP TABLE IF EXISTS LigneCommande;
DROP TABLE IF EXISTS categorie_plat;
DROP TABLE IF EXISTS Commande;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Plat;
DROP TABLE IF EXISTS Categorie;

-- --------------------------------------------------
-- Table Categorie
-- --------------------------------------------------
CREATE TABLE Categorie (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom TEXT NOT NULL
);

-- --------------------------------------------------
-- Table Plat
-- --------------------------------------------------
CREATE TABLE Plat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom TEXT NOT NULL,
    description TEXT NOT NULL,
    prix FLOAT NOT NULL,  -- Note : DECIMAL(10,2) serait mieux pour de l'argent, mais FLOAT fonctionne
    photourl TEXT NOT NULL,
    quantite INT NOT NULL, -- Stock disponible
    disponible BOOLEAN DEFAULT TRUE -- Ajout utile pour la gestion du menu
);

-- --------------------------------------------------
-- Table User
-- --------------------------------------------------
CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom TEXT NOT NULL,
    nbPoints INT DEFAULT 0
);

-- --------------------------------------------------
-- Table Commande
-- --------------------------------------------------
CREATE TABLE Commande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    heure_commande DATETIME DEFAULT CURRENT_TIMESTAMP,
    prix_total FLOAT NOT NULL,
    statut VARCHAR(50) NOT NULL, -- ex: "EN_COURS", "TERMINE" (JSON est lourd pour juste un statut)
    id_utilisateur INT NULL,     -- NULL si le client n'est pas identifié
    FOREIGN KEY (id_utilisateur) REFERENCES User(id) ON DELETE SET NULL
);

-- --------------------------------------------------
-- Table categorie_plat (Table de liaison)
-- --------------------------------------------------
CREATE TABLE categorie_plat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_plat INT NOT NULL,
    id_categorie INT NOT NULL,
    FOREIGN KEY (id_plat) REFERENCES Plat(id) ON DELETE CASCADE,
    FOREIGN KEY (id_categorie) REFERENCES Categorie(id) ON DELETE CASCADE
);

-- --------------------------------------------------
-- Table LigneCommande (Détail de la commande)
-- --------------------------------------------------
CREATE TABLE LigneCommande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_commande INT NOT NULL,
    id_plat INT NOT NULL,
    quantite INT NOT NULL,
    options_choisies TEXT, -- ex: "Sans oignon, Sauce piquante"
    FOREIGN KEY (id_commande) REFERENCES Commande(id) ON DELETE CASCADE,
    FOREIGN KEY (id_plat) REFERENCES Plat(id)
);

-- --------------------------------------------------
-- JEU DE DONNÉES DE TEST (OPTIONNEL)
-- --------------------------------------------------
INSERT INTO Categorie (nom) VALUES ('Entrées'), ('Plats'), ('Desserts');

INSERT INTO Plat (nom, description, prix, photourl, quantite) VALUES 
('Nems Poulet', '4 pièces croustillantes', 5.50, 'nems.jpg', 100),
('Bo Bun', 'Vermicelles, boeuf, nems', 12.90, 'bobun.jpg', 50);

-- Liaison : Nems (id 1) sont une Entrée (id 1)
INSERT INTO categorie_plat (id_plat, id_categorie) VALUES (1, 1);
-- Liaison : Bo Bun (id 2) est un Plat (id 2)
INSERT INTO categorie_plat (id_plat, id_categorie) VALUES (2, 2);
