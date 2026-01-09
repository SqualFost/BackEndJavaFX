-- ============================================================
-- 1. FULL CLEANUP (Dropping tables)
-- ============================================================
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS LigneCommande;
DROP TABLE IF EXISTS lignecommande;
DROP TABLE IF EXISTS categorie_plat;
DROP TABLE IF EXISTS Commande;
DROP TABLE IF EXISTS commande;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS Plat;
DROP TABLE IF EXISTS plat;
DROP TABLE IF EXISTS Categorie;
DROP TABLE IF EXISTS categorie;
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- 2. STRUCTURE CREATION
-- ============================================================

-- Table categorie
CREATE TABLE categorie (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           nom VARCHAR(100) NOT NULL
);

-- Table plat
CREATE TABLE plat (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      nom VARCHAR(255) NOT NULL,
                      description TEXT NOT NULL,
                      prix FLOAT NOT NULL,
                      photourl VARCHAR(255) NOT NULL,
                      quantite INT NOT NULL,
                      disponible BOOLEAN DEFAULT TRUE,
                      photo LONGBLOB DEFAULT NULL
);

-- Table user
CREATE TABLE user (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      nom VARCHAR(255) NOT NULL,
                      nbPoints INT DEFAULT 0
);

-- Table commande
CREATE TABLE commande (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          heure_commande DATETIME DEFAULT CURRENT_TIMESTAMP,
                          prix_total FLOAT NOT NULL,
                          statut VARCHAR(50) NOT NULL,
                          id_utilisateur INT NULL,
                          FOREIGN KEY (id_utilisateur) REFERENCES user(id) ON DELETE SET NULL
);

-- Table lignecommande
CREATE TABLE lignecommande (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               id_commande INT NOT NULL,
                               id_plat INT NOT NULL,
                               quantite INT NOT NULL,
                               options_choisies TEXT,
                               FOREIGN KEY (id_commande) REFERENCES commande(id) ON DELETE CASCADE,
                               FOREIGN KEY (id_plat) REFERENCES plat(id)
);

-- Table categorie_plat
CREATE TABLE categorie_plat (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                id_plat INT NOT NULL,
                                id_categorie INT NOT NULL,
                                FOREIGN KEY (id_plat) REFERENCES plat(id) ON DELETE CASCADE,
                                FOREIGN KEY (id_categorie) REFERENCES categorie(id) ON DELETE CASCADE
);

-- ============================================================
-- 3. INSERTING CATEGORIES (Translated to English)
-- ============================================================
INSERT INTO categorie (id, nom) VALUES
                                    (1, 'Appetizers'),
                                    (2, 'Noodles'),
                                    (3, 'Sushi'),
                                    (4, 'Fried Dishes'),
                                    (5, 'Desserts'),
                                    (6, 'Drinks');

-- ============================================================
-- 4. INSERTING DISHES (IDs 1 to 99 - Translated)
-- ============================================================

INSERT INTO plat (id, nom, description, prix, photourl, quantite, disponible) VALUES
                                                                                  (1, 'Sushi', 'Beef Tartare Sushi', 7.5, 'sushi.jpg', 100, 1),
                                                                                  (2, 'Ramen', 'Chicken Broth Ramen', 12.9, 'ramen.jpg', 100, 1),
                                                                                  (3, 'Veggie Spring Rolls', '3 pieces - Crispy rice wrappers filled with carrots, cabbage, and vermicelli.', 4.5, 'nems_vege.png', 50, 1),
                                                                                  (4, 'Chicken Spring Rolls', '3 pieces - Fried rice wrappers with chicken and vegetables', 4.5, 'nems_poulet.png', 50, 1),
                                                                                  (5, 'Pork Spring Rolls', '3 pieces - Traditional pork and black mushroom spring rolls', 4.5, 'nems_porc.png', 50, 1),
                                                                                  (6, 'Shrimp Spring Rolls', '3 pieces - Crispy spring rolls with whole shrimps', 5.5, 'nems_crevette.png', 50, 1),
                                                                                  (7, 'Vegetable Spring Rolls', '3 pieces - Vegetarian spring rolls with cabbage and carrots', 4, 'nems_vege.png', 50, 1),
                                                                                  (8, 'Beef Samosa', '3 pieces - Crispy triangles with beef and curry', 4.9, 'samoussa_boeuf.png', 40, 1),
                                                                                  (9, 'Veggie Samosa', '3 pieces - Triangles with potatoes and peas', 4.2, 'samoussa_vege.png', 40, 1),
                                                                                  (10, 'Shrimp Summer Roll', 'Fresh rice paper roll, vermicelli, lettuce, mint, shrimp', 3.9, 'springroll_crevette.png', 30, 1),
                                                                                  (11, 'Avocado Summer Roll', 'Vegetarian version with creamy avocado', 3.5, 'springroll_avocat.png', 30, 1),
                                                                                  (12, 'Wakame Salad', 'Japanese seaweed salad marinated with sesame', 4.5, 'wakame.png', 40, 1),
                                                                                  (13, 'Cabbage Salad', 'Crunchy marinated white cabbage, vinegar sauce', 3, 'salade_choux.png', 100, 1),
                                                                                  (14, 'Edamame', 'Steamed soybeans, sea salt', 3.9, 'edamame.png', 60, 1),
                                                                                  (15, 'Miso Soup', 'Dashi broth, miso paste, tofu, seaweed', 3, 'soupe_miso.png', 80, 1),
                                                                                  (16, 'Hot and Sour Soup', 'Spicy sweet and sour soup with chicken (Peking style)', 4.5, 'soupe_pekinoise.png', 40, 1),
                                                                                  (17, 'Steamed Shrimp Dumplings', '4 pieces - Ha Kao, translucent rice dough', 6, 'hakao.png', 30, 1),
                                                                                  (18, 'Steamed Pork Dumplings', '4 pieces - Siu Mai with pork and mushrooms', 5.9, 'siumai.png', 30, 1),
                                                                                  (19, 'Kimchi', 'Spicy Korean fermented cabbage', 3.5, 'kimchi.png', 40, 1),
                                                                                  (20, 'Shoyu Ramen', 'Soy sauce broth, chashu pork, soft-boiled egg, naruto', 12.5, 'ramen_shoyu.png', 30, 1),
                                                                                  (21, 'Miso Ramen', 'Rich miso paste broth, corn, butter, pork', 13, 'ramen_miso.png', 30, 1),
                                                                                  (22, 'Tonkotsu Ramen', 'Creamy pork bone broth, ginger, sesame', 13.5, 'ramen_tonkotsu.png', 30, 1),
                                                                                  (23, 'Vegetarian Ramen', 'Vegetable broth, fried tofu, mushrooms', 11.5, 'ramen_vege.png', 30, 1),
                                                                                  (24, 'Tempura Udon', 'Thick noodles, dashi broth, shrimp fritters', 14, 'udon_tempura.png', 25, 1),
                                                                                  (25, 'Beef Udon', 'Stir-fried udon noodles with beef and onions', 13.5, 'udon_boeuf.png', 25, 1),
                                                                                  (26, 'Chicken Pad Thai', 'Stir-fried rice noodles, peanuts, lime', 12, 'padthai_poulet.png', 40, 1),
                                                                                  (27, 'Shrimp Pad Thai', 'Stir-fried rice noodles, shrimps, soy sprouts', 13, 'padthai_crevette.png', 40, 1),
                                                                                  (28, 'Tofu Pad Thai', 'Vegetarian version with firm tofu', 11, 'padthai_tofu.png', 40, 1),
                                                                                  (29, 'Chicken Yakisoba', 'Stir-fried wheat noodles with Japanese sauce', 11.5, 'yakisoba_poulet.png', 35, 1),
                                                                                  (30, 'Veggie Yakisoba', 'Stir-fried noodles with crunchy vegetables', 10.5, 'yakisoba_vege.png', 35, 1),
                                                                                  (31, 'Beef Bo Bun', 'Cold vermicelli, stir-fried beef, spring rolls, raw veggies', 12.5, 'bobun_boeuf.png', 40, 1),
                                                                                  (32, 'Spring Roll Bo Bun', 'Cold vermicelli, double portion of spring rolls', 11.5, 'bobun_nem.png', 40, 1),
                                                                                  (33, 'Beef Pho', 'Vietnamese soup, beef slices, fresh herbs', 12, 'pho_boeuf.png', 30, 1),
                                                                                  (34, 'Duck Stir-fried Noodles', 'Noodles with vegetables and roasted duck breast', 14.5, 'nouilles_canard.png', 20, 1),
                                                                                  (35, 'Japchae', 'Stir-fried sweet potato noodles with beef (Korean)', 12, 'japchae.png', 20, 1),
                                                                                  (36, 'Salmon Sushi', '2 pieces - Fresh salmon Nigiri', 3.5, 'sushi_saumon.png', 50, 1),
                                                                                  (37, 'Tuna Sushi', '2 pieces - Red tuna Nigiri', 4, 'sushi_thon.png', 50, 1),
                                                                                  (38, 'Shrimp Sushi', '2 pieces - Cooked shrimp Nigiri', 3.5, 'sushi_ebi.png', 50, 1),
                                                                                  (39, 'Sea Bream Sushi', '2 pieces - Royal Sea Bream Nigiri', 3.8, 'sushi_daurade.png', 40, 1),
                                                                                  (40, 'Salmon Maki', '6 pieces - Nori seaweed rolls with salmon', 4.5, 'maki_saumon.png', 60, 1),
                                                                                  (41, 'Tuna Maki', '6 pieces - Nori seaweed rolls with tuna', 5, 'maki_thon.png', 60, 1),
                                                                                  (42, 'Avocado Maki', '6 pieces - Vegetarian avocado Maki', 3.9, 'maki_avocat.png', 60, 1),
                                                                                  (43, 'Cucumber Maki', '6 pieces - Fresh Kappa Maki', 3.5, 'maki_concombre.png', 60, 1),
                                                                                  (44, 'Salmon Avocado California', '6 pieces - Sesame seeds on the outside', 5.5, 'cali_saumon_avocat.png', 50, 1),
                                                                                  (45, 'Cooked Tuna California', '6 pieces - Cooked tuna with mayo and avocado', 5.5, 'cali_thon_cuit.png', 50, 1),
                                                                                  (46, 'Tempura California', '6 pieces - Fried shrimp inside', 6.5, 'cali_tempura.png', 40, 1),
                                                                                  (47, 'Salmon Spring Roll', '6 pieces - Rice paper, mint, salmon', 5.9, 'spring_saumon.png', 40, 1),
                                                                                  (48, 'Salmon Sashimi', '12 slices of fresh salmon', 14, 'sashimi_saumon.png', 20, 1),
                                                                                  (49, 'Tuna Sashimi', '12 slices of fresh tuna', 16, 'sashimi_thon.png', 20, 1),
                                                                                  (50, 'Salmon Chirashi', 'Bowl of vinegared rice topped with salmon', 13.5, 'chirashi_saumon.png', 25, 1),
                                                                                  (51, 'Dragon Roll', '8 pieces - Eel, avocado, crab stick, special sauce', 12, 'dragon_roll.png', 15, 1),
                                                                                  (52, 'Chicken Karaage', 'Japanese fried chicken marinated in ginger and soy', 6.5, 'karaage.png', 40, 1),
                                                                                  (53, 'Shrimp Tempura', '5 pieces - Lightly battered shrimp fritters', 8.5, 'tempura_ebi.png', 30, 1),
                                                                                  (54, 'Vegetable Tempura', 'Assortment of crispy fried vegetables', 7, 'tempura_legumes.png', 30, 1),
                                                                                  (55, 'Tonkatsu', 'Panko breaded pork served with Tonkatsu sauce', 9.5, 'tonkatsu_plat.png', 25, 1),
                                                                                  (56, 'Chicken Katsu', 'Crispy breaded chicken cutlet', 9, 'chicken_katsu.png', 25, 1),
                                                                                  (57, 'Takoyaki', '6 pieces - Octopus balls with creamy sauce', 7.5, 'takoyaki.png', 30, 1),
                                                                                  (58, 'Fried Gyoza', '5 pieces - Fried chicken dumplings', 6, 'gyoza_frit.png', 40, 1),
                                                                                  (59, 'Spicy Chicken Wings', '6 Chicken wings with Korean spicy sauce', 7.9, 'wings_spicy.png', 30, 1),
                                                                                  (60, 'Fried Calamari', 'Squid rings in batter', 6.5, 'calamars.png', 35, 1),
                                                                                  (61, 'Breaded Shrimp', '5 pieces - Golden Panko breading', 7.5, 'crevettes_panees.png', 35, 1),
                                                                                  (62, 'Korokke', '2 Japanese potato croquettes', 5, 'korokke.png', 20, 1),
                                                                                  (63, 'Agedashi Tofu', 'Fried tofu in dashi broth', 5.5, 'agedashi.png', 20, 1),
                                                                                  (64, 'Sweet Potato Fries', 'Side dish, spicy salt', 4.5, 'frites_patatedouce.png', 50, 1),
                                                                                  (65, 'Cantonese Fried Rice', 'Wok-fried rice, eggs, ham, peas', 5.5, 'riz_cantonnais.png', 50, 1),
                                                                                  (66, 'Katsu Donburi', 'Large rice bowl topped with breaded pork and egg', 13, 'katsudon.png', 20, 1),
                                                                                  (67, 'Karaage Donburi', 'Large rice bowl topped with fried chicken and mayo', 12.5, 'karaagedon.png', 20, 1),
                                                                                  (68, 'Vanilla Mochi Ice Cream', '2 pieces - Rice dough filled with vanilla ice cream', 4.5, 'mochi_vanille.png', 40, 1),
                                                                                  (69, 'Mango Mochi Ice Cream', '2 pieces - Tropical fruit flavor', 4.5, 'mochi_mangue.png', 40, 1),
                                                                                  (70, 'Green Tea Mochi Ice Cream', '2 pieces - Authentic Matcha flavor', 4.5, 'mochi_matcha.png', 40, 1),
                                                                                  (71, 'Coconut Mochi Ice Cream', '2 pieces - Creamy coconut', 4.5, 'mochi_coco.png', 40, 1),
                                                                                  (72, 'Chocolate Mochi', '2 pieces - Melting chocolate center', 4.5, 'mochi_choco.png', 40, 1),
                                                                                  (73, 'Sakura Mochi', '2 pieces - Cherry blossom flavor', 4.8, 'mochi_sakura.png', 30, 1),
                                                                                  (74, 'Dorayaki', 'Japanese pancakes filled with red bean paste', 4, 'dorayaki.png', 30, 1),
                                                                                  (75, 'Taiyaki', 'Fish-shaped waffle filled with custard cream', 4.5, 'taiyaki.png', 20, 1),
                                                                                  (76, 'Coconut Balls', '2 pieces - Hot steamed coconut balls (Perles de Coco)', 3.5, 'perle_coco.png', 30, 1),
                                                                                  (77, 'Chinese Nougat', 'With sesame and peanuts', 3, 'nougat.png', 50, 1),
                                                                                  (78, 'Candied Ginger', 'Small sweet strips', 2.5, 'gingembre_confit.png', 50, 1),
                                                                                  (79, 'Lychees in Syrup', 'Bowl of fresh lychees', 3.5, 'lychee.png', 40, 1),
                                                                                  (80, 'Exotic Fruit Salad', 'Mango, pineapple, lychee, papaya', 4.5, 'salade_fruits.png', 30, 1),
                                                                                  (81, 'Yuzu Cheesecake', 'Japanese lemon cheesecake', 5.5, 'cheesecake_yuzu.png', 20, 1),
                                                                                  (82, 'Matcha Lava Cake', 'Green tea molten center', 5.5, 'fondant_matcha.png', 20, 1),
                                                                                  (83, 'Fried Banana', 'Banana fritter, honey and sesame', 4, 'banane_frite.png', 25, 1),
                                                                                  (84, 'Coca-Cola', '33cl - Original red can', 2.5, 'coca.png', 100, 1),
                                                                                  (85, 'Coca-Cola Zero', '33cl - Sugar free', 2.5, 'coca_zero.png', 80, 1),
                                                                                  (86, 'Sprite', '33cl - Lemon lime', 2.5, 'sprite.png', 60, 1),
                                                                                  (87, 'Fanta Orange', '33cl - Orange flavor', 2.5, 'fanta.png', 60, 1),
                                                                                  (88, 'Ramune Original', 'Traditional Japanese lemonade with marble', 3.5, 'ramune_nature.png', 40, 1),
                                                                                  (89, 'Ramune Strawberry', 'Strawberry flavored Japanese lemonade', 3.5, 'ramune_fraise.png', 40, 1),
                                                                                  (90, 'Homemade Iced Tea', 'Jasmine green tea, lemon, honey', 3.5, 'icetea_maison.png', 50, 1),
                                                                                  (91, 'Evian', '50cl - Natural mineral water', 2, 'evian.png', 100, 1),
                                                                                  (92, 'San Pellegrino', '50cl - Sparkling water', 2.5, 'sanpellegrino.png', 80, 1),
                                                                                  (93, 'Asahi Beer', '33cl - Japanese Super Dry Lager', 4.5, 'asahi.png', 60, 1),
                                                                                  (94, 'Tsingtao Beer', '33cl - Chinese Lager', 4, 'tsingtao.png', 60, 1),
                                                                                  (95, 'Singha Beer', '33cl - Thai Beer', 4.5, 'singha.png', 50, 1),
                                                                                  (96, 'Sake', '180ml - Japanese rice wine (hot or cold)', 6, 'sake.png', 30, 1),
                                                                                  (97, 'Hot Green Tea', 'Sencha Teapot', 3, 'the_vert.png', 100, 1),
                                                                                  (98, 'Jasmine Tea', 'Jasmine scented teapot', 3, 'the_jasmin.png', 100, 1),
                                                                                  (99, 'Brown Sugar Bubble Tea', 'Milk tea, tapioca pearls, brown sugar', 5.5, 'bubbletea.png', 40, 1);

-- ============================================================
-- 5. LINKING DATA (UNCHANGED LOGIC)
-- ============================================================

INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 1, id FROM plat WHERE id BETWEEN 3 AND 19;
INSERT INTO categorie_plat (id_categorie, id_plat) VALUES (2, 2);
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 2, id FROM plat WHERE id BETWEEN 20 AND 35;
INSERT INTO categorie_plat (id_categorie, id_plat) VALUES (3, 1);
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 3, id FROM plat WHERE id BETWEEN 36 AND 51;
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 4, id FROM plat WHERE id BETWEEN 52 AND 67;
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 5, id FROM plat WHERE id BETWEEN 68 AND 83;
INSERT INTO categorie_plat (id_categorie, id_plat) SELECT 6, id FROM plat WHERE id BETWEEN 84 AND 99;

INSERT INTO user (nom, nbPoints) VALUES ('Test Client', 50);