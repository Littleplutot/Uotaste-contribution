# 🍽️ UOtaste – Projet SEG2505 (Livrables 1, 2 et 3)

## Groupe 01  
**Cours :** SEG2505 – Automne 2025  
**Livrables :** 1️ / 2️ / 3️
**Rôle principal :** Chef  

---


### Ma Contribution
- calcul et affichage du bilan nutrionnel d'une recette
- implementation du boutton d'affichage de la recette
- interface qui affiche l'information nutritionnelle
- Ajout de methodes dans la base de donnee pour permettre le calcul et l'affichage possible


---

### Fichier Travailler

### activity_information_nutritionnelle.xml

- Interface qui affiche le bilan nutritionnelle de la recette

### NuttitionInfo.java
- Ajout de la logique de liaison entre les valeurs nutritionnelles et les ingredients

### NuttitionCalculator.java
- logique java qui permet les calcul du bilan total 


### UpdateRecipeActivity.java
- Ajout de la logique qui ouvre RecipeNutrionInfoActivity.java pour faire apparaitre le bilan

### RecipeNutrionInfoActivity.java
- logique qui lie les recettes choisient dans UpdateRecipeActivity.java avec la logique de   NuttitionCalculator.java pour afficher les résultats.

### Ingredient.java
- Ajout de la logique qui associe les valeurs nutritionnelles aux ingredients lors et apres leur création

### DatabaseProject.java
- Ajout des methodes qui permettent le lient les ingredients avec valeur nutritionnelles aux recettes
- ces methodes sont : Recipe getRecipeById(long recipeId) ; Ingredient getIngredientByName(String ingredientName);


---

### Validation du livrable 3
1. Ajouter un ingrédient avec informations nutritionnelles.  
2. Créer une recette et associer cet ingrédient .  
3. Vérifier le **bilan calorique total** affiché.  

---

## Gestion de version

- Branche principale : `BRANCH-MONZA`  
- Historique complet des livrables 1 → 3.  
- le projet au complet sera dans la branche Developement 


---

## Auteur
**MONZA SANGO**  
Étudiant en bac specialisée en informatique – Université d’Ottawa  
📧 msang006@uottawa.ca  

---

###  But du README
> *Le fichier README est présent pour clarifier ma participation quant aux livrables 3 . suit a un probleme avec git . j'ai push mon travail sur la branch-Monza mais notre projet complet reconstrutible sera sur development.
