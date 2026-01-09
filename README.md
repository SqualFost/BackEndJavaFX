# 🍜 Click & Wok - Backend API

L'API REST officielle pour l'application borne de commande **Click & Wok**. Elle gère la persistance des données (plats, catégories, commandes) et la communication avec la base de données MySQL.

## ✨ Fonctionnalités principales

* **API RESTful :** Architecture légère et performante basée sur le framework **Javalin**.
* **Gestion du Menu :** Endpoints pour récupérer les plats, catégories et gérer les stocks.
* **Système de Commande :** Création, suivi et historisation des tickets de commande.
* **Base de Données :** Intégration MySQL avec gestionnaire de connexion robuste (Singleton).
* **Compatibilité Docker :** Configuration prête pour un déploiement conteneurisé.

## ⚙️ Prérequis

* **Java JDK 21+** (Recommandé).
* **Maven 3.8+**.
* **MySQL 8.0+** : La base de données doit être accessible.

## 🚀 Installation et Lancement

### 1. Configuration de la Base de Données
Avant de lancer l'API, assurez-vous que votre base de données est prête.
* Exécutez le script SQL fourni dans `BDD/creation_bdd.sql` pour créer les tables et insérer les données initiales.
* **Config par défaut :**
    * Host: `localhost`
    * Port: `3306`
    * User: `root`
    * Password: *(vide)*
    * Database: `click_n_wok`

### 2. Démarrage (Local)
Utilisez Maven pour compiler et lancer le serveur :

```bash
mvn clean compile exec:java
```

Le serveur démarrera sur http://localhost:7001 (Port configuré par défaut).

### 3. Démarrage (Docker)
Un fichier docker-compose.yml est inclus pour orchestrer l'API et la base de données :

```bash
docker-compose up --build
```

## 🔌 Documentation API (Endpoints)

L'API expose les ressources suivantes via des contrôleurs dédiés :

| Méthode | Endpoint | Description |
| :--- | :--- | :--- |
| **GET** | `/plats` | Liste de tous les plats disponibles. |
| **PUT** | `/plats/{id}` | Mise à jour (ex: stock) d'un plat. |
| **GET** | `/categories` | Liste des catégories (Entrées, Sushis...). |
| **GET** | `/categorie-plats` | Table de liaison Plat <-> Catégorie. |
| **POST** | `/commandes` | Création d'une nouvelle commande. |
| **GET** | `/commandes` | Récupération de l'historique (pour l'écran Cuisine). |

## 📂 Structure du projet
* src/main/java/fr/univcours/api/

* Main.java : Point d'entrée, configuration du serveur Javalin et CORS.

* Controller/ : Gestion des requêtes HTTP (Routing).

* Database/ : Connexion JDBC et gestion des transactions.

* ServicesImpl/ : Logique métier (Business Logic).

* Models/ : POJOs représentant les tables BDD.

## 🛠️ Stack Technique
* Langage : Java

* Serveur Web : Javalin (Micro-framework)

* Base de données : MySQL

* Build Tool : Maven
