# Projet EPF - Backend pour PvZ - MAGNAND Laura P2026

Ce projet implémente un backend API RESTful pour le jeu **Plants vs Zombies**. Il permet de gérer les entités `Zombies`, `Plantes` et `Maps` à travers des opérations CRUD (Create, Read, Update, Delete). Ce backend communique avec une base de données MySQL via JdbcTemplate et est construit avec **Spring MVC**.

---

## Objectif

Le but de ce projet est de créer l'API backend nécessaire pour faire fonctionner le jeu **Plants vs Zombies**. Le backend doit gérer les entités du jeu suivantes :

- **Zombies**
- **Plantes**
- **Maps**

L'application backend permet de gérer la création, la modification, la suppression et la récupération des entités via des endpoints RESTful. L'intégration avec le frontend est effectuée via des requêtes HTTP pour permettre à l'utilisateur de manipuler les données du jeu (ajouter des zombies, modifier des cartes, etc.).

---

## Technologies utilisées

- **Langage** : Java 21
- **Framework** : Spring MVC
- **Gestion des dépendances** : Maven
- **Serveur d'application** : Tomcat
- **Base de données** : MySQL
- **Outil d'accès aux données** : JdbcTemplate
- **API Servlet** : Jakarta Servlet API
- **Format de données** : JSON
- **Outils de tests** : JUnit, H2, Mockito
- **Configuration** : CORS et configuration manuelle de Spring MVC avec XML (web.xml, dispatcher-servlet.xml)
- **Spring Boot Starter Validation** : Pour la validation des DTOs avec `@Valid`


---

## Fonctionnalités implémentées

- **Gestion des entités Zombies, Plantes, et Maps** via des endpoints CRUD.
- **Vérification d'existence de `id_map`** : Avant la création ou la modification d'un **Zombie**, le backend vérifie que la **Map** associée existe dans la base de données.
- **Suppression de zombies associés à une map** : Lorsqu'une **Map** est supprimée, tous les **Zombies** associés à cette map sont également supprimés.


- **Gestion des exceptions** : Implémentation d'une gestion centralisée des exceptions pour gérer les erreurs de manière cohérente dans toute l'application.
- **Validation des données** : Utilisation des mécanismes de validation avancée de Spring pour garantir que les données envoyées sont correctes avant d'effectuer des opérations (par exemple, validation des champs des entités via des annotations comme `@NotNull`, `@Size`, etc.).

- **Architecture en couches** : Le projet suit l'architecture en couches classique avec un modèle **Controller → Service → DAO → Model**.
- **Sécurisation CORS** : Configuré pour permettre les appels API depuis le frontend React sur `http://localhost:5173`.

---

## Endpoints disponibles

### **Plantes**
- **GET /plantes** : Liste toutes les plantes
- **GET /plantes/{id}** : Récupère une plante par son ID
- **POST /plantes** : Crée une nouvelle plante
- **PUT /plantes/{id}** : Modifie une plante existante
- **DELETE /plantes/{id}** : Supprime une plante

### **Zombies**
- **GET /zombies** : Liste tous les zombies
- **GET /zombies/{id}** : Récupère un zombie par son ID
- **POST /zombies** : Crée un nouveau zombie
- **PUT /zombies/{id}** : Modifie un zombie existant
- **DELETE /zombies/{id}** : Supprime un zombie

### **Maps**
- **GET /maps** : Liste toutes les maps
- **GET /maps/{id}** : Récupère une map par son ID
- **POST /maps** : Crée une nouvelle map
- **PUT /maps/{id}** : Modifie une map existante
- **DELETE /maps/{id}** : Supprime une map (tous les zombies associés sont également supprimés)

---

## Tests

Les tests sont réalisés avec **JUnit** et **Mockito** pour tester les services et les DAO.

H2 est utilisé en mémoire pour simuler une base de données pendant les tests, permettant ainsi d'effectuer des tests sans connexion à une base MySQL réelle.

Les fichiers suivants sont utilisés pour initialiser la base H2 avec les valeurs nécessaires aux tests :

- **init.sql** : Script d'initialisation de la base de données H2.
- **values.sql** : Script pour insérer des données de test dans la base H2.

---

## Base de données

L'application utilise **MySQL** comme base de données et **JdbcTemplate** pour l'accès aux données. La configuration de l'accès à la base de données doit être définie dans le fichier `src/main/resources/database.properties`.

---

## Démarrer Tomcat

Générez le fichier `.war` avec Maven :

    mvn clean package
    
Placez le fichier `.war` dans le dossier `webapps` de Tomcat.

Démarrez Tomcat. Un dossier avec le même nom que votre `.war` sera créé dans `webapps`.
Accédez à l'application** via l'URL suivante :

    http://localhost:8080/CoursEpfBack
