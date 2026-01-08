-- ============================================================
-- 1. NETTOYAGE COMPLET (Ordre inverse des dépendances)
-- ============================================================
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS LigneCommande;
DROP TABLE IF EXISTS categorie_plat;
DROP TABLE IF EXISTS Commande;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Plat;
DROP TABLE IF EXISTS Categorie;
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- 2. CRÉATION DE LA STRUCTURE (TABLES)
-- ============================================================

-- Table Categorie
CREATE TABLE Categorie (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

-- Table Plat
CREATE TABLE Plat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    prix FLOAT NOT NULL,
    photourl VARCHAR(255) NOT NULL,
    quantite INT NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    photo LONGBLOB DEFAULT NULL -- (Optionnel, au cas où)
);

-- Table User (Clients / Fidélité)
CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    nbPoints INT DEFAULT 0
);

-- Table Commande (Le ticket global)
CREATE TABLE Commande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    heure_commande DATETIME DEFAULT CURRENT_TIMESTAMP,
    prix_total FLOAT NOT NULL,
    statut VARCHAR(50) NOT NULL, -- Ex: 'EN_COURS', 'PAYEE', 'PRETE'
    id_utilisateur INT NULL,
    FOREIGN KEY (id_utilisateur) REFERENCES User(id) ON DELETE SET NULL
);

-- Table LigneCommande (Le détail : 2x Sushi, 1x Coca...)
CREATE TABLE LigneCommande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_commande INT NOT NULL,
    id_plat INT NOT NULL,
    quantite INT NOT NULL,
    options_choisies TEXT, -- Ex: "Sans oignon"
    FOREIGN KEY (id_commande) REFERENCES Commande(id) ON DELETE CASCADE,
    FOREIGN KEY (id_plat) REFERENCES Plat(id)
);

-- Table de liaison (Un plat peut être dans une catégorie)
CREATE TABLE categorie_plat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_plat INT NOT NULL,
    id_categorie INT NOT NULL,
    FOREIGN KEY (id_plat) REFERENCES Plat(id) ON DELETE CASCADE,
    FOREIGN KEY (id_categorie) REFERENCES Categorie(id) ON DELETE CASCADE
);

-- ============================================================
-- 3. INSERTION DES CATÉGORIES
-- ============================================================
INSERT INTO Categorie (id, nom) VALUES
(1, 'Entrées'),
(2, 'Nouilles'),
(3, 'Sushis'),
(4, 'Friture'),
(5, 'Desserts'),
(6, 'Boissons');

-- ============================================================
-- 4. INSERTION DES PLATS (IDs 1 à 99 - DONNÉES EXACTES)
-- ============================================================

-- IDs 1 et 2
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
(1, 'Sushi', 'Sushi au tartare de boeuf', 7.5, 'sushi.jpg', 100, 1),
(2, 'Ramen', 'Ramen au Bouillon de Poulet', 12.9, 'ramen.jpg', 100, 1);

-- IDs 3 à 19 (Entrées)
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
(3, 'Nems aux Légumes', '3 pièces - Galettes de riz croustillantes garnies de carottes, choux et vermicelles.', 4.5, 'nems_vege.png', 50, 1),
(4, 'Nems Poulet', '3 pièces - Galettes de riz frites au poulet et légumes', 4.5, 'nems_poulet.png', 50, 1),
(5, 'Nems Porc', '3 pièces - Traditionnels nems au porc et champignons noirs', 4.5, 'nems_porc.png', 50, 1),
(6, 'Nems Crevettes', '3 pièces - Nems croustillants aux crevettes entières', 5.5, 'nems_crevette.png', 50, 1),
(7, 'Nems Légumes', '3 pièces - Nems végétariens aux choux et carottes', 4, 'nems_vege.png', 50, 1),
(8, 'Samoussa Boeuf', '3 pièces - Triangles croustillants au boeuf et curry', 4.9, 'samoussa_boeuf.png', 40, 1),
(9, 'Samoussa Légumes', '3 pièces - Triangles aux pommes de terre et petits pois', 4.2, 'samoussa_vege.png', 40, 1),
(10, 'Rouleau Printemps Crevette', 'Galette fraîche, vermicelles, salade, menthe, crevette', 3.9, 'springroll_crevette.png', 30, 1),
(11, 'Rouleau Printemps Avocat', 'Version végétarienne avec avocat fondant', 3.5, 'springroll_avocat.png', 30, 1),
(12, 'Salade Wakame', 'Algues japonaises marinées au sésame', 4.5, 'wakame.png', 40, 1),
(13, 'Salade de Choux', 'Choux blanc mariné croquant, sauce vinaigrée', 3, 'salade_choux.png', 100, 1),
(14, 'Edamame', 'Fèves de soja à la vapeur, fleur de sel', 3.9, 'edamame.png', 60, 1),
(15, 'Soupe Miso', 'Bouillon dashi, pâte miso, tofu, algues', 3, 'soupe_miso.png', 80, 1),
(16, 'Soupe Pékinoise', 'Soupe aigre-douce pimentée au poulet', 4.5, 'soupe_pekinoise.png', 40, 1),
(17, 'Raviolis Vapeur Crevette', '4 pièces - Ha Kao, pâte de riz translucide', 6, 'hakao.png', 30, 1),
(18, 'Bouchées Porc Vapeur', '4 pièces - Siu Mai au porc et champignons', 5.9, 'siumai.png', 30, 1),
(19, 'Kimchi', 'Choux fermenté épicé coréen', 3.5, 'kimchi.png', 40, 1);

-- IDs 20 à 35 (Nouilles)
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
(20, 'Ramen Shoyu', 'Bouillon soja, porc chashu, oeuf mollet, naruto', 12.5, 'ramen_shoyu.png', 30, 1),
(21, 'Ramen Miso', 'Bouillon pâte miso riche, maïs, beurre, porc', 13, 'ramen_miso.png', 30, 1),
(22, 'Ramen Tonkotsu', 'Bouillon os de porc crémeux, gingembre, sésame', 13.5, 'ramen_tonkotsu.png', 30, 1),
(23, 'Ramen Végétarien', 'Bouillon légumes, tofu frit, champignons', 11.5, 'ramen_vege.png', 30, 1),
(24, 'Udon Tempura', 'Grosses nouilles, bouillon dashi, beignets crevettes', 14, 'udon_tempura.png', 25, 1),
(25, 'Udon Boeuf', 'Nouilles udon sautées au boeuf et oignons', 13.5, 'udon_boeuf.png', 25, 1),
(26, 'Pad Thai Poulet', 'Nouilles de riz sautées, cacahuètes, citron vert', 12, 'padthai_poulet.png', 40, 1),
(27, 'Pad Thai Crevettes', 'Nouilles de riz sautées, crevettes, soja', 13, 'padthai_crevette.png', 40, 1),
(28, 'Pad Thai Tofu', 'Version végétarienne au tofu ferme', 11, 'padthai_tofu.png', 40, 1),
(29, 'Yakisoba Poulet', 'Nouilles de blé sautées sauce japonaise', 11.5, 'yakisoba_poulet.png', 35, 1),
(30, 'Yakisoba Légumes', 'Nouilles sautées mélange de légumes croquants', 10.5, 'yakisoba_vege.png', 35, 1),
(31, 'Bo Bun Boeuf', 'Vermicelles froids, boeuf sauté, nems, crudités', 12.5, 'bobun_boeuf.png', 40, 1),
(32, 'Bo Bun Nem', 'Vermicelles froids, double ration de nems', 11.5, 'bobun_nem.png', 40, 1),
(33, 'Pho Boeuf', 'Soupe vietnamienne, lamelles de boeuf, herbes fraîches', 12, 'pho_boeuf.png', 30, 1),
(34, 'Nouilles Sautées Canard', 'Nouilles aux légumes et magret de canard laqué', 14.5, 'nouilles_canard.png', 20, 1),
(35, 'Japchae', 'Nouilles de patate douce sautées au boeuf (Corée)', 12, 'japchae.png', 20, 1);

-- IDs 36 à 51 (Sushis)
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
(36, 'Sushi Saumon', '2 pièces - Nigiri saumon frais', 3.5, 'sushi_saumon.png', 50, 1),
(37, 'Sushi Thon', '2 pièces - Nigiri thon rouge', 4, 'sushi_thon.png', 50, 1),
(38, 'Sushi Crevette', '2 pièces - Nigiri crevette cuite', 3.5, 'sushi_ebi.png', 50, 1),
(39, 'Sushi Daurade', '2 pièces - Nigiri daurade royale', 3.8, 'sushi_daurade.png', 40, 1),
(40, 'Maki Saumon', '6 pièces - Rouleaux algue nori et saumon', 4.5, 'maki_saumon.png', 60, 1),
(41, 'Maki Thon', '6 pièces - Rouleaux algue nori et thon', 5, 'maki_thon.png', 60, 1),
(42, 'Maki Avocat', '6 pièces - Maki végétarien avocat', 3.9, 'maki_avocat.png', 60, 1),
(43, 'Maki Concombre', '6 pièces - Maki frais kappa maki', 3.5, 'maki_concombre.png', 60, 1),
(44, 'California Saumon Avocat', '6 pièces - Sésame extérieur', 5.5, 'cali_saumon_avocat.png', 50, 1),
(45, 'California Thon Cuit', '6 pièces - Thon cuit mayo, avocat', 5.5, 'cali_thon_cuit.png', 50, 1),
(46, 'California Tempura', '6 pièces - Crevette frite à l''intérieur', 6.5, 'cali_tempura.png', 40, 1),
(47, 'Spring Roll Saumon', '6 pièces - Feuille de riz, menthe, saumon', 5.9, 'spring_saumon.png', 40, 1),
(48, 'Sashimi Saumon', '12 tranches de saumon frais', 14, 'sashimi_saumon.png', 20, 1),
(49, 'Sashimi Thon', '12 tranches de thon frais', 16, 'sashimi_thon.png', 20, 1),
(50, 'Chirashi Saumon', 'Bol de riz vinaigré recouvert de saumon', 13.5, 'chirashi_saumon.png', 25, 1),
(51, 'Dragon Roll', '8 pièces - Anguille, avocat, surimi, sauce spéciale', 12, 'dragon_roll.png', 15, 1);

-- IDs 52 à 67 (Friture)
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
(52, 'Poulet Karaage', 'Poulet frit à la japonaise mariné gingembre soja', 6.5, 'karaage.png', 40, 1),
(53, 'Tempura Crevettes', '5 pièces - Beignets de crevettes légers', 8.5, 'tempura_ebi.png', 30, 1),
(54, 'Tempura Légumes', 'Assortiment de légumes frits croustillants', 7, 'tempura_legumes.png', 30, 1),
(55, 'Tonkatsu', 'Porc pané panko servi avec sauce tonkatsu', 9.5, 'tonkatsu_plat.png', 25, 1),
(56, 'Poulet Katsu', 'Escalope de poulet panée croustillante', 9, 'chicken_katsu.png', 25, 1),
(57, 'Takoyaki', '6 pièces - Boulettes de poulpe sauce onctueuse', 7.5, 'takoyaki.png', 30, 1),
(58, 'Gyoza Frits', '5 pièces - Raviolis au poulet frits', 6, 'gyoza_frit.png', 40, 1),
(59, 'Ailes de Poulet Spicy', '6 Ailes de poulet sauce piquante coréenne', 7.9, 'wings_spicy.png', 30, 1),
(60, 'Calamars Frits', 'Anneaux de calamars en beignet', 6.5, 'calamars.png', 35, 1),
(61, 'Crevettes Panées', '5 pièces - Panure panko dorée', 7.5, 'crevettes_panees.png', 35, 1),
(62, 'Korokke', '2 Croquettes de pomme de terre japonaises', 5, 'korokke.png', 20, 1),
(63, 'Agedashi Tofu', 'Tofu frit dans un bouillon dashi', 5.5, 'agedashi.png', 20, 1),
(64, 'Frites de Patate Douce', 'Accompagnement, sel épicé', 4.5, 'frites_patatedouce.png', 50, 1),
(65, 'Riz Sauté Cantonnais', 'Riz sauté au wok, oeufs, jambon, petits pois', 5.5, 'riz_cantonnais.png', 50, 1),
(66, 'Donburi Katsu', 'Grand bol de riz avec porc pané et oeuf', 13, 'katsudon.png', 20, 1),
(67, 'Donburi Karaage', 'Grand bol de riz avec poulet frit et mayo', 12.5, 'karaagedon.png', 20, 1);

-- IDs 68 à 83 (Desserts)
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
(68, 'Mochi Glacé Vanille', '2 pièces - Pâte de riz fourrée glace vanille', 4.5, 'mochi_vanille.png', 40, 1),
(69, 'Mochi Glacé Mangue', '2 pièces - Saveur fruitée tropicale', 4.5, 'mochi_mangue.png', 40, 1),
(70, 'Mochi Glacé Thé Vert', '2 pièces - Saveur Matcha authentique', 4.5, 'mochi_matcha.png', 40, 1),
(71, 'Mochi Glacé Coco', '2 pièces - Noix de coco onctueuse', 4.5, 'mochi_coco.png', 40, 1),
(72, 'Mochi Chocolat', '2 pièces - Coeur fondant chocolat', 4.5, 'mochi_choco.png', 40, 1),
(73, 'Mochi Sakura', '2 pièces - Saveur fleur de cerisier', 4.8, 'mochi_sakura.png', 30, 1),
(74, 'Dorayaki', 'Pancakes japonais fourrés pâte haricot rouge', 4, 'dorayaki.png', 30, 1),
(75, 'Taiyaki', 'Gaufre forme poisson fourrée crème pâtissière', 4.5, 'taiyaki.png', 20, 1),
(76, 'Perles de Coco', '2 pièces - Boules chaudes vapeur coco', 3.5, 'perle_coco.png', 30, 1),
(77, 'Nougat Chinois', 'Aux sésames et cacahuètes', 3, 'nougat.png', 50, 1),
(78, 'Gingembre Confit', 'Petites lamelles sucrées', 2.5, 'gingembre_confit.png', 50, 1),
(79, 'Lychees au Sirop', 'Coupe de lychees frais', 3.5, 'lychee.png', 40, 1),
(80, 'Salade de Fruits Exotiques', 'Mangue, ananas, lychee, papaye', 4.5, 'salade_fruits.png', 30, 1),
(81, 'Cheesecake Yuzu', 'Gâteau fromage au citron japonais', 5.5, 'cheesecake_yuzu.png', 20, 1),
(82, 'Fondant Chocolat Matcha', 'Coeur coulant au thé vert', 5.5, 'fondant_matcha.png', 20, 1),
(83, 'Banane Frite', 'Beignet de banane, miel et sésame', 4, 'banane_frite.png', 25, 1);

-- IDs 84 à 99 (Boissons)
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
(84, 'Coca-Cola', '33cl - Canette rouge originale', 2.5, 'coca.png', 100, 1),
(85, 'Coca-Cola Zéro', '33cl - Sans sucres', 2.5, 'coca_zero.png', 80, 1),
(86, 'Sprite', '33cl - Citron citron vert', 2.5, 'sprite.png', 60, 1),
(87, 'Fanta Orange', '33cl - Goût orange', 2.5, 'fanta.png', 60, 1),
(88, 'Ramune Nature', 'Limonade japonaise traditionnelle bille', 3.5, 'ramune_nature.png', 40, 1),
(89, 'Ramune Fraise', 'Limonade japonaise parfum fraise', 3.5, 'ramune_fraise.png', 40, 1),
(90, 'Thé Glacé Maison', 'Thé vert jasmin, citron, miel', 3.5, 'icetea_maison.png', 50, 1),
(91, 'Evian', '50cl - Eau minérale naturelle', 2, 'evian.png', 100, 1),
(92, 'San Pellegrino', '50cl - Eau gazeuse', 2.5, 'sanpellegrino.png', 80, 1),
(93, 'Bière Asahi', '33cl - Bière blonde japonaise Super Dry', 4.5, 'asahi.png', 60, 1),
(94, 'Bière Tsingtao', '33cl - Bière blonde chinoise', 4, 'tsingtao.png', 60, 1),
(95, 'Bière Singha', '33cl - Bière thaïlandaise', 4.5, 'singha.png', 50, 1),
(96, 'Sake', '180ml - Vin de riz japonais (chaud ou froid)', 6, 'sake.png', 30, 1),
(97, 'Thé Vert Chaud', 'Théière Sencha', 3, 'the_vert.png', 100, 1),
(98, 'Thé Jasmin', 'Théière parfumée au jasmin', 3, 'the_jasmin.png', 100, 1),
(99, 'Bubble Tea Brown Sugar', 'Thé au lait, perles de tapioca, sucre roux', 5.5, 'bubbletea.png', 40, 1);

-- ============================================================
-- 5. INSERTION DES LIENS (POUR L'AFFICHAGE PAR CATÉGORIE)
-- ============================================================

-- Entrées (inclut le reste des entrées + le Ramen isolé s'il est considéré comme une entrée)
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 1, id FROM Plat WHERE id BETWEEN 3 AND 19;

-- Nouilles (Inclut le Ramen ID 2 et les IDs 20 à 35)
INSERT INTO categorie_plat (id_categorie, id_plat) VALUES (2, 2);
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 2, id FROM Plat WHERE id BETWEEN 20 AND 35;

-- Sushis (Inclut le Sushi ID 1 et les IDs 36 à 51)
INSERT INTO categorie_plat (id_categorie, id_plat) VALUES (3, 1);
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 3, id FROM Plat WHERE id BETWEEN 36 AND 51;

-- Friture (IDs 52 à 67)
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 4, id FROM Plat WHERE id BETWEEN 52 AND 67;

-- Desserts (IDs 68 à 83)
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 5, id FROM Plat WHERE id BETWEEN 68 AND 83;

-- Boissons (IDs 84 à 99)
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 6, id FROM Plat WHERE id BETWEEN 84 AND 99;

-- Test d'un utilisateur
INSERT INTO User (nom, nbPoints) VALUES ('Client Test', 50);