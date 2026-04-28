package Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uotaste.R;
import com.example.uotaste.Recipe;

import androidx.appcompat.app.AppCompatActivity;

import Database.DatabaseProject;
import com.example.uotaste.NutritionCalculator;
import com.example.uotaste.NutritionInfo;




public class RecipeNutritionInfoActivity extends AppCompatActivity {

    private long recipeId;

    private DatabaseProject db;

    private TextView afficherResultaBt;

    private ImageView backBt;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_nutritionelle);

        afficherResultaBt = findViewById(R.id.afficherResultaBt);

        backBt = findViewById(R.id.backBt);
        backBt.setOnClickListener(v -> finish());
        // pour recuperer l'id de la recette a modifier
        long recipeId = getIntent().getLongExtra("recipeId", -1);

        // mesure de defense
        if (recipeId == -1) {
            Toast.makeText(this, "No recipe selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

             db = new DatabaseProject(this);

            // charger la recette avec l'id correspondant
            Recipe recipe = db.getRecipeById(recipeId);

            if(recipe != null && recipe.getIngredients() != null){
                // afficher les ingredients de la recette
                Toast.makeText(this, "Ingredients: " + recipe.getIngredients().size(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No ingredients found for this recipe", Toast.LENGTH_SHORT).show();
            }
            // je stock le resultat nutritionnel dans une variable
            NutritionInfo nutritionInfo = NutritionCalculator.calculateRecipeNutrition(recipe);

            // afficher le resultat nutritionnel dans le textview
            String formatNutritionInfo = nutritionInfo.formatNutritionInfo(nutritionInfo);
            afficherResultaBt.setText(formatNutritionInfo);
            afficherResultaBt.setTextSize(20);




    }







}
