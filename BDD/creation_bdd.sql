-- ============================================================
-- 1. COMPLETE CLEANUP
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
-- 2. STRUCTURE CREATION
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
                      photo LONGBLOB DEFAULT NULL
);

-- Table User
CREATE TABLE User (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      nom VARCHAR(255) NOT NULL,
                      nbPoints INT DEFAULT 0
);

-- Table Commande
CREATE TABLE Commande (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          heure_commande DATETIME DEFAULT CURRENT_TIMESTAMP,
                          prix_total FLOAT NOT NULL,
                          statut VARCHAR(50) NOT NULL, -- Ex: 'IN_PROGRESS', 'PAID', 'READY'
                          id_utilisateur INT NULL,
                          FOREIGN KEY (id_utilisateur) REFERENCES User(id) ON DELETE SET NULL
);

-- Table LigneCommande
CREATE TABLE LigneCommande (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               id_commande INT NOT NULL,
                               id_plat INT NOT NULL,
                               quantite INT NOT NULL,
                               options_choisies TEXT, -- Ex: "No onion"
                               FOREIGN KEY (id_commande) REFERENCES Commande(id) ON DELETE CASCADE,
                               FOREIGN KEY (id_plat) REFERENCES Plat(id)
);

-- Table categorie_plat
CREATE TABLE categorie_plat (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                id_plat INT NOT NULL,
                                id_categorie INT NOT NULL,
                                FOREIGN KEY (id_plat) REFERENCES Plat(id) ON DELETE CASCADE,
                                FOREIGN KEY (id_categorie) REFERENCES Categorie(id) ON DELETE CASCADE
);

-- ============================================================
-- 3. INSERTING CATEGORIES (Translated)
-- ============================================================
INSERT INTO Categorie (id, nom) VALUES
                                    (1, 'Appetizers'),
                                    (2, 'Noodles'),
                                    (3, 'Sushi'),
                                    (4, 'Fried Food'),
                                    (5, 'Desserts'),
                                    (6, 'Drinks');

-- ============================================================
-- 4. INSERTING DISHES (IDs 1 to 99 - Translated)
-- ============================================================

-- IDs 1 and 2
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
                                                                                  (1, 'Beef Tartare Sushi', 'Sushi topped with beef tartare', 7.5, 'sushi.png', 100, 1),
                                                                                  (2, 'Ramen', 'Chicken Broth Ramen', 12.9, 'ramen.png', 100, 1);

-- IDs 3 to 19 (Appetizers)
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
                                                                                  (3, 'Veggie Spring Rolls', '3 pcs - Crispy rice wrappers filled with carrots, cabbage, and vermicelli.', 4.5, 'nems_vege.png', 50, 1),
                                                                                  (4, 'Chicken Spring Rolls', '3 pcs - Fried rice wrappers with chicken and vegetables', 4.5, 'nems_poulet.png', 50, 1),
                                                                                  (5, 'Pork Spring Rolls', '3 pcs - Traditional fried rolls with pork and black mushrooms', 4.5, 'nems_porc.png', 50, 1),
                                                                                  (6, 'Shrimp Spring Rolls', '3 pcs - Crispy rolls with whole shrimps', 5.5, 'nems_crevette.png', 50, 1),
                                                                                  (7, 'Vegetable Spring Rolls', '3 pcs - Vegetarian rolls with cabbage and carrots', 4, 'nems_vege.png', 50, 1),
                                                                                  (8, 'Beef Samosa', '3 pcs - Crispy triangles with beef and curry', 4.9, 'samoussa_boeuf.png', 40, 1),
                                                                                  (9, 'Veggie Samosa', '3 pcs - Triangles with potatoes and peas', 4.2, 'samoussa_vege.png', 40, 1),
                                                                                  (10, 'Shrimp Summer Roll', 'Fresh rice paper, vermicelli, salad, mint, shrimp', 3.9, 'springroll_crevette.png', 30, 1),
                                                                                  (11, 'Avocado Summer Roll', 'Vegetarian version with creamy avocado', 3.5, 'springroll_avocat.png', 30, 1),
                                                                                  (12, 'Wakame Salad', 'Japanese seaweed marinated in sesame oil', 4.5, 'wakame.png', 40, 1),
                                                                                  (13, 'Cabbage Salad', 'Crunchy marinated white cabbage, vinegar sauce', 3, 'salade_choux.png', 100, 1),
                                                                                  (14, 'Edamame', 'Steamed soy beans, sea salt', 3.9, 'edamame.png', 60, 1),
                                                                                  (15, 'Miso Soup', 'Dashi broth, miso paste, tofu, seaweed', 3, 'soupe_miso.png', 80, 1),
                                                                                  (16, 'Hot & Sour Soup', 'Spicy sweet and sour soup with chicken (Peking style)', 4.5, 'soupe_pekinoise.png', 40, 1),
                                                                                  (17, 'Steamed Shrimp Dumplings', '4 pcs - Ha Kao, translucent rice dough', 6, 'hakao.png', 30, 1),
                                                                                  (18, 'Steamed Pork Dumplings', '4 pcs - Siu Mai with pork and mushrooms', 5.9, 'siumai.png', 30, 1),
                                                                                  (19, 'Kimchi', 'Korean spicy fermented cabbage', 3.5, 'kimchi.png', 40, 1);

-- IDs 20 to 35 (Noodles)
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
                                                                                  (20, 'Shoyu Ramen', 'Soy sauce broth, chashu pork, soft-boiled egg, naruto', 12.5, 'ramen_shoyu.png', 30, 1),
                                                                                  (21, 'Miso Ramen', 'Rich miso paste broth, corn, butter, pork', 13, 'ramen_miso.png', 30, 1),
                                                                                  (22, 'Tonkotsu Ramen', 'Creamy pork bone broth, ginger, sesame', 13.5, 'ramen_tonkotsu.png', 30, 1),
                                                                                  (23, 'Veggie Ramen', 'Vegetable broth, fried tofu, mushrooms', 11.5, 'ramen_vege.png', 30, 1),
                                                                                  (24, 'Tempura Udon', 'Thick noodles, dashi broth, shrimp fritters', 14, 'udon_tempura.png', 25, 1),
                                                                                  (25, 'Beef Udon', 'Udon noodles stir-fried with beef and onions', 13.5, 'udon_boeuf.png', 25, 1),
                                                                                  (26, 'Chicken Pad Thai', 'Stir-fried rice noodles, peanuts, lime', 12, 'padthai_poulet.png', 40, 1),
                                                                                  (27, 'Shrimp Pad Thai', 'Stir-fried rice noodles, shrimps, bean sprouts', 13, 'padthai_crevette.png', 40, 1),
                                                                                  (28, 'Tofu Pad Thai', 'Vegetarian version with firm tofu', 11, 'padthai_tofu.png', 40, 1),
                                                                                  (29, 'Chicken Yakisoba', 'Stir-fried wheat noodles with Japanese sauce', 11.5, 'yakisoba_poulet.png', 35, 1),
                                                                                  (30, 'Veggie Yakisoba', 'Stir-fried noodles with crunchy vegetables', 10.5, 'yakisoba_vege.png', 35, 1),
                                                                                  (31, 'Beef Bo Bun', 'Cold vermicelli, stir-fried beef, spring rolls, raw veggies', 12.5, 'bobun_boeuf.png', 40, 1),
                                                                                  (32, 'Spring Roll Bo Bun', 'Cold vermicelli, double portion of spring rolls', 11.5, 'bobun_nem.png', 40, 1),
                                                                                  (33, 'Beef Pho', 'Vietnamese soup, beef slices, fresh herbs', 12, 'pho_boeuf.png', 30, 1),
                                                                                  (34, 'Duck Stir-fried Noodles', 'Noodles with vegetables and roasted duck breast', 14.5, 'nouilles_canard.png', 20, 1),
                                                                                  (35, 'Japchae', 'Stir-fried sweet potato noodles with beef (Korean)', 12, 'japchae.png', 20, 1);

-- IDs 36 to 51 (Sushi)
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
                                                                                  (36, 'Salmon Sushi', '2 pcs - Fresh salmon nigiri', 3.5, 'sushi_saumon.png', 50, 1),
                                                                                  (37, 'Tuna Sushi', '2 pcs - Red tuna nigiri', 4, 'sushi_thon.png', 50, 1),
                                                                                  (38, 'Shrimp Sushi', '2 pcs - Cooked shrimp nigiri', 3.5, 'sushi_ebi.png', 50, 1),
                                                                                  (39, 'Sea Bream Sushi', '2 pcs - Royal sea bream nigiri', 3.8, 'sushi_daurade.png', 40, 1),
                                                                                  (40, 'Salmon Maki', '6 pcs - Nori seaweed rolls with salmon', 4.5, 'maki_saumon.png', 60, 1),
                                                                                  (41, 'Tuna Maki', '6 pcs - Nori seaweed rolls with tuna', 5, 'maki_thon.png', 60, 1),
                                                                                  (42, 'Avocado Maki', '6 pcs - Vegetarian avocado maki', 3.9, 'maki_avocat.png', 60, 1),
                                                                                  (43, 'Cucumber Maki', '6 pcs - Fresh kappa maki', 3.5, 'maki_concombre.png', 60, 1),
                                                                                  (44, 'Salmon Avocado Cali', '6 pcs - Sesame outside', 5.5, 'cali_saumon_avocat.png', 50, 1),
                                                                                  (45, 'Cooked Tuna Cali', '6 pcs - Cooked tuna with mayo, avocado', 5.5, 'cali_thon_cuit.png', 50, 1),
                                                                                  (46, 'Tempura Cali', '6 pcs - Fried shrimp inside', 6.5, 'cali_tempura.png', 40, 1),
                                                                                  (47, 'Salmon Spring Roll', '6 pcs - Rice paper, mint, salmon', 5.9, 'spring_saumon.png', 40, 1),
                                                                                  (48, 'Salmon Sashimi', '12 slices of fresh salmon', 14, 'sashimi_saumon.png', 20, 1),
                                                                                  (49, 'Tuna Sashimi', '12 slices of fresh tuna', 16, 'sashimi_thon.png', 20, 1),
                                                                                  (50, 'Salmon Chirashi', 'Bowl of vinegared rice topped with salmon', 13.5, 'chirashi_saumon.png', 25, 1),
                                                                                  (51, 'Dragon Roll', '8 pcs - Eel, avocado, surimi, special sauce', 12, 'dragon_roll.png', 15, 1);

-- IDs 52 to 67 (Fried Food)
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
                                                                                  (52, 'Chicken Karaage', 'Japanese fried chicken marinated in ginger and soy', 6.5, 'karaage.png', 40, 1),
                                                                                  (53, 'Shrimp Tempura', '5 pcs - Light shrimp fritters', 8.5, 'tempura_ebi.png', 30, 1),
                                                                                  (54, 'Veggie Tempura', 'Assortment of crispy fried vegetables', 7, 'tempura_legumes.png', 30, 1),
                                                                                  (55, 'Tonkatsu', 'Panko breaded pork served with tonkatsu sauce', 9.5, 'tonkatsu_plat.png', 25, 1),
                                                                                  (56, 'Chicken Katsu', 'Crispy breaded chicken cutlet', 9, 'chicken_katsu.png', 25, 1),
                                                                                  (57, 'Takoyaki', '6 pcs - Octopus balls with creamy sauce', 7.5, 'takoyaki.png', 30, 1),
                                                                                  (58, 'Fried Gyoza', '5 pcs - Fried chicken dumplings', 6, 'gyoza_frit.png', 40, 1),
                                                                                  (59, 'Spicy Chicken Wings', '6 Korean style spicy wings', 7.9, 'wings_spicy.png', 30, 1),
                                                                                  (60, 'Fried Calamari', 'Battered squid rings', 6.5, 'calamars.png', 35, 1),
                                                                                  (61, 'Breaded Shrimp', '5 pcs - Golden panko breading', 7.5, 'crevettes_panees.png', 35, 1),
                                                                                  (62, 'Korokke', '2 Japanese potato croquettes', 5, 'korokke.png', 20, 1),
                                                                                  (63, 'Agedashi Tofu', 'Fried tofu in dashi broth', 5.5, 'agedashi.png', 20, 1),
                                                                                  (64, 'Sweet Potato Fries', 'Side dish, spiced salt', 4.5, 'frites_patatedouce.png', 50, 1),
                                                                                  (65, 'Cantonese Fried Rice', 'Wok fried rice, eggs, ham, peas', 5.5, 'riz_cantonnais.png', 50, 1),
                                                                                  (66, 'Katsu Donburi', 'Large rice bowl with breaded pork and egg', 13, 'katsudon.png', 20, 1),
                                                                                  (67, 'Karaage Donburi', 'Large rice bowl with fried chicken and mayo', 12.5, 'karaagedon.png', 20, 1);

-- IDs 68 to 83 (Desserts)
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
                                                                                  (68, 'Vanilla Ice Cream Mochi', '2 pcs - Rice dough filled with vanilla ice cream', 4.5, 'mochi_vanille.png', 40, 1),
                                                                                  (69, 'Mango Ice Cream Mochi', '2 pcs - Fruity tropical flavor', 4.5, 'mochi_mangue.png', 40, 1),
                                                                                  (70, 'Green Tea Ice Cream Mochi', '2 pcs - Authentic Matcha flavor', 4.5, 'mochi_matcha.png', 40, 1),
                                                                                  (71, 'Coconut Ice Cream Mochi', '2 pcs - Creamy coconut', 4.5, 'mochi_coco.png', 40, 1),
                                                                                  (72, 'Chocolate Mochi', '2 pcs - Melting chocolate center', 4.5, 'mochi_choco.png', 40, 1),
                                                                                  (73, 'Sakura Mochi', '2 pcs - Cherry blossom flavor', 4.8, 'mochi_sakura.png', 30, 1),
                                                                                  (74, 'Dorayaki', 'Japanese pancakes filled with red bean paste', 4, 'dorayaki.png', 30, 1),
                                                                                  (75, 'Taiyaki', 'Fish-shaped waffle filled with custard cream', 4.5, 'taiyaki.png', 20, 1),
                                                                                  (76, 'Coconut Balls', '2 pcs - Warm steamed coconut pearls', 3.5, 'perle_coco.png', 30, 1),
                                                                                  (77, 'Chinese Nougat', 'With sesame and peanuts', 3, 'nougat.png', 50, 1),
                                                                                  (78, 'Candied Ginger', 'Small sweet strips', 2.5, 'gingembre_confit.png', 50, 1),
                                                                                  (79, 'Lychees in Syrup', 'Cup of fresh lychees', 3.5, 'lychee.png', 40, 1),
                                                                                  (80, 'Exotic Fruit Salad', 'Mango, pineapple, lychee, papaya', 4.5, 'salade_fruits.png', 30, 1),
                                                                                  (81, 'Yuzu Cheesecake', 'Japanese lemon cheesecake', 5.5, 'cheesecake_yuzu.png', 20, 1),
                                                                                  (82, 'Matcha Chocolate Fondant', 'Molten green tea center', 5.5, 'fondant_matcha.png', 20, 1),
                                                                                  (83, 'Fried Banana', 'Banana fritter, honey and sesame', 4, 'banane_frite.png', 25, 1);

-- IDs 84 to 99 (Drinks)
INSERT INTO Plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
                                                                                  (84, 'Coca-Cola', '33cl - Original red can', 2.5, 'coca.png', 100, 1),
                                                                                  (85, 'Coca-Cola Zero', '33cl - Sugar free', 2.5, 'coca_zero.png', 80, 1),
                                                                                  (86, 'Sprite', '33cl - Lemon lime', 2.5, 'sprite.png', 60, 1),
                                                                                  (87, 'Fanta Orange', '33cl - Orange flavor', 2.5, 'fanta.png', 60, 1),
                                                                                  (88, 'Ramune Original', 'Traditional Japanese lemonade with marble', 3.5, 'ramune_nature.png', 40, 1),
                                                                                  (89, 'Ramune Strawberry', 'Strawberry flavored Japanese lemonade', 3.5, 'ramune_fraise.png', 40, 1),
                                                                                  (90, 'Homemade Iced Tea', 'Jasmine green tea, lemon, honey', 3.5, 'icetea_maison.png', 50, 1),
                                                                                  (91, 'Evian', '50cl - Natural mineral water', 2, 'evian.png', 100, 1),
                                                                                  (92, 'San Pellegrino', '50cl - Sparkling water', 2.5, 'sanpellegrino.png', 80, 1),
                                                                                  (93, 'Asahi Beer', '33cl - Japanese blonde beer Super Dry', 4.5, 'asahi.png', 60, 1),
                                                                                  (94, 'Tsingtao Beer', '33cl - Chinese blonde beer', 4, 'tsingtao.png', 60, 1),
                                                                                  (95, 'Singha Beer', '33cl - Thai beer', 4.5, 'singha.png', 50, 1),
                                                                                  (96, 'Sake', '180ml - Japanese rice wine (hot or cold)', 6, 'sake.png', 30, 1),
                                                                                  (97, 'Hot Green Tea', 'Sencha teapot', 3, 'the_vert.png', 100, 1),
                                                                                  (98, 'Jasmine Tea', 'Scented jasmine teapot', 3, 'the_jasmin.png', 100, 1),
                                                                                  (99, 'Brown Sugar Bubble Tea', 'Milk tea, tapioca pearls, brown sugar', 5.5, 'bubbletea.png', 40, 1);

-- ============================================================
-- 5. INSERTING LINKS (Unchanged logic)
-- ============================================================

-- Appetizers
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 1, id FROM Plat WHERE id BETWEEN 3 AND 19;

-- Noodles
INSERT INTO categorie_plat (id_categorie, id_plat) VALUES (2, 2);
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 2, id FROM Plat WHERE id BETWEEN 20 AND 35;

-- Sushi
INSERT INTO categorie_plat (id_categorie, id_plat) VALUES (3, 1);
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 3, id FROM Plat WHERE id BETWEEN 36 AND 51;

-- Fried Food
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 4, id FROM Plat WHERE id BETWEEN 52 AND 67;

-- Desserts
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 5, id FROM Plat WHERE id BETWEEN 68 AND 83;

-- Drinks
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 6, id FROM Plat WHERE id BETWEEN 84 AND 99;

-- Test User
INSERT INTO User (nom, nbPoints) VALUES ('Test Client', 50);