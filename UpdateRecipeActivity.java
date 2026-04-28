package Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uotaste.R;
import Database.DatabaseProject;

import java.util.List;

public class UpdateRecipeActivity extends AppCompatActivity {

    private Spinner recipeSpinner, ingredientSpinner;
    private EditText recipeNameET, descriptionET, percentageET;
    private ImageView recipeImageIV, backArrow;
    private Button selectImageBt, addIngredientBt, saveChangesBt, nutrionInfoBt;
    private LinearLayout ingredientListLayout;

    private DatabaseProject db;
    private long selectedRecipeId = -1;
    private Uri selectedImageUri;

    private static final int PICK_IMAGE_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);

        db = new DatabaseProject(this);

        // Liaison des vues
        recipeSpinner = findViewById(R.id.recipeSpinner);
        ingredientSpinner = findViewById(R.id.ingredientSpinner);
        recipeNameET = findViewById(R.id.recipeNameET);
        descriptionET = findViewById(R.id.descriptionET);
        percentageET = findViewById(R.id.percentageET);
        recipeImageIV = findViewById(R.id.recipeImageIV);
        backArrow = findViewById(R.id.backArrow);
        selectImageBt = findViewById(R.id.selectImageBt);
        addIngredientBt = findViewById(R.id.addIngredientBt);
        saveChangesBt = findViewById(R.id.saveChangesBt);
        ingredientListLayout = findViewById(R.id.ingredientListLayout);

        nutrionInfoBt = findViewById(R.id.nutrionInfoBt);
        nutrionInfoBt.setOnClickListener(v -> openNutritionInformation());


        // Charger les recettes
        loadRecipesInSpinner();
        loadIngredientsInSpinner();

        // Retour
        backArrow.setOnClickListener(v -> finish());

        // Choisir une image
        selectImageBt.setOnClickListener(v -> openImagePicker());

        // Quand on sélectionne une recette
        recipeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String recipeName = recipeSpinner.getSelectedItem().toString();
                if (!recipeName.equals("Select a recipe")) {
                    loadRecipeDetails(recipeName);
                } else {
                    clearRecipeDetails();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Ajouter un ingrédient
        addIngredientBt.setOnClickListener(v -> addIngredientToRecipe());

        // Sauvegarder la recette
        saveChangesBt.setOnClickListener(v -> updateRecipe());
    }

    protected void openNutritionInformation() {
        if (selectedRecipeId == -1){
            Toast.makeText(this, "No recipe selected", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent intent = new Intent(this, RecipeNutritionInfoActivity.class);
        intent.putExtra("recipeId", selectedRecipeId);

        startActivity(intent);

    }


    // ============================================================
    // 📸 Choisir une image
    // ============================================================
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            recipeImageIV.setImageURI(selectedImageUri);
        }
    }

    // ============================================================
    // 🧁 Charger les recettes dans le spinner
    // ============================================================
    private void loadRecipesInSpinner() {
        List<String> recipes = db.getAllRecipeNames();

        if (recipes.isEmpty()) recipes.add("Select a recipe");
        else recipes.add(0, "Select a recipe");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, recipes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeSpinner.setAdapter(adapter);
    }

    // ============================================================
    // 🧂 Charger les ingrédients existants dans le spinner
    // ============================================================
    private void loadIngredientsInSpinner() {
        List<String> ingredients = db.getAllIngredients();

        if (ingredients.isEmpty()) ingredients.add("No ingredient found");
        else ingredients.add(0, "Select ingredient");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ingredients);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingredientSpinner.setAdapter(adapter);
    }

    // ============================================================
    // 🍰 Charger les détails de la recette sélectionnée
    // ============================================================
    private void loadRecipeDetails(String recipeName) {
        DatabaseProject.RecipeDetails details = db.getRecipeDetailsByName(recipeName);

        if (details == null) {
            Toast.makeText(this, "Recipe not found in database", Toast.LENGTH_SHORT).show();
            clearRecipeDetails();
            return;
        }

        selectedRecipeId = details.id;
        recipeNameET.setText(details.name);
        descriptionET.setText(details.description != null ? details.description : "");

        // Charger l'image
        if (details.imagePath != null && !details.imagePath.isEmpty()) {
            recipeImageIV.setVisibility(View.VISIBLE);
            recipeImageIV.setImageURI(Uri.parse(details.imagePath));
        } else {
            recipeImageIV.setVisibility(View.GONE);
        }

        // Charger les ingrédients associés
        List<DatabaseProject.RecipeIngredientInfo> ingredients = db.getIngredientsWithPercentages(details.id);
        refreshIngredientList(ingredients);
    }

    // ============================================================
    // 🔄 Vider les champs
    // ============================================================
    private void clearRecipeDetails() {
        selectedRecipeId = -1;
        recipeNameET.setText("");
        descriptionET.setText("");
        recipeImageIV.setVisibility(View.GONE);
        ingredientListLayout.removeAllViews();
    }

    // ============================================================
    // 🧾 Rafraîchir la liste d’ingrédients avec actions interactives
    // ============================================================
    private void refreshIngredientList(List<DatabaseProject.RecipeIngredientInfo> ingredients) {
        ingredientListLayout.removeAllViews();

        if (ingredients.isEmpty()) {
            TextView none = new TextView(this);
            none.setText("No ingredients linked to this recipe.");
            none.setTextColor(getResources().getColor(android.R.color.white));
            none.setPadding(8, 8, 8, 8);
            ingredientListLayout.addView(none);
            return;
        }

        for (DatabaseProject.RecipeIngredientInfo info : ingredients) {
            TextView tv = new TextView(this);
            tv.setText("• " + info.name + " — " + info.percentage + "%");
            tv.setTextColor(getResources().getColor(android.R.color.white));
            tv.setPadding(10, 15, 10, 15);
            tv.setTextSize(17);
            tv.setBackgroundResource(android.R.drawable.list_selector_background);
            tv.setClickable(true);
            tv.setFocusable(true);

            // ➕ CLIC COURT → modifier pourcentage
            tv.setOnClickListener(v -> showEditPercentageDialog(info.name, info.percentage));

            // ➖ CLIC LONG → supprimer ingrédient
            tv.setOnLongClickListener(v -> {
                confirmDeleteIngredient(info.name);
                return true;
            });

            // Double-Clic --> voir infos nutritionneles
            tv.setOnClickListener(v -> {
                //Menu  de choix : Modifier ou voir nutrition
                showIngredientOptionsMenu(info.name, info.percentage);
            });

            ingredientListLayout.addView(tv);
        }
    }

    // ============================================================
    // ✏️ Boîte de dialogue pour modifier le pourcentage
    // ============================================================
    private void showEditPercentageDialog(String ingredientName, double oldPercentage) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_percentage, null);
        EditText editPercentageET = dialogView.findViewById(R.id.editPercentageET);
        Button saveBt = dialogView.findViewById(R.id.savePercentageBt);
        editPercentageET.setText(String.valueOf(oldPercentage));

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        saveBt.setOnClickListener(v -> {
            String newVal = editPercentageET.getText().toString().trim();
            if (newVal.isEmpty()) {
                Toast.makeText(this, "Enter new percentage", Toast.LENGTH_SHORT).show();
                return;
            }

            double newPercentage = Double.parseDouble(newVal);
            if (newPercentage <= 0 || newPercentage > 100) {
                Toast.makeText(this, "Invalid percentage", Toast.LENGTH_SHORT).show();
                return;
            }

            long ingredientId = db.getIngredientIdByName(ingredientName);
            boolean success = db.updateIngredientPercentage(selectedRecipeId, ingredientId, newPercentage);

            if (success) {
                Toast.makeText(this, "Updated successfully!", Toast.LENGTH_SHORT).show();
                refreshIngredientList(db.getIngredientsWithPercentages(selectedRecipeId));
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    // ============================================================
    // ❌ Supprimer un ingrédient lié à la recette
    // ============================================================
    private void confirmDeleteIngredient(String ingredientName) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Ingredient")
                .setMessage("Remove \"" + ingredientName + "\" from this recipe?")
                .setPositiveButton("Yes", (d, w) -> {
                    long ingredientId = db.getIngredientIdByName(ingredientName);
                    boolean deleted = db.deleteIngredientFromRecipe(selectedRecipeId, ingredientId);
                    if (deleted) {
                        Toast.makeText(this, "Ingredient removed!", Toast.LENGTH_SHORT).show();
                        refreshIngredientList(db.getIngredientsWithPercentages(selectedRecipeId));
                    } else {
                        Toast.makeText(this, "Deletion failed.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // ============================================================
    // ➕ Ajouter un nouvel ingrédient
    // ============================================================
    private void addIngredientToRecipe() {
        if (selectedRecipeId == -1) {
            Toast.makeText(this, "Select a recipe first!", Toast.LENGTH_SHORT).show();
            return;
        }

        String ingredientName = ingredientSpinner.getSelectedItem().toString();
        if (ingredientName.equals("Select ingredient") || ingredientName.equals("No ingredient found")) {
            Toast.makeText(this, "Select a valid ingredient", Toast.LENGTH_SHORT).show();
            return;
        }

        String percentageText = percentageET.getText().toString().trim();
        if (percentageText.isEmpty()) {
            Toast.makeText(this, "Enter percentage", Toast.LENGTH_SHORT).show();
            return;
        }

        double percentage = Double.parseDouble(percentageText);
        if (percentage <= 0 || percentage > 100) {
            Toast.makeText(this, "Percentage must be between 1 and 100", Toast.LENGTH_SHORT).show();
            return;
        }

        long ingredientId = db.getIngredientIdByName(ingredientName);
        if (db.isIngredientLinkedToRecipe(selectedRecipeId, ingredientId)) {
            Toast.makeText(this, "Ingredient already linked!", Toast.LENGTH_SHORT).show();
            return;
        }

        db.addIngredientToRecipe(selectedRecipeId, ingredientId, percentage);
        Toast.makeText(this, "Ingredient added!", Toast.LENGTH_SHORT).show();

        refreshIngredientList(db.getIngredientsWithPercentages(selectedRecipeId));
        percentageET.setText("");
    }

    // ============================================================
    // 💾 Sauvegarder la recette
    // ============================================================
    private void updateRecipe() {
        if (selectedRecipeId == -1) {
            Toast.makeText(this, "No recipe selected", Toast.LENGTH_SHORT).show();
            return;
        }

        String newName = recipeNameET.getText().toString().trim();
        String newDesc = descriptionET.getText().toString().trim();
        String newImagePath = selectedImageUri != null ? selectedImageUri.toString() : null;

        boolean success = db.updateRecipe(selectedRecipeId, newName, newDesc, newImagePath);

        if (success) {
            Toast.makeText(this, "Recipe updated successfully!", Toast.LENGTH_SHORT).show();
            loadRecipesInSpinner();
        } else {
            Toast.makeText(this, "Error updating recipe", Toast.LENGTH_SHORT).show();
        }
    }

    // ============================================================
    // Menu d'options pour un ingrédient
    // ============================================================
    private void showIngredientOptionsMenu(String ingredientName, double percentage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Option for " + ingredientName);

        // option du menu
        String[] options = {"View Nutrition", "Edit Percentage", "Detele Ingredient"};
        builder.setItems(options, (dialog, which) -> {
            switch(which) {
                case 0: // Voir infos nutritionnelles
                    openNutritionActivity(ingredientName);
                    break;
                case 1: // Modifie le pourentage
                    showIngredientOptionsMenu(ingredientName, percentage);
                    break;
                case 2: // Supprimer
                    confirmDeleteIngredient(ingredientName);
                    break;

            }
        });
        builder.show();
    }

    // ============================================================
    // Ouvrir l'activité des info nutritionnelles
    // ============================================================
    private void openNutritionActivity(String ingredientName) {
        // Recupere l'id de l'ingredient
        long ingredientId = db.getIngredientIdByName(ingredientName);

        // Lancer la nouvelle activite
        Intent intent = new Intent(this, IngredientNutritionActivity.class);
        intent.putExtra("INGREDIENT_ID" , ingredientId);
        intent.putExtra("INGREDIENT_NAME" , ingredientName);
        startActivity(intent);
    }

}
