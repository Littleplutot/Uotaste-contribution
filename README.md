# UOtaste – Projet SEG2505 (Livrables 1, 2 et 3)

## Groupe 01  
- David Nathan Ramona : 300256513
- Sango Monza : 300340301
- Anick  : 300404681
- Jiogo Kana Steven :300456515
**Cours :** SEG2505 – Automne 2025  
**Livrables :** 1 / 2 / 3
**Rôle principal :** Chef  

---

## État courant du projet

L’application **UOtaste** est une application Android permettant de gérer les opérations d’un restaurant selon trois rôles :  
- **Administrateur** : gestion des utilisateurs et du système.  
- **Chef** : gestion complète des recettes et des ingrédients.  
- **Serveur** : affichage et validation des commandes (prévu pour le livrable 4).  

Le projet repose sur une architecture **Android (Java)** utilisant **SQLite** pour la base de données interne, gérée par la classe `DatabaseProject.java`.

### Fonctionnalités du livrable 1
  - Création de la structure du projet Android (Activities, layouts XML, BD).  
  - Connexion simple (`LogInActivity`) permettant d’accéder selon le rôle.  
  - Gestion des utilisateurs (Admin)
  - Navigation principale : Admin / Chef / Waiter.  

### Fonctionnalités du livrable 2
  - Gestion complète du rôle **Chef** :  
  ### Gestion des recettes
  - Créer une recette (nom unique + description + image locale).
  - Modifier une recette.
  - Supprimer une recette (avec suppression des ingrédients associés).
  - Sélectionner et charger les détails d’une recette.

  ### Gestion des ingrédients
  - Créer un ingrédient (nom, unité, quantité, QR code).
  - Mettre à jour la quantité (ajout cumulatif).
  - Supprimer un ingrédient (vérification des références).
  - Lier un ingrédient à une recette (pourcentage utilisé).
  - Modifier un pourcentage.
  - Supprimer un ingrédient d'une recette.

  ### Scanner QR Code
  - Ajout d’un ingrédient via scan.  
  - Stockage du code dans SQLite.

### UML inclus
- Diagramme de classes (DatabaseProject / Activities / Associations).
- Diagrammes de séquences :
  - Ajout de recette  
  - Modification recette  
  - Suppression recette  
  - Ajout ingrédient à recette  
  - Modification ingrédient d’une recette  
  - Suppression ingrédient d’une recette    

### Fonctionnalités du livrable 3
  ### Nutrition
  - Récupération d’informations nutritionnelles d’un ingrédient :  
    - lipides  
    - glucides  
    - fibres  
    - protéines  
    - sel  
- Stockage dans la table `nutrition_info`.
- Calcul automatique du bilan nutritionnel total d’une recette.

### Tests unitaires (JUnit)
- Gestion des vendeurs.  
- Gestion des ingrédients et calcul nutritionnel.  
- Vérification cohérence :  
  - quantités après usage  
  - calculs de bilan  
  - intégrité BD.

---
---

## Processus de reconstruction

### 1️- Prérequis
- **Android Studio**  
- **SDK Android 12 (API 31)**  
- **Langage :** Java  
- **Base de données :** SQLite  

### 2️- Étapes d’installation et d’exécution
1. **Cloner le dépôt :**
   ```bash
   git clone https://github.com/uOttawa-2025-2026-seg2505-projet/Groupe-01-repo.git

   cd Groupe-01-repo

   git checkout development

   ```
2. **Ouvrir dans Android Studio** → attendre la synchronisation Gradle.  
3. **Lancer l’application** sur un appareil ou un émulateur Android.  
4. **Comptes par défaut :**
   - `admin` / `admin-pwd`  
   - `chef` / `chef-pwd`  
   - `waiter` / `waiter-pwd`  

---

## Scénario de validation
---
### Validation du livrable 1
  - Ouvrir l’application et se connecter en tant qu’**Admin**.  
  - Créer un nouvel utilisateur (Chef ou Serveur).  
  - Se connecter en tant que Chef et en tant que Serveur
  
 --- 

#### Validation du livrable 2
 ### Connexion et accès aux fonctions du Chef
   - Lancer l’application.
   - Se connecter comme **Chef**. 
   - Depuis le Chef Dashboard, accéder aux options :
     - Create Recipe
     - Update Recipe
     - Delete Recipe
     - Create Ingredient
     - Update Ingredient
     - Delete Ingredient 

 ### Gestion des Recettes
   1. Ajout d’une recette
   Objectif : créer une nouvelle recette avec un nom unique et des attributs corrects.
   - Depuis le menu Chef → cliquer sur **Create Recipe**.
   - Saisir un nom de recette (unique).
   - Ajouter une description.
   - Sélectionner une image depuis la galerie (obligatoire selon consignes).
   Résultats attendus :
   - La recette est créée dans la table recipes.
   - L’ID généré automatiquement est renvoyé.
   - L’application affiche : "Recipe created successfully!"
   - La recette apparaît dans le spinner de sélection (Update / Delete Recipe).
   - Aucun doublon n’est permis → un nom identique doit retourner un message d’erreur.
  
  2. Modification d’une recette
   Objectif : mettre à jour les informations principales d’une recette existante.
    - Depuis le menu Chef → cliquer  **Infromation**.
    - Sélectionner une recette dans le spinner.
    - Vérifier que les elements suivant s'affichent correctement :
      - le nom
      - la description
      - l’image
      - la liste des ingrédients associés
    - Modifier l’un ou plusieurs des champs (nom, texte, image).
    - Cliquer sur Save Changes.
   Résultats attendus :
    - La table recipes est mise à jour.
    - Les modifications sont visibles immédiatement dans le spinner.
    - Si l’image est remplacée → l'ancien chemin est remplacé.
    - Si le nom existe déjà → message d’erreur : "A recipe with this name already exists."
    - La liste des ingrédients reste inchangée et correctement associée.
  
  3. Suppression d’une recette
   Objectif : supprimer une recette et toutes ses associations.
    -Depuis le menu Chef → cliquer sur **Delete Recipe**.
    - Sélectionner une recette.
    - Une fenêtre de confirmation apparaît : "Are you sure you want to delete this recipe?"
    - Confirmer.
   Résultats attendus :
    - La recette est supprimée de recipes.
    - Toutes les lignes de recipe_ingredients associées sont supprimées.
    - Les quantités des ingrédients utilisés peuvent être restaurées.
    - L’application affiche : "Recipe deleted successfully."
    - Le nom de la recette n’apparaît plus dans aucun spinner.
  
 ### Gestion des Ingredients  
  1. Création d’un ingrédient
  Objectif : Enregistrer un nouvel ingrédient dans la base avec : un nom, une quantité de base, une quantité disponible, une unité, un QR Code (optionnel ou scanné).
  Depuis le menu Chef → cliquer sur **Create Ingredient**, puis saisir :
    - nom de l’ingrédient
    - quantité de base (ex. 30)
    - unité (g, L, pièces…)
    - QR code (saisi ou scanné)
  (Option) Scanner un code QR via QRScanActivity :
    - l’identifiant est automatiquement rempli
    - le Chef complète le nom + unité
    - Appuyer sur Save Ingredient. 
  Résultats attendus :
    - Une nouvelle entrée est créée dans ingredients.
    - quantity_available = quantity_base au moment de la création.
    - Le QR Code est stocké en base.
    - L’interface affiche : "Ingredient created successfully!"
    - Le spinner des ingrédients est automatiquement mis à jour.
  2. Mise à jour d’un ingrédient
   Objectif : Corriger ou augmenter la quantité d’un ingrédient existant (réassort).
   Depuis le menu Chef → cliquer sur **Update Ingredient**, 
     - Sélectionner un ingrédient dans le spinner.
     - L’interface affiche automatiquement :
       - quantité de base actuelle
       - quantité disponible actuelle
       - unité actuelle
     - Saisir une nouvelle quantité à ajouter (ex. +12).
     - (Option) Modifier l’unité.
     - Appuyer sur Update Ingredient.
  Résultats attendus :
     - quantity_base = ancienne base + quantité ajoutée
     - quantity_available = ancienne dispo + quantité ajoutée
     - L’interface affiche : "Quantity updated!"
     - Les valeurs mises à jour apparaissent immédiatement.
  3. Suppression d’un ingrédient
  Objectif : Retirer définitivement un ingrédient de la base.
  Depuis le menu Chef → cliquer sur **Delete Ingredient**, 
     - choisir un ingrédient.
     - Ses détails (nom, QR code, quantités, unité) sont affichés automatiquement.
     - Cliquer sur Delete Ingredient.
     - Confirmer la suppression dans la boîte de dialogue.
  Résultats attendus :
     - L’ingrédient est supprimé de la table ingredients.
     - Toutes les associations dans recipe_ingredients sont supprimées.
     - Le spinner est rafraîchi sans cet ingrédient.
     - Message : "Ingredient deleted successfully"
     - 
 ### Gestion des Ingredients d'une recette
  1. Modification du pourcentage d’un ingrédient  
   Objectif : Adapter la proportion d’un ingrédient déjà lié à une recette.
   Aller dans **Information**:
     - Sélectionner une recette.
     - Dans la liste des ingrédients associés, effectuer un clic simple sur un ingrédient.
     - Un menu pop-up avec 3 options apparait, cliquez sur Edit percentage.
     - Une boîte de dialogue apparaît avec le pourcentage actuel
     - donner une nouvelle valeur
     - Valider
   Résultats attendus :
     - Le pourcentage est mis à jour dans la table recipe_ingredients.
     - La quantité disponible de l’ingrédient peut être ajustée si la logique est activée.
     - L’interface se rafraîchit automatiquement.
     - l’ancien pourcentage s'affiche dans un champ donnant la possibilité d'entrer le nouveau pourcentage
     - Message affiché :"Percentage updated!" apres modification du pourcentage
  2. Suppression d’un ingrédient d’une recette
   Objectif : Retirer complètement l’association entre un ingrédient et une recette.
   Aller dans **Information**:
     - Effectuer un clic long sur un ingrédient dans la liste.
     - Une boîte de confirmation apparaît :"Remove this ingredient from the recipe?"
     - Confirmer.

   Résultats attendus:
    - La ligne correspondante est supprimée de recipe_ingredients.
    - L’ingrédient disparaît instantanément de la liste.
    - La quantité disponible peut être restaurée.
    - Message affiché: "Ingredient removed!"

---

### Validation du livrable 3
  1. Validation – Informations nutritionnelles
   Objectif: Permettre au Chef d’obtenir les informations nutritionnelles d’un ingrédient (via OpenFoodFacts), puis de les stocker et de les afficher
   Se connecter en tant que Chef:
    - cliquer sur **Information**.
    - Sélectionner une recette dans le spinner.
    - Sélectionner un ingrédient de la recette selectionnée.
    - Un menu pop-up avec 3 options apparait, cliquez sur "view Nutrition"
    - Vérifier que l’écran IngredientNutritionActivity s’ouvre, avec :
      - le nom de l’ingrédient affiché (ingredientNameTV)
      - le QR Code vide ou déjà enregistré
      - les boutons :
        - Scan QR Code
        - Fetch Nutrition Info
    - Scanner un QR Code
      - Cliquer sur Scan QR Code.
      - Scanner un code barre d’un produit alimentaire.
      - Au retour dans l’activité, vérifier que le champ QR Code affiche la valeur exacte.
    - Récupérer les informations nutritionnelles via API
      - Appuyer sur Fetch Nutrition Info.
      - Vérifier que l’application :
        - appelle l'URL : https://fr.openfoodfacts.org/produit/3504182960123/tomates-cerises-allongees-azura
        - affiche un loader (si existant)
        - parse la réponse JSON
  
  Résultats attendus:
  Si le produit existe → la liste nutritionnelle s’affiche avec les valeurs correspondantes:
    - Calories (kcal/100g)
    - Carbohydrates (g)
    - Proteins (g)
    - Fat (g)
    - Fibers (g)
    - Salt (g)
 2. Lancer le calcul du bilan nutritionnel
   Depuis le **Dashboard** de "Chef":
    - l'utilisateur clique sur **information** dans Recipe Management
    - il est conduit vers l'interface update recipe qui contient le bouton Information Nutriotionnelle.
    - si l'utilisateur choisit une recette , appui sur le bouton Information Nutrionnelle. il ouvre RecipeNutritionInfoActivity.java
    - si l'utilsateur ne choisit pas de recette en cliquant sur le bouton Information Nutionnelle un message " lui disant que aucune recette n'a été selectionnée apparaitre"
    - Affchage de l'interface d'information Nutrionnelle:
      Grace a la methode On create :
        - l'interface s'affiche avec une fenetre qui contient un tableau.
        - effectue les calculs nutritionnelle en fonction des ingredients de la recette.
        - puis affiche un tableau contenant le bilan nutritionnelle.

---

### **tests unitaires JUnit** :
  Dans le cadre du livrable 3, une série complète de tests unitaires a été développée afin de garantir la fiabilité du système, notamment pour :
   - la gestion des utilisateurs (en particulier les vendeurs/serveurs):
     - Création d’utilisateurs;
     - Connexion / Authentification
     - Mise à jour d’un compte
     - Suppression d’un utilisateur
     - Gestion du rôle « Waiter »
   - la gestion des ingrédients:
     - Création / Validation
     - Suppression / Modification
     - Lien avec les recettes
   - la logique de calcul nutritionnel
   - Tests fonctionnels (Express Tests):
     - buttonLogoutIsVisible
     - buttonLogoutIsClickable
     - createIngredientButton
     - modifyIngredientButton
     - deleteIngredientButton
Les tests ont été codés en Java, et exécutés via Android Studio.
Tous les tests passent avec succès (statut : PASS) et ont ete reperoriés dans une fiche au format PDF qui fait partie de la documentation soumise
 
---
### Validation du livrable 4
####
   - 	Le serveur appuie sur l’option “Recipes & Nutrition” depuis son espace.
     - 	Résultat attendu :
     l’application affiche la liste complète des recettes sous forme de CardViews, avec :
     nom de la recette ;
     calories formatées ;
     icône alimentaire ;
     éventuellement une courte description.
   - tri des recettes
     - Tri par Nom
        La liste se réorganise en ordre alphabétique (A → Z).
        La mise à jour est instantanée et correcte.
        
     - Tri par Calories
        Les recettes sont classées de la moins calorique à la plus calorique.
        Les valeurs de calories s’affichent bien à 3 décimales (formatage appliqué).
   - Clic sur une recette
     - 	Le serveur sélectionne une recette dans la liste.
     - Résultat attendu :
        Une fenêtre modale (AlertDialog) s’ouvre affichant :
          Nom de la recette
          Calories
          Carbohydrates
          Proteins
          Fats
          Fibers
        Les informations correspondent exactement à la recette cliquée,
        sans décalage d’index (le problème corrigé dans ton code).
#### VALIDATION - SALES BILLING REPORT (WAITER)
Objectif : Vérifier le bon fonctionnement du module de rapport des ventes permettant au serveur de consulter les statistiques de ventes par recette, incluant les notes moyennes et les commentaires clients.
- Connexion se connecter en tant que Waiter.
- Enregistrement des ventes (prérequis)
Depuis le tableau de bord, cliquer sur "Record Sale"
  - Vérifier l'écran RecordSaleActivity avec :

  - Spinner de sélection de recette

  - RatingBar (0-5 étoiles)

  - Champ texte pour commentaire (optionnel)

  - Bouton "Record Sale"
- Accès au rapport Retour au tableau de bord
  - Cliquer sur "View Sales Billing"

  - Vérifier l'écran SalesBillingActivity avec :

  - Titre "Sales Billing Report"

  - Bouton retour (flèche)

  - Deux cartes de statistiques globales

  - Liste (RecyclerView) des recettes

- Statistiques globales Carte 1 - Total Sales :
  - Attendu : "Total Sales: 4"

  - Correspond au total des ventes enregistrées

  - Carte 2 - Average Rating :

  - Attendu : "Average Rating: 4.0"

  - Calcul : (4 + 3 + 5 + 4) / 4 = 4.0
  - Vérifier le style des cartes (fond blanc, texte centré, ombre)
-  Liste des recettes Éléments à vérifier par recette :
   - Image de la recette (si disponible)
   - Nom de la recette
   - Nombre de ventes
   - Note moyenne
   - Section "Comments:" avec liste des commentaires
   - Ordre de tri attendu :
   - Pasta Carbonara (2 ventes)
   - Tomato Soup (1 vente)
   - Caesar Salad (1 vente)
   - Vérifier le style des cartes (fond blanc, texte centré, ombre)
 - Détail Pasta Carbonara Ventes : "2 sales"
   - Note moyenne : "3.5 / 5.0" (calcul : (4+3)/2)
   - Commentaires :
   - ⁠Très bon plat
   - ⁠Bon mais un peu salé
 - Recette sans commentaire (Caesar Salad)
   - Ventes : "1 sale"
   - Note moyenne : "4.0 / 5.0"
   - Commentaires : "No comments yet"
 - Cas "Aucune vente"Réinitialiser la base de données via Administrator
   - Reconnecter en tant que Waiter
   - Vérifier l'affichage :
   - Total Sales: 0
   - Average Rating: N/A
   - Message : "No sales data available. Please record some sales first."
   - RecyclerView masquée
 - Gestion des images
   - Cas 1 : Recette avec image valide → Image affichée
   - Cas 2 : Recette sans image → ImageView masquée
   - Cas 3 : Image URI invalide → Application ne crash pas, log d'erreur visible

#### Rédaction du retour d'expérience(organisation , difficulté, solutions)
  - catégorie Organisation :
      Pour le livrable 1 nous nous sommes concertés et afin de completer les fonctionnalités requises, nous avons subdiviser le travail en considerant juste les potentielles forces de chacun, les taches delegués étaient peu nombreuses  mais en termes de la charge qu'incombait chacune d'elles nous avons omis d'analyser leur profondeur de fond en comble de sorte à subdiviser un travail equitable pour l'ensemble des membres du groupes. 

     la force de l'équipe était la communication malgrès notre organisation qui nous portait prejudice , nos faibles connaissances dans l'utilisation d'android studio, les interfaces utilisateurs , l'ecriture du code en xml. nous avons su rester à l'ecoute des suggestions et des propositions de chacun afin de ne pas etre submerger par la quantité de travail . 

     Nos difficultés durant ce livrable était que la repartition des tâches était desequilibrer certains avaient des taches plus lourdes en termes de charge de travaillent que d'autres car ils devraient faire l'interface utilisateur et aussi la logique java et implemnater la logique des vues en java . 

     En sommes en depit de touts nos tumultes nous pouvons souligne que savoir faire preuve de communication est une force qui meme lorsque l'equipe manque de compétences elle nous permet de surpasser les difficultés.

    ----
     Pour le livrable 2 afin de palier à notre problème d'organisation nous avons recourus à l'ia pour nous permettre de subdiviser equitablement nos tâches, durant ce livrable nous avions mal gerer le facteurs temps et encore nous avons sous estimer le quantité de travail lier à chacune de nos taches meme en ayant recours à un outils d'intelligence artificielle pour la subdivision de celles ci.

     Dans ce livrable aucun point positif n'est a dénotté nous avons connu des problèmes dans lors de la soumission github, en supprimant dans un premiers temps notre travail , nous avons remarquer qu'en essayant de push sur la meme branche nous rencontrions une erreur de sorte aucun de nous ne pouvais push au meme moment , de plus faute d'organisation nous nous sommes retrouvés sous pressions et avons recouru a des outils intelligence artificielles pour au moins soumettre le livrable. de plus notre correcteur nous avez reprocher le manque de quelque fichier qui aideraient à la soumission que nous avons oublier de soumettre . 
     ---
     pour le livrable 3 nous avons corriger l'organisation nous avons utiliser une ia pour repartir nos taches , chacun d'entre ayant tirê des lessons de 

     1. Livrable 1

          Organisation : L'equipe s'est réunie et à eu des réunion hebdomadaire le temps de la durée du livrable 1 , nous avons tous consulter les prerequis du livrable1 et nous nous sommes departager les tahces. nous avions eu à nous rencontrer sur teams , et quelque fois pendant les heures de cours pour pouvoir 

          Difficultés: pas de difficutlés majeur rencontrer juste pour se repartir équitablement les tâches.

          Solutions / leçons: la communication et la prévision nous ont montré être des atouts dont notre equipe disposer pour rendre le livrable sans soucis.

      2. Livrable 2

          Organisation: nous avons rencontré certaines difficultés, notamment dans la répartition des tâches. Certains segments du travail se sont révélés plus lourds ou plus complexes que prévu, ce qui a entraîné une charge de travail inégale parmi les membres de l’équipe. Cette expérience nous a poussés à revoir notre méthode d’organisation.

          Difficultés: manuqe d'organisation flagrant, un manque de communication, de precision quant aux tâches, beaucoup d'ambigueté dans la comprehension du cahier de charge, incomprehension dans l'utilisation du github et probleme de commit . 

          Solutions / leçons: Nathan a su démontrer des compétences dans le management du git , et l'organisation des repos . La réactivité dont il a fait preuve en moment de stress , nous a appris qu'il ne faut pas paniquer lorsque rien ne va.

      3. Livrable 3 

          Organisation: nous avons intégré l’utilisation de l’intelligence artificielle comme outil d’aide à la planification. L’IA nous a permis d’obtenir des suggestions de répartition des tâches plus équilibrées, claires et adaptées à la nature des activités de développement. Grâce à cette approche, nous avons réussi à améliorer:
          •	l’équité dans la distribution du travail ;
          •	la clarté des responsabilités ;
          •	la vitesse d’exécution ;
          •	la qualité générale du livrable.

          Difficultés: aucune difficultés à souligner.

          Solutions / leçons: nous avons appris des erreurs des précédents livrables et nous nous sommes attelés à la tâche. nous avons compris qu'un echec n'est pas une fatalité mais un moyen efficace d'apprendre.

      4. Livrable 4

          Organisation: nous avons appris de nos erreurs et l'organisation se fait de miuex en mieux.

          Difficultés: le debbugage du code en générale nous a pris beaucoup de temps. 

          Solutions / leçons: ne jamais abandonné


#### tableau et résumé des contibutions 


   1.  Monza

|       Description        |     Controlleur de vue      |     Logique Java      |  Developement Interface Utilisateur  |             Base de Données              |
| :----------------------: | :-------------------------: | :-------------------: | :----------------------------------: | :--------------------------------------: |
|      Administrator       |        LogInActivity        |    SessionManager     |         activity_adminboard          |               partie User                |
|                          |        AdminActivity        |     Administrator     |         activity_create_user         |  recuperer l'information nutritionnelle  |
|                          |   UserManagementActivity    |         Chef          |        activity_update_recipe        |                                          |
|                          |     CreateUserActivity      |  NutritionCalculator  |        activity_loginterface         |                                          |
|                          |    ResetPasswordActivity    |                       |         activity_modify_user         |                                          |
|                          |     ModifyUserActivity      |         User          |        activity_resetpassword        |                                          |
|                          | RecipeNutritionInfoActivity |        Waiter         |       activity_usermanagement        |                                          |
|                          |       WaiterActivity        |                       |          dialog_edit_admin           |                                          |
|                          |        ChefActivity         |                       |           dialog_edit_user           |                                          |
|                          |                             |                       |              item_user               |                                          |
|                          |                             |                       |                                      |                                          |
| ------------------------ |  ------------------------   | --------------------- | ------------------------------------ | ---------------------------------------- |
|                          |                             |                       |                                      |                                          |
|           Chef           |                             |     NutritionInfo     |                                      |    activity_information_nutritionelle    |
|                          |                             |                       |                                      |                                          |
| ------------------------ |  ------------------------   | --------------------- | ------------------------------------ | ---------------------------------------- |




précision: par soucis de transparence les classes :item_user , dialog_edit_user, dialog_edit_admin , activity_resetpassword, activity_modify_user, activity_create_user
SessionManager, User, LogInActivity(version base de donnees), AdminActivity, UserManagementActivity, CreateUserActivity, ResetPasswordActivity, ModifyUserActivity. 

les methodes de la base donnée: 

Sont des classes pour lesquelles Monza reconnaît avoir utiliser l'ia .

##### Livrable 1 - Connection Utilisateurs

la responsabilité était de faire le login de l'administrateur , du Chef, et du waiter afin de les permettres de se connecter avec leurs informations d'identifiants. 
l' interface de connexion et la page d'acceuille de l'administrateur . Aussi la creation de l'interface de connexion pour tout les utilisateurs et une connexion entre l'interfaces de connexions et les differentes page d'acceuilles des differents utilisateurs. de plus la déconnexion de chacun des utilisateurs predifini: admin, chef, waiter, waiter.

##### Livrable 2 - AdminActivity 
la responsabilité était de dynamiser la connexion des differents utilisateurs en recupérant leurs informations à partir de la base de données ensuite. il a fallu s'occuper de l'administrateur en implememtant ses fonctionnalités, les modifications d'utilisateurs, la creations d'utilisateurs, la reinitialisations du mot de passe de l'admin, la mise a jour de son profile. la création d'utilisateur inclus tout les rôles l'admin seul a le droit de création et il ne peut que créer des admins , des chefs, des waiters, avec leurs informations nom, prenom, email. l'administrateur peut consulter la liste d'utilisateur et vérifier ceux qui sont affectifs ou pas et peux les modifiers en effacants certains ou en modifiant les données d'autres. ensuite l'administrateur peut reinitialiser la base de données .

##### Livrable 3 - NutritionCalculator
pour le livrable trois il a fallu calculer et afficher le bilannutritionnelle d'une recette. la premiere etapes consister à comprendre la relation entre les classes Recipe, RecipeIngredient, et NutrintionInfo . la deuxime etape était de creer depuis la base de donnée une methode qui elle recupere la liste des recettes selon leurs ID . Monza s'est occupé de recuperer l'id connecter à  UpdateRecipeActivity pour l'utiliser et afficher directement l'information nurtitionnelle de la recette en consulatant juste l'informations de celle-ci sans calcul explicite. Monza se chargea d'implementer la logique de calcul et les methodes qui associent les informations nutritionnellles aux ingredients et connecter ses memes ingredients à la recette afin de calculer le bilan total de la recette et l'afficher implicitement.

##### Livrable 4 - Redaction de retour d'experience
pour le livrable 4 Monza s'est Chargé faire la documentation en ce qui concerne la redaction du retour d'experience, les situations rencontraient , les challenges, les defis surmonter , les succès , les échecs , tout ceux parquoi le groupent est passé et ce que le groupe a tireé comme lesson.



  2. Anick 

|     Description      |     Controlleur de vue     |        Logique Java         |   Developement Interface Utilisateur   |        Base de Données        |
| :------------------: | :------------------------: | :-------------------------: | :------------------------------------: | :---------------------------: |
|         chef         |       Chef activity        |        TestExpresso         |          Activity chef board           |                               |
|                      |                            |   Manage Ingredients test   |         Activity Create recipe         |                               |
|                      |                            |     Manage Ingredients      |                                        |                               |
|                      |                            |                             |                                        |                               |
|                      |   Create recipe Activity   |                             |                                        |        Databaseproject        |
|                      |                            |                             |                                        |                               |
|                      |   Delete recipe Activity   |                             |                                        |                               |
|                      |                            |                             |                                        |                               |
|                      |    Record sale Activity    |                             |                                        |                               |
|                      |      Waiter Activity       |                             |                                        |                               |
|                      |   Update recipe Activity   |                             |                                        |                               |
| -------------------- | -------------------------- | --------------------------- | -------------------------------------- | ----------------------------- |
|                      |                            |                             |                                        |                               |
|                      |                            |     Manage waiter test      |          Activity record sale          |                               |
|        waiter        |                            |        Manage waiter        |         Activity waiter board          |                               |
|                      |                            |                             |                                        |                               |
|                      |                            |                             |                                        |                               |
| -------------------- | -------------------------- | --------------------------- | -------------------------------------- | ----------------------------- |


##### Livrable 1 - MODÉLISATION UML ET INTERFACES INITIALES
Pour le premier livrable, un double rôle a été assuré : contributeur technique et Scrum Master. La participation a porté sur la mise en place du projet, la structuration de l'équipe et la production des premiers artefacts nécessaires à la compréhension et au démarrage du développement.

En tant que Scrum Master, la répartition des tâches a été coordonnée, des rencontres fréquentes ont été organisées pour suivre la progression de l’équipe et la clarté des priorités a été maintenue. La structure Git a également été mise en place (branches individuelles, merges propres, respect de la convention de commit).

Sur le plan technique, une contribution a été apportée à la création des premières interfaces graphiques, essentielles pour amorcer la navigation et définir l’identité visuelle de l’application. Le diagramme d’activité principal, reflétant le fonctionnement général de l’application et les interactions entre les différents rôles, a également été produit.

Création du diagramme d’activité principal décrivant l’authentification et le parcours utilisateur.
Développement des premières interfaces XML : écran d’accueil du chef, de l’administrateur et du vendeur.
Soutien aux membres pour leurs tâches techniques et clarification du fonctionnement attendu.

##### Livrable 2 - CONCEPTION ET IMPLÉMENTATION DES INTERFACES DU CHEF 
Pour ce livrable, la contribution s’est concentrée sur l’aspect visuel et ergonomique des fonctionnalités du rôle Chef. La conception et le développement des interfaces nécessaires à la création et à la gestion des recettes et des ingrédients ont été réalisés.​

Le travail incluait la construction des vues XML, la définition des composants visuels, la gestion des champs obligatoires et la préparation des interactions (boutons, formulaires, validations). L’architecture visuelle mise en place a permis de faciliter l’intégration du scan QR, l’ajout des ingrédients et la modification des recettes dans les livrables ultérieurs.​

Interface complète du Chef, incluant le tableau de bord et les accès aux fonctionnalités principales.
Écran d’ajout de recette : zones de texte, sélection d’image, description, mise en page harmonisée.
Écran d’ajout d’ingrédient : champ de pourcentage, bouton de scan QR, messages d’erreur.
Validation visuelle des champs avec rétroaction claire (toasts, erreurs affichées). 

##### Livrable 3 - TESTS UNITAIRES ET TESTS UI (EXPRESSO)
Dans le troisième livrable, la responsabilité portait sur l’ensemble de la mise en place et l’écriture des tests. Des tests unitaires JUnit ont été réalisés pour vérifier la logique métier, ainsi que des tests d’interface Expresso pour valider le comportement visuel et interactif de l’application.​

Les tests développés ont permis d’assurer la stabilité des fonctionnalités existantes, de détecter des erreurs potentielles et de garantir la cohérence du travail entre les membres. Ce travail a également servi à valider le comportement réel des écrans réalisés au livrable précédent.​

Tests JUnit pour la gestion des vendeurs et des opérations sur les ingrédients.
Création de tests Expresso : navigation, validation des champs, affichage des messages d’erreur.
Mise en place d’un environnement de tests structuré pour éviter les régressions.
Validation systématique des écrans développés dans le livrable 2.

##### Livrable 4 - IMPLÉMENTATION COMPLÈTE DE L’ENREGISTREMENT D’UNE VENTE
Dans ce livrable, la tâche principale confiée concernait l’implémentation du système d’enregistrement des ventes pour le rôle Vendeur. L’interface a été conçue, la logique d’interaction gérée, les champs validés et l’ensemble connecté à la base de données afin que chaque vente puisse être enregistrée correctement.​

Cette fonctionnalité est essentielle dans le flux de travail du serveur, car elle permet de sélectionner une recette vendue, d’attribuer une note et d’écrire une appréciation. Le processus a été conçu pour rester simple, intuitif et robuste, avec des messages clairs en cas d’erreurs ou d’oubli de champs requis.​

Développement de l’écran d’enregistrement : sélection de recette, étoiles, commentaire.
Gestion des événements de clic (validation, sélection, focus).
Validation des champs (note obligatoire, recette obligatoire).
Messages d'erreur et de succès clairs et visuels.
Connexion complète à la base avec contrôles d’intégrité.
Tests manuels complets du flux pour garantir une expérience fluide.

  3. Nathan


|     Description      |      Controlleur de vue      |         Logique Java          |    Developement Interface Utilisateur    |         Base de Données          |
| :------------------: | :--------------------------: | :---------------------------: | :--------------------------------------: | :------------------------------: |
|         chef         | IngredientNutritionActivity  |                               |                                          |                                  |
|                      |                              |                               |                                          |                                  |
|                      |                              |                               |          activity_waiter_recipe          |                                  |
|                      |                              |                               |            item_recipe_sales             |                                  |
|                      |                              |                               |          activity_sale_billing           |         Databaseproject          |
|                      |                              |                               |                                          |                                  |
|                      |                              |                               |                                          |                                  |
|                      |                              |                               |                                          |                                  |
|                      |                              |                               |                                          |                                  |
|                      |                              |                               |                                          |                                  |
|                      |                              |                               |                                          |                                  |
| -------------------- | ---------------------------- | ----------------------------- | ---------------------------------------- | -------------------------------  |
|                      |                              |     SalesBillingActivity      |                                          |                                  |
|                      |                              |        RecipeSalesData        |                                          |                                  |
|        waiter        |                              |      RecipeSalesAdapter       |                                          |                                  |
|                      |                              |                               |                                          |                                  |
|                      |                              |                               |                                          |                                  |
| -------------------- | ---------------------------- | ----------------------------- | ---------------------------------------- | -------------------------------- |
   

##### Livrable 1 - Documentation
Pour le premier livrable, la responsabilité a porté sur l’ensemble de la documentation du projet. Un fichier README.md complet a été rédigé afin d’expliquer l’architecture de l’application UTaste, de documenter les trois rôles utilisateurs (Administrator, Chef, Waiter), de proposer un guide d’utilisation détaillant la navigation dans l’application et de lister les comptes par défaut avec leurs identifiants. Des instructions d’installation et de déploiement du projet ont également été rédigées.

##### Livrable 2 - CRÉATION COMPLÈTE DE LA BASE DE DONNÉES 
Pour le deuxième livrable, la conception et l’implémentation de l’intégralité de la base de données SQLite de l’application UOTaste dans le fichier DatabaseProject.java ont été assurées. Toutes les tables nécessaires ont été créées : users, recipes, ingredients, recipe_ingredients, sales et nutrition_info, chacune avec ses colonnes, contraintes d’unicité, clés étrangères, règles CHECK et ON DELETE CASCADE pour garantir l’intégrité des données.

La base de données a été configurée avec le nom UOtaste.db, avec l’implémentation de onCreate() pour les requêtes de création, de onUpgrade() pour la mise à jour du schéma, et l’activation des clés étrangères via PRAGMA foreign_keys = ON. Des contraintes UNIQUE ont été appliquées sur le nom d’utilisateur, l’email, le nom de recette et le code QR, ainsi que des contraintes CHECK sur le pourcentage (0–100) et la note (0–5) afin de sécuriser les valeurs stockées.

De nombreuses méthodes CRUD ont été développées pour chaque entité :

Pour les utilisateurs : createUser(), getAllUsers(), getUser(), updateUser(), deleteUser(), updatePassword(), resetUserPassword(), usernameExists(), emailExists(), resetDatabase().

Pour les recettes : addRecipe(), updateRecipe(), deleteRecipeAndRestoreStock(), recipeExists(), getAllRecipeNames(), getRecipeDetailsByName(), getRecipeById().

Pour les ingrédients : getAllIngredients(), getIngredientsByRecipeId(), getIngredientIdByName(), getIngredientByName(), addIngredientToRecipe(), deleteIngredientFromRecipe(), updateIngredientPercentage(), updateIngredientQuantity(), getIngredientsWithPercentages().

Pour les autres tables : addNutritionInfo(), updateNutritionInfo(), addSale().

Deux classes internes ont été définies pour structurer les données : RecipeDetails (id, name, description, imagePath, createdBy) et RecipeIngredientInfo (name, percentage). Enfin, des méthodes d’initialisation comme insertDefaultUsers() et insertDefaultIngredients() ont été mises en place pour insérer des utilisateurs par défaut (admin, chef, waiter, waiter2) ainsi que cinq ingrédients avec QR codes valides (Tomate, Fromage, Lait, Farine, Œufs).


##### Livrable 3 - SCAN QR CODE ET API OPENFOODFACTS
Pour le troisième livrable, la mise en place du flux complet de scan de code-barres, de récupération des informations nutritionnelles via l’API OpenFoodFacts et d’enregistrement dans la base de données a été assurée.
Le fichier IngredientNutritionActivity.java gère l’ensemble du processus : startBarcodeScanner() lance le scanner ZXing, onActivityResult() récupère le code-barres, fetchNutritionFromAPI() réalise l’appel HTTP GET, parseNutritionData() extrait les valeurs nutritionnelles depuis le JSON, displayNutritionInfo() les affiche et saveNutritionToDB() les enregistre.

L’extension de la base de données a été réalisée avec la table nutrition_info (colonnes nutrition_id, ingredient_id unique et clé étrangère, ainsi que les six valeurs nutritionnelles en REAL).
Des méthodes comme addNutritionInfo() et updateNutritionInfo() gèrent l’insertion et la mise à jour, tandis que insertDefaultIngredients() ajoute cinq ingrédients de test avec codes QR valides (Tomate, Fromage, Lait, Farine, Œufs).

Une gestion complète des erreurs a été implémentée à l’aide de blocs try-catch pour les exceptions réseau (IOException), les erreurs de parsing JSON (JSONException) et la vérification du code HTTP 200 afin de détecter les codes-barres inexistants.


##### Livrable 4 - Bilan des ventes
Pour ce quatrième livrable, la fonctionnalité complète d’affichage du bilan des ventes pour le rôle Waiter a été implémentée, afin de visualiser les recettes vendues avec leurs statistiques.

Le fichier RecipeSalesData.java définit une classe POJO immuable contenant recipeId, recipeName, salesCount, averageRating et imagePath, avec uniquement des getters pour garantir l’intégrité des données.

Dans DatabaseProject.java, la méthode getRecipeSalesStatistics() exécute une requête SQL d’agrégation utilisant un LEFT JOIN entre recipes et sales, les fonctions COUNT() et AVG(), COALESCE() pour remplacer les valeurs NULL par 0, ainsi que GROUP BY, HAVING et ORDER BY pour regrouper les résultats par recette, filtrer et trier par nombre de ventes décroissant. La méthode parcourt ensuite le Cursor et construit une liste d’objets RecipeSalesData.

Le fichier RecipeSalesAdapter.java contient un adapter RecyclerView personnalisé gérant l’affichage de la liste : onCreateViewHolder() gonfle le layout item_recipe_sales.xml, onBindViewHolder() appelle bind() sur le ViewHolder, et updateList() permet de rafraîchir les données. La classe interne SalesViewHolder référence les vues (image, nom, nombre de ventes, note) et la méthode bind() formate l’affichage en « X sales » et « X.X / 5.0 » ou « No ratings yet » si la note est égale à 0, avec un try-catch pour la gestion des images.

Le fichier SalesBillingActivity.java orchestre l’écran principal : initViews() initialise les vues, loadSalesData() récupère les données, calcule les statistiques globales (total des ventes, moyenne générale des notes), affiche « No sales recorded yet » si la liste est vide, et setupRecyclerView() configure le RecyclerView avec un LinearLayoutManager et l’adapter.

Des modifications ont été apportées à WaiterActivity.java avec l’ajout du bouton btnSalesBilling, son initialisation dans onCreate(), et la méthode openSalesBilling() qui lance SalesBillingActivity via un Intent. Les layouts activity_sales_billing.xml et item_recipe_sales.xml ont été créés (structure, couleurs, TextViews et ImageView), le bouton « View Sales Billing » a été ajouté dans activity_waiterboard.xml, et SalesBillingActivity a été déclarée dans AndroidManifest.xml avec exported="false" et parentActivityName="Activities.WaiterActivity".


4. Steven

|     Description      |      Controlleur de vue       |         Logique Java          |    Developement Interface Utilisateur    |         Base de Données          |
| :------------------: | :---------------------------: | :---------------------------: | :--------------------------------------: | :------------------------------: |
|         chef         |         ChefActivity          |                               |                                          |                                  |
|                      |   CreateIngredientActivity    |                               |             activity_qrscan              |                                  |
|                      |     CreateRecipeActivity      |                               |            activity_chefboard            |                                  |
|                      |     UpdateRecipeActivity      |                               |           activity_waiterboard           |                                  |
|                      |     DeleteRecipeActivity      |                               |        activity_createingredient         |         Databaseproject          |
|                      | AddIngredientToRecipeActivity |                               |        activity_update_ingredient        |                                  |
|                      |        QRScanActivity         |                               |        activity_delete_ingredient        |                                  |
|                      |        WaiterActivity         |                               |          activity_createrecipe           |                                  |
|                      |     WaiterRecipesActivity     |                               |          activity_updaterecipe           |                                  |
|                      |                               |                               |    activity_add_ingredient_to_recipe     |                                  |
|                      |                               |                               |                                          |                                  |
| -------------------- | ----------------------------  | ----------------------------- | ---------------------------------------- | -------------------------------  |
|                      |                               |                               |                                          |                                  |
|                      |                               |                               |                                          |                                  |
|        waiter        |                               |                               |                                          |                                  |
|                      |                               |                               |                                          |                                  |
|                      |                               |                               |                                          |                                  |
| -------------------- | ----------------------------  | ----------------------------- | ---------------------------------------- | -------------------------------- |

##### Livrable 1
Pour le premier livrable, le travail a porté sur la conception complète du système.
Les principales activités réalisées sont :

Élaboration du diagramme de classes principal du projet.

Modélisation des entités fondamentales : User, Administrator, Chef, Waiter, Recipe, Ingredient, RecipeIngredient, Sales, NutritionInfo.

Définition des associations, des cardinalités, des attributs et des relations entre ces entités.

Construction d’une base solide pour la mise en œuvre des fonctionnalités ultérieures.

Uniformisation du vocabulaire conceptuel et alignement avec la future structure des tables SQLite.

Le livrable 1 correspond ainsi à la contribution à la conception UML globale du projet.

##### Livrable 2  

Durant le deuxième livrable, tout le travail d’implémentation lié au rôle Chef a porté sur plusieurs modules critiques de l’application. La logique métier complète du Chef a ainsi été mise en place.

Gestion des recettes : création de recettes avec nom unique, description et image obligatoire, modification complète, suppression avec nettoyage automatique des données liées, et affichage dynamique des détails (nom, description, image, ingrédients associés).

Gestion des ingrédients : création d’ingrédients (nom, unité, quantité de base, quantité disponible, QR code), mise à jour cumulative des quantités pour le réassort, et suppression sécurisée avec vérification des références en base.

Association Recette ↔ Ingrédient : ajout d’ingrédients à une recette via la table recipe_ingredients, saisie et mise à jour du pourcentage d’utilisation, suppression d’un ingrédient par clic long, et ajustement automatique des quantités disponibles lorsque cette option est activée.

Intégration du QR Code : scan via la caméra avec QRScanActivity, récupération automatique du code, stockage dans SQLite et préremplissage du champ lors de la création d’un ingrédient.

Améliorations générales : mise à jour du manifeste Android (permissions et activités), correction de bugs et de crashs liés à la navigation et aux intents, rafraîchissement automatique des spinners et des listes, et gestion complète du cycle de vie des activités du rôle Chef.

Was this answer helpful?
Help us improve by giving some quick feedback.
Related

Comment tester automatiquement les flux CRUD du rôle Chef

Quels tests unitaires écrire pour la gestion des ingrédients

Comment gérer les migrations SQLite sans perdre les QR codes

Comment synchroniser les quantités d ingrédients avec stock central

Stratégies pour optimiser les performances d affichage des recettes


##### Livrable 3
Pour le troisième livrable, la responsabilité a porté sur la documentation complète du projet ainsi que sur la mise à jour et la finalisation du modèle conceptuel.

Rédaction du README professionnel : description de l’état du projet, procédures d’installation, de compilation et d’exécution, scénarios de validation détaillés, et indications permettant à l’enseignant de cloner et tester facilement l’application.

Mise à jour des diagrammes UML : création ou actualisation des diagrammes de séquence (ajout, modification, suppression de recette, gestion des ingrédients) et mise à jour du diagramme de classes pour intégrer les informations nutritionnelles, la table nutrition_info, les activités supplémentaires du Chef et les nouvelles relations entre recettes, ingrédients et nutrition.

Organisation documentaire : réorganisation du dossier /doc/UML, nettoyage des fichiers .puml, harmonisation des représentations UML et participation à la préparation de la vidéo de démonstration.

Le livrable 3 correspond donc à la contribution principale à la documentation, au README et à l’évolution du modèle conceptuel du projet.

##### Livrable 4

Création du boutton "Recipe & Nutrition" dans l'intreface d'accueil du Waiter. creation du fichier XML servant a présenter l'ensemble des recettes disponibles dans la base de donnee annexe de leur bilan calorifique respectivement. creation du la classe activity WaiterRecipe pour la gestion du backend dudit fichier XML.

   #### Estimation du temps passé par livrable( livrable 1, et 2, 3,4 )
   - Nathan : 
     -  livrable 1: 1 semaine
     -  livrable 2: 2 semaines 
     -  livrable 3: 2 semaines 
     -  livrable 4: 1 semaine

   Anick : 
     -  livrable 1: 1 semaine 
     -  livrable 2: 2 semaines
     -  livrable 3: 1 semaine et demi
     -  livrable 4: 3 jours 

   Steven : 
    -  livrable 1: 2 semaines 
    -  livrable 2: 2 semaines 
    -  livrable 3: 1 semaine 
    -  livrable 4: 1jour

   Monza : 
   - livrable 1: 5 jours 
   - livrable 2: 1 semaine
   - livrable 3: 1 semaine
   - livrable 4: 1 jour

---

## Gestion de version

- Branche principale : `Developement`  
- Historique complet des livrables 1 → 4.  
- Diagrammes UML et fichiers de test inclus dans le dossier `/doc`.  

---

## Auteur
**Monza Sango**  
Étudiant en science de l'informatique – Université d’Ottawa  

---

### Conformité à la consigne
> *Le fichier README est présent et est suffisamment consistant pour décrire l’état courant du projet, son processus de reconstruction, son scénario de validation et ses éventuelles limites.*
