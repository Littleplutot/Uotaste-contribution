package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.uotaste.Ingredient;
import com.example.uotaste.NutritionInfo;
import com.example.uotaste.Recipe;
import com.example.uotaste.RecipeIngredient;
import com.example.uotaste.User;

// Classe qui crée et manage toutes les tables de la base de données
public class DatabaseProject extends SQLiteOpenHelper {
    // Information de la base de données
    private static final String DATABASE_NAME = "UOtaste.db";
    private static final int DATABASE_VERSION = 3;

    // Noms des tables
    public static final String TABLE_USERS = "users";
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String TABLE_SALES = "sales";
    public static final String TABLE_NUTRITION_INFO = "nutrition_info";

    // Colonnes pour la table users
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USERNAME = "user_name";
    public static final String COLUMN_IS_ACTIVE = "is_active";
    public static final String COLUMN_PHONE = "user_phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    // Colonnes pour la table Recipes
    public static final String COLUMN_RECIPE_ID = "recipe_id";
    public static final String COLUMN_RECIPE_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_PATH = "image_path";
    public static final String COLUMN_CREATED_BY = "created_by";

    /// Colonnes pour la table Ingredients
    public static final String COLUMN_INGREDIENT_ID = "ingredient_id";
    public static final String COLUMN_INGREDIENT_NAME = "name";
    public static final String COLUMN_QR_CODE = "qr_code";
    public static final String COLUMN_BARCODE = "barcode";
    public static final String COLUMN_QUANTITY_BASE = "quantity_base";
    public static final String COLUMN_QUANTITY_AVAILABLE = "quantity_available";
    public static final String COLUMN_UNIT = "unit";


    // Colonne pour la table Recipe_INGREDIENTS
    public static final String COLUMN_PERCENTAGE = "percentage";

    // Colonnes pour la table SALES
    public static final String COLUMN_SALE_ID = "sale_id";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_SALE_DATE = "sale_date";

    // Colonnes pour la table NUTRITION_INFO
    public static final String COLUMN_NUTRITION_ID = "nutrition_id";
    public static final String COLUMN_CALORIES = "calories";
    public static final String COLUMN_CARBOHYDRATES = "carbohydrates";
    public static final String COLUMN_PROTEINS = "proteins";
    public static final String COLUMN_FATS = "fats";
    public static final String COLUMN_FIBERS = "fibers";
    public static final String COLUMN_SALT = "salt";

    // Requêtes de création des tables
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USERNAME + " TEXT," +
                    COLUMN_EMAIL + " TEXT UNIQUE NOT NULL," +
                    COLUMN_PASSWORD + " TEXT NOT NULL," +
                    COLUMN_FIRST_NAME + " TEXT," +
                    COLUMN_LAST_NAME + " TEXT," +
                    COLUMN_ROLE + " TEXT NOT NULL," +
                    COLUMN_IS_ACTIVE + " INTEGER DEFAULT 1," +
                    COLUMN_PHONE + " TEXT," +
                    COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    COLUMN_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    private static final String CREATE_TABLE_RECIPES =
            "CREATE TABLE " + TABLE_RECIPES + "(" +
                    COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_RECIPE_NAME + " TEXT UNIQUE NOT NULL," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_IMAGE_PATH + " TEXT," +
                    COLUMN_CREATED_BY + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + COLUMN_CREATED_BY + ") REFERENCES " +
                    TABLE_USERS + "(" + COLUMN_USER_ID + ")" + ");";

    private static final String CREATE_TABLE_INGREDIENTS =
            "CREATE TABLE " + TABLE_INGREDIENTS + "(" +
                    COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_INGREDIENT_NAME + " TEXT NOT NULL," +
                    COLUMN_QR_CODE + " TEXT UNIQUE," +
                    COLUMN_BARCODE + " TEXT," +
                    COLUMN_QUANTITY_BASE + " REAL NOT NULL," +
                    COLUMN_QUANTITY_AVAILABLE + " REAL NOT NULL," +
                    COLUMN_UNIT + " TEXT NOT NULL" +
                    ");";

    private static final String CREATE_TABLE_RECIPE_INGREDIENTS =
            "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + "(" +
                    COLUMN_RECIPE_ID + " INTEGER," +
                    COLUMN_INGREDIENT_ID + " INTEGER," +
                    COLUMN_PERCENTAGE + " REAL NOT NULL CHECK(" + COLUMN_PERCENTAGE + " >= 0 AND "
                    + COLUMN_PERCENTAGE + " <= 100)," +
                    "PRIMARY KEY(" + COLUMN_RECIPE_ID + ", " + COLUMN_INGREDIENT_ID + ")," +
                    "FOREIGN KEY(" + COLUMN_RECIPE_ID + ") REFERENCES " + TABLE_RECIPES +
                    "(" + COLUMN_RECIPE_ID + ") ON DELETE CASCADE," +
                    "FOREIGN KEY(" + COLUMN_INGREDIENT_ID + ") REFERENCES " + TABLE_INGREDIENTS +
                    "(" + COLUMN_INGREDIENT_ID + ")" +
                    ");";

    private static final String CREATE_TABLE_SALES =
            "CREATE TABLE " + TABLE_SALES + "(" +
                    COLUMN_SALE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_RECIPE_ID + " INTEGER NOT NULL," +
                    COLUMN_USER_ID + " INTEGER NOT NULL," +
                    COLUMN_RATING + " REAL CHECK(" + COLUMN_RATING + " >= 0 AND " + COLUMN_RATING + " <= 5)," +
                    COLUMN_COMMENT + " TEXT," +
                    COLUMN_SALE_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY(" + COLUMN_RECIPE_ID + ") REFERENCES " + TABLE_RECIPES + "(" + COLUMN_RECIPE_ID + ")," +
                    "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")" +
                    ");";

    private static final String CREATE_TABLE_NUTRITION_INFO =
            "CREATE TABLE " + TABLE_NUTRITION_INFO + "(" +
                    COLUMN_NUTRITION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_INGREDIENT_ID + " INTEGER UNIQUE," +
                    COLUMN_CALORIES + " REAL," +
                    COLUMN_CARBOHYDRATES + " REAL," +
                    COLUMN_PROTEINS + " REAL," +
                    COLUMN_FATS + " REAL," +
                    COLUMN_FIBERS + " REAL," +
                    COLUMN_SALT + " REAL," +
                    "FOREIGN KEY(" + COLUMN_INGREDIENT_ID + ") REFERENCES " + TABLE_INGREDIENTS + "(" + COLUMN_INGREDIENT_ID + ")" +
                    ");";

    public DatabaseProject(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de toutes les tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_RECIPES);
        db.execSQL(CREATE_TABLE_INGREDIENTS);
        db.execSQL(CREATE_TABLE_RECIPE_INGREDIENTS);
        db.execSQL(CREATE_TABLE_SALES);
        db.execSQL(CREATE_TABLE_NUTRITION_INFO);

        // Activer les contraintes de clés étrangères
        db.execSQL("PRAGMA foreign_keys = ON;");

        // Insérer les utilisateurs par défaut
        insertDefaultUsers(db);
        insertTestNutritionDataOnCreation(db);
    }

    /**
     * Récupérer tous les utilisateurs
     * UTILISÉ PAR: ModifyUserActivity, UserManagementActivity
     * @return Liste de tous les utilisateurs
     */
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Log.d("DatabaseProject", "📊 Getting all users...");

        Cursor cursor = db.query(
                TABLE_USERS,
                null,
                null,
                null,
                null,
                null,
                COLUMN_USERNAME + " ASC"
        );

        // 🔍 LOG : Nombre de lignes
        Log.d("DatabaseProject", "Cursor count: " + (cursor != null ? cursor.getCount() : 0));

        if (cursor != null && cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_ACTIVE)) == 1
                );
                userList.add(user);

                // 🔍 LOG : Chaque utilisateur ajouté
                Log.d("DatabaseProject", "Added user: " + user.getUserName());

            } while (cursor.moveToNext());

            cursor.close();
        }



        Log.d("DatabaseProject", "✅ Returning " + userList.size() + " users");
        return userList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprimer les tables existantes
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUTRITION_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Recréer la base de données
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // Activer les clés étrangères à chaque ouverture
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    // Insérer les utilisateurs par défaut (Administrator et Chef)
    private void insertDefaultUsers(SQLiteDatabase db) {
// Insérer les utilisateurs par défaut (Administrator et Chef)
            // Insérer l'administrateur
            db.execSQL("INSERT INTO " + TABLE_USERS + " (" +
                    COLUMN_USERNAME + ", " +        // Ajout de Username
                    COLUMN_EMAIL + ", " +
                    COLUMN_PASSWORD + ", " +
                    COLUMN_ROLE + ", " +
                    COLUMN_FIRST_NAME + ", " +
                    COLUMN_LAST_NAME +
                    ") VALUES ('admin', 'admin@uotaste.com', 'admin-pwd', 'Administrator', 'Admin', 'User');");

            // Insérer le chef
            db.execSQL("INSERT INTO " + TABLE_USERS + " (" +
                    COLUMN_USERNAME + ", " +        // Ajout de Username
                    COLUMN_EMAIL + ", " +
                    COLUMN_PASSWORD + ", " +
                    COLUMN_ROLE + ", " +
                    COLUMN_FIRST_NAME + ", " +
                    COLUMN_LAST_NAME +
                    ") VALUES ('chef', 'chef@uotaste.com', 'chef-pwd', 'Chef', 'Head', 'Chef');");

            // Inserer le waiter
            db.execSQL("INSERT INTO " + TABLE_USERS + " (" +
                    COLUMN_USERNAME + ", " +        // Ajout de usernamme
                    COLUMN_EMAIL + ", " +
                    COLUMN_PASSWORD + ", " +
                    COLUMN_ROLE + ", " +
                    COLUMN_FIRST_NAME + ", " +
                    COLUMN_LAST_NAME +
                    ") VALUES ('waiter', 'waiter@uotaste.com', 'waiter-pwd', 'Waiter', 'Waiter', 'User');");

            //Insere le deuxieme waiter
            db.execSQL("INSERT INTO " + TABLE_USERS + " (" +
                    COLUMN_USERNAME + ", " +        // Ajout de username
                    COLUMN_EMAIL + ", " +
                    COLUMN_PASSWORD + ", " +
                    COLUMN_ROLE + ", " +
                    COLUMN_FIRST_NAME + ", " +
                    COLUMN_LAST_NAME +
                    ") VALUES ('waiter2', 'waiter2@uotaste.com', 'waiter-pwd', 'Waiter', 'Waiter', 'User');");
    }
    //************************************************
    //********************************
    // Pour User
    //********************************
    //Monza-partie
    //************************************************
    /**
     * Vérifier si un username existe déjà
     * UTILISÉ PAR: CreateUserActivity
     * @param username Le username à vérifier
     * @return true si existe, false sinon
     */
    public boolean usernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_USERNAME + " = ?",
                new String[]{username},
                null,
                null,
                null
        );

        boolean exists = (cursor != null && cursor.getCount() > 0);

        if (cursor != null) {
            cursor.close();
        }


        return exists;
    }

    /**
     * Vérifier si un email existe déjà
     * UTILISÉ PAR: CreateUserActivity
     * @param email L'email à vérifier
     * @return true si existe, false sinon
     */
    public boolean emailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_EMAIL + " = ?",
                new String[]{email},
                null,
                null,
                null
        );

        boolean exists = (cursor != null && cursor.getCount() > 0);

        if (cursor != null) {
            cursor.close();
        }

        
        return exists;
    }

    /**
     * Créer un nouvel utilisateur
     * UTILISÉ PAR: CreateUserActivity
     * @param user L'objet User à insérer (sans ID, il sera généré)
     * @return L'ID du nouvel utilisateur ou -1 si erreur
     */
    public long createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, user.getUserName());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_PASSWORD, user.getPassWord());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PHONE, user.getPhoneNumber());
        values.put(COLUMN_ROLE, user.getRole());
        values.put(COLUMN_IS_ACTIVE, user.isActive() ? 1 : 0);

        long userId = db.insert(TABLE_USERS, null, values);


        return userId;
    }
    /**
     * Méthode CENTRALE pour récupérer un utilisateur
     * Peut chercher par ID OU par username
     *
     * @param identifier ID (int) OU username (String)
     * @return User ou null si non trouvé
     */
    public User getUser(Object identifier) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        Cursor cursor = null;

        try {
            if (identifier instanceof Integer) {
                cursor = db.query(TABLE_USERS, null,
                        COLUMN_USER_ID + " = ?",
                        new String[]{String.valueOf(identifier)},
                        null, null, null);
            } else if (identifier instanceof String) {
                // ✅ Chercher par Usernama (pour le login)
                cursor = db.query(TABLE_USERS, null,
                        COLUMN_USERNAME + " = ?",  // Chercher par email !
                        new String[]{(String) identifier},
                        null, null, null);
            }

            if (cursor != null && cursor.moveToFirst()) {
                user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_ACTIVE)) == 1
                );
            }
        } finally {
            if (cursor != null) cursor.close();

        }

        return user;
    }

    /**
     * Mettre à jour un utilisateur existant
     * UTILISÉ PAR: ModifyUserActivity, AdminActivity (update profile)
     * @param user L'objet User avec les nouvelles valeurs (doit avoir un ID)
     * @return Nombre de lignes modifiées (1 si succès, 0 si échec)
     */
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Mettre tous les champs à jour
        values.put(COLUMN_USERNAME, user.getUserName());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PHONE, user.getPhoneNumber());
        values.put(COLUMN_ROLE, user.getRole());
        values.put(COLUMN_IS_ACTIVE, user.isActive() ? 1 : 0);


        // Mettre à jour l'utilisateur avec cet ID
        int rowsAffected = db.update(
                TABLE_USERS,
                values,
                COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())}
        );


        return rowsAffected;
    }
    /*
    * Change on the data base methode to boolean to return true or false
    * */
    // Changer le mot de passe d'un utilisateur
    public boolean updatePassword(int userId, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);

        int rowsAffected = db.update(
                TABLE_USERS,
                values,
                COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)}
        );


        return rowsAffected > 0;  // ✅ Retourne true si au moins 1 ligne modifiée
    }

    // Réinitialiser le mot de passe d'un utilisateur à sa valeur par défaut
    public int resetUserPassword(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Récupérer le rôle de l'utilisateur
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ROLE},
                COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)},
                null, null, null);

        String defaultPassword = "waiter-pwd";
        if (cursor.moveToFirst()) {
            int roleIndex = cursor.getColumnIndex(COLUMN_ROLE);
            String role = cursor.getString(roleIndex);

            if ("Administrator".equals(role)) {
                defaultPassword = "admin-pwd";
            } else if ("Chef".equals(role)) {
                defaultPassword = "chef-pwd";
            }
            cursor.close();
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, defaultPassword);

        int rowsAffected = db.update(TABLE_USERS, values,
                COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)});
        return rowsAffected;
    }

    // Supprimer un utilisateur
    public int deleteUser(int userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_USERS, COLUMN_USER_ID + "= ?",
                new String[]{String.valueOf(userID)});

        return rowsDeleted;
    }

// Réinitialiser la base de données à l'état initial

    public void resetDatabase() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SALES, null, null);

        db.delete(TABLE_NUTRITION_INFO, null, null);

        db.delete(TABLE_RECIPE_INGREDIENTS, null, null);

        db.delete(TABLE_INGREDIENTS, null, null);

        db.delete(TABLE_RECIPES, null, null);

        db.delete(TABLE_USERS,

                COLUMN_USERNAME + " NOT IN (?, ?, ?)",

                new String[]{"admin", "chef", "waiter"});

        // Réinitialiser les mots de passe des 3 utilisateurs de base

        db.execSQL("UPDATE " + TABLE_USERS + " SET " +

                COLUMN_PASSWORD + " = 'admin-pwd' " +

                "WHERE " + COLUMN_USERNAME + " = 'admin';");

        db.execSQL("UPDATE " + TABLE_USERS + " SET " +

                COLUMN_PASSWORD + " = 'chef-pwd' " +

                "WHERE " + COLUMN_USERNAME + " = 'chef';");

        db.execSQL("UPDATE " + TABLE_USERS + " SET " +

                COLUMN_PASSWORD + " = 'waiter-pwd' " +

                "WHERE " + COLUMN_USERNAME + " = 'waiter';");

        // ✅ OPTIONNEL : Réactiver les 3 utilisateurs de base (au cas où)

        db.execSQL("UPDATE " + TABLE_USERS + " SET " +

                COLUMN_IS_ACTIVE + " = 1 " +

                "WHERE " + COLUMN_USERNAME + " IN ('admin', 'chef', 'waiter');");



    }

    //************************************************
    //FIN MONZA- FIN UTILISATEUR
    //************************************************


    //************************************************
    // Pour Recipe
    //************************************************

    // Ajouter une nouvelle recette
    public long addRecipe(String name, String description, String imagePath, long createdBy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_IMAGE_PATH, imagePath);
        values.put(COLUMN_CREATED_BY, createdBy);

        long id = db.insert(TABLE_RECIPES, null, values);
        return id;
    }


// ============================================================
// 🔹 Mettre à jour une recette
// UTILISÉ PAR : UpdateRecipeActivity
// ============================================================
    public boolean updateRecipe(long recipeId, String name, String description, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_IMAGE_PATH, imagePath);

        int rows = db.update(TABLE_RECIPES, values,
                COLUMN_RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipeId)});

        return rows > 0;
    }

    // Récupère uniquement les ingrédients liés à une recette spécifique
    public List<Ingredient> getIngredientsByRecipeId(int recipeId) {
        List<Ingredient> ingredient = new ArrayList<>();
        List<String> ingredientNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT i." + COLUMN_INGREDIENT_NAME +
                " FROM " + TABLE_INGREDIENTS + " i " +
                " INNER JOIN " + TABLE_RECIPE_INGREDIENTS + " ri " +
                " ON i." + COLUMN_INGREDIENT_ID + " = ri." + COLUMN_INGREDIENT_ID +
                " WHERE ri." + COLUMN_RECIPE_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(recipeId)});

        if (cursor.moveToFirst()) {
            do {
                ingredientNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return ingredient ;
    }
    // Met à jour le pourcentage d’un ingrédient déjà associé à une recette
    public boolean updateIngredientPercentage(long recipeId, long ingredientId, double newPercentage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERCENTAGE, newPercentage);

        int rows = db.update(TABLE_RECIPE_INGREDIENTS,
                values,
                COLUMN_RECIPE_ID + " = ? AND " + COLUMN_INGREDIENT_ID + " = ?",
                new String[]{String.valueOf(recipeId), String.valueOf(ingredientId)});

        return rows > 0;
    }


    // POUR RECIPE INGREDIENT
    // Ajouter un ingredient à une recette avec un pourcentage
    public long addIngredientToRecipe(long recipeId, long ingredientId, double percentage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_ID, recipeId);
        values.put(COLUMN_INGREDIENT_ID, ingredientId);
        values.put(COLUMN_PERCENTAGE, percentage);

        long id = db.insert(TABLE_RECIPE_INGREDIENTS, null, values);
        return id;
    }
    // ============================================================
// ❌ Supprimer un ingrédient associé à une recette
// ============================================================
    public boolean deleteIngredientFromRecipe(long recipeId, long ingredientId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // 🔹 (Optionnel) Restaurer la quantité de stock de l’ingrédient
        double percentageUsed = 0;
        Cursor cursor = db.rawQuery(
                "SELECT percentage FROM " + TABLE_RECIPE_INGREDIENTS +
                        " WHERE recipe_id = ? AND ingredient_id = ?",
                new String[]{String.valueOf(recipeId), String.valueOf(ingredientId)}
        );

        if (cursor.moveToFirst()) {
            percentageUsed = cursor.getDouble(0);
        }
        cursor.close();

        // Si tu veux restaurer la quantité d’origine :
        if (percentageUsed > 0) {
            Cursor ingCursor = db.rawQuery(
                    "SELECT quantity_base, quantity_available FROM " + TABLE_INGREDIENTS +
                            " WHERE " + COLUMN_INGREDIENT_ID + " = ?",
                    new String[]{String.valueOf(ingredientId)}
            );

            if (ingCursor.moveToFirst()) {
                double base = ingCursor.getDouble(0);
                double available = ingCursor.getDouble(1);
                double restored = available + (base * (percentageUsed / 100));

                ContentValues values = new ContentValues();
                values.put("quantity_available", restored);
                db.update(TABLE_INGREDIENTS, values,
                        COLUMN_INGREDIENT_ID + " = ?",
                        new String[]{String.valueOf(ingredientId)});
            }
            ingCursor.close();
        }

        // 🔹 Supprimer le lien entre la recette et l’ingrédient
        int rows = db.delete(
                TABLE_RECIPE_INGREDIENTS,
                "recipe_id = ? AND ingredient_id = ?",
                new String[]{String.valueOf(recipeId), String.valueOf(ingredientId)}
        );

        return rows > 0;
    }


    // POUR NUTRITION INFO
    // Ajouter les informations nutritionnelles d'un ingredient
    public long addNutritionInfo(long ingredientId, double calories, double carbs,
                                 double proteins, double fats, double fibers, double salt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INGREDIENT_ID, ingredientId);
        values.put(COLUMN_CALORIES, calories);
        values.put(COLUMN_CARBOHYDRATES, carbs);
        values.put(COLUMN_PROTEINS, proteins);
        values.put(COLUMN_FATS, fats);
        values.put(COLUMN_FIBERS, fibers);
        values.put(COLUMN_SALT, salt);

        long id = db.insert(TABLE_NUTRITION_INFO, null, values);
        return id;
    }

    // Modifier les informations nutritionnelles d'un ingredient
    public int updateNutritionInfo(long ingredientId, double calories, double carbs,
                                   double proteins, double fats, double fibers, double salt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CALORIES, calories);
        values.put(COLUMN_CARBOHYDRATES, carbs);
        values.put(COLUMN_PROTEINS, proteins);
        values.put(COLUMN_FATS, fats);
        values.put(COLUMN_FIBERS, fibers);
        values.put(COLUMN_SALT, salt);

        int rowsAffected = db.update(TABLE_NUTRITION_INFO, values,
                COLUMN_INGREDIENT_ID + " = ?",
                new String[]{String.valueOf(ingredientId)});
        return rowsAffected;
    }

    // Pour SALES
    // Enregistrer une vente
    public long addSale(long recipeId, long waiterId, double rating, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_ID, recipeId);
        values.put(COLUMN_USER_ID, waiterId);
        values.put(COLUMN_RATING, rating);
        values.put(COLUMN_COMMENT, comment);

        long id = db.insert(TABLE_SALES, null, values);
        return id;
    }
    // ======================================================
    // 🔍 Vérifier si une recette existe déjà
    // ======================================================
    /**
     * Vérifie si une recette portant ce nom existe déjà
     * UTILISÉ PAR: CreateRecipeActivity
     * @param name Nom de la recette à vérifier
     * @return true si elle existe, false sinon
     */
    public boolean recipeExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_RECIPES,
                new String[]{COLUMN_RECIPE_ID},
                COLUMN_RECIPE_NAME + " = ?",
                new String[]{name},
                null, null, null
        );

        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return exists;
    }

    // ======================================================
    // 🧂 Récupérer tous les ingrédients (pour le Spinner)
    // ======================================================
    /**
     * Récupère la liste de tous les ingrédients existants dans la BD
     * UTILISÉ PAR: CreateRecipeActivity
     * @return Liste de noms d’ingrédients
     */
    public List<String> getAllIngredients() {
        List<String> ingredientList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_INGREDIENTS,
                new String[]{COLUMN_INGREDIENT_NAME},
                null, null, null, null,
                COLUMN_INGREDIENT_NAME + " ASC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ingredientList.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENT_NAME)));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return ingredientList;
    }

    // ======================================================
    // ⚙️ Mettre à jour la quantité disponible d’un ingrédient
    // ======================================================
    /**
     * Met à jour la quantité utilisée d’un ingrédient après ajout dans une recette.
     * Cette méthode suppose qu’il existe une colonne `quantity` dans la table `ingredients`.
     * Si elle n’existe pas encore, la méthode enregistre uniquement un log (aucune erreur).
     *
     * UTILISÉ PAR: CreateRecipeActivity
     */
    public void updateIngredientQuantity(long ingredientId, double percentageUsed) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Vérifie si la colonne quantity existe dans la table
            Cursor checkCursor = db.rawQuery(
                    "PRAGMA table_info(" + TABLE_INGREDIENTS + ")", null);

            boolean hasQuantityColumn = false;
            while (checkCursor.moveToNext()) {
                String columnName = checkCursor.getString(checkCursor.getColumnIndexOrThrow("name"));
                if ("quantity".equalsIgnoreCase(columnName)) {
                    hasQuantityColumn = true;
                    break;
                }
            }
            checkCursor.close();

            if (!hasQuantityColumn) {
                // Si la colonne n’existe pas, on log simplement
                Log.w("DatabaseProject", "⚠️ Column 'quantity' not found in table 'ingredients'. " +
                        "updateIngredientQuantity() skipped.");
                return;
            }

            // Lire la quantité actuelle
            Cursor cursor = db.query(
                    TABLE_INGREDIENTS,
                    new String[]{"quantity"},
                    COLUMN_INGREDIENT_ID + " = ?",
                    new String[]{String.valueOf(ingredientId)},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                double currentQuantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
                cursor.close();

                // Réduire la quantité en fonction du pourcentage utilisé
                double newQuantity = currentQuantity * (1 - (percentageUsed / 100.0));

                ContentValues values = new ContentValues();
                values.put("quantity", newQuantity);

                db.update(TABLE_INGREDIENTS, values,
                        COLUMN_INGREDIENT_ID + " = ?",
                        new String[]{String.valueOf(ingredientId)});
            } else if (cursor != null) {
                cursor.close();
            }

        } catch (Exception e) {
            Log.e("DatabaseProject", "❌ Error updating ingredient quantity: " + e.getMessage());
        }
        
    }

    // ============================================================
    // 🔹 Obtenir la liste de tous les noms de recettes
    // UTILISÉ PAR : DeleteRecipeActivity
    // ============================================================
    public List<String> getAllRecipeNames() {
        List<String> recipeNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_RECIPES,
                new String[]{COLUMN_RECIPE_NAME},
                null,
                null,
                null,
                null,
                COLUMN_RECIPE_NAME + " ASC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                recipeNames.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME)));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return recipeNames;
    }

    // ============================================================
    // 🔹 Structure interne pour contenir les détails d’une recette
    // ============================================================
    public static class RecipeDetails {
        public long id;
        public String name;
        public String description;
        public String imagePath;
        public long createdBy;
    }

    // ============================================================
    // 🔹 Récupérer les détails complets d’une recette à partir de son nom
    // UTILISÉ PAR : DeleteRecipeActivity
    // ============================================================
    public RecipeDetails getRecipeDetailsByName(String recipeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        RecipeDetails details = null;

        Cursor cursor = db.query(
                TABLE_RECIPES,
                null,
                COLUMN_RECIPE_NAME + " = ?",
                new String[]{recipeName},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            details = new RecipeDetails();
            details.id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID));
            details.name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME));
            details.description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            details.imagePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH));
            details.createdBy = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CREATED_BY));
            cursor.close();
        }

        return details;
    }

    public Recipe getRecipeById(long recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();




        Recipe recipe = null;


        Cursor cursor = db.query(
                TABLE_RECIPES,
                null,
                COLUMN_RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipeId)},
                null,
                null,
                null
        );




        if (cursor != null && cursor.moveToFirst()) {
            List<RecipeIngredientInfo> info = getIngredientsWithPercentages(recipeId);
            List<RecipeIngredient> recipeIngredients = new ArrayList<>();

            for (RecipeIngredientInfo infoitem: info){

                Ingredient ingredient = getIngredientByName(infoitem.name);

                if (ingredient != null){
                    RecipeIngredient recipeIngredient = new RecipeIngredient ((int)recipeId, ingredient, infoitem.percentage);
                    recipeIngredients.add(recipeIngredient);
                }

            }

            recipe = new Recipe();
            recipe.setRecipeId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID)));
            recipe.setNameRecipe(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME)));
            recipe.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
            recipe.setImagePath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH)));
            recipe.setIngredients(recipeIngredients);
            cursor.close();
        }


        return recipe;
    }

    // ============================================================
    // 🔹 Structure interne pour lister les ingrédients d’une recette
    // ============================================================
    public static class RecipeIngredientInfo {
        public String name;
        public double percentage;

        public RecipeIngredientInfo(String name, double percentage) {
            this.name = name;
            this.percentage = percentage;
        }
    }

    // ============================================================
    // 🔹 Obtenir les ingrédients associés à une recette avec leurs pourcentages
    // UTILISÉ PAR : DeleteRecipeActivity
    // ============================================================
    public List<RecipeIngredientInfo> getIngredientsWithPercentages(long recipeId) {
        List<RecipeIngredientInfo> ingredients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT i." + COLUMN_INGREDIENT_NAME + ", ri." + COLUMN_PERCENTAGE +
                " FROM " + TABLE_INGREDIENTS + " i " +
                "JOIN " + TABLE_RECIPE_INGREDIENTS + " ri " +
                "ON i." + COLUMN_INGREDIENT_ID + " = ri." + COLUMN_INGREDIENT_ID +
                " WHERE ri." + COLUMN_RECIPE_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(recipeId)});

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                double percentage = cursor.getDouble(1);
                ingredients.add(new RecipeIngredientInfo(name, percentage));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return ingredients;
    }

    // ============================================================
    // 🔹 Supprimer une recette et restaurer les stocks d’ingrédients
    // UTILISÉ PAR : DeleteRecipeActivity
    // ============================================================
    public boolean deleteRecipeAndRestoreStock(long recipeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        boolean success = false;

        try {
            // 1️⃣ Récupérer les ingrédients et leurs pourcentages
            List<RecipeIngredientInfo> ingredients = getIngredientsWithPercentages(recipeId);

            // 2️⃣ Supprimer la recette (grâce à ON DELETE CASCADE)
            int rowsDeleted = db.delete(TABLE_RECIPES,
                    COLUMN_RECIPE_ID + " = ?",
                    new String[]{String.valueOf(recipeId)});

            // 3️⃣ Restaurer les quantités (si ta BD gère la quantité disponible)
            for (RecipeIngredientInfo ing : ingredients) {
                String update = "UPDATE " + TABLE_INGREDIENTS +
                        " SET " + COLUMN_BARCODE + " = " + COLUMN_BARCODE + " " + // ← placeholder si tu veux gérer des stocks
                        "WHERE " + COLUMN_INGREDIENT_NAME + " = ?";
                db.execSQL(update, new Object[]{ing.name});
            }

            if (rowsDeleted > 0) {
                success = true;
                db.setTransactionSuccessful();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return success;
    }
    // ============================================================
// 🔹 Obtenir l’ID d’un ingrédient à partir de son nom
// UTILISÉ PAR : UpdateRecipeActivity
// ============================================================
    public long getIngredientIdByName(String ingredientName) {
        SQLiteDatabase db = this.getReadableDatabase();
        long id = -1;

        Cursor cursor = db.query(
                TABLE_INGREDIENTS,
                new String[]{COLUMN_INGREDIENT_ID},
                COLUMN_INGREDIENT_NAME + " = ?",
                new String[]{ingredientName},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {

            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENT_ID));



            cursor.close();
        }

        return id;
    }

    public Ingredient getIngredientByName(String ingredientName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Ingredient ingredient = null;

        Cursor ingredient_cursor = db.query(
                TABLE_INGREDIENTS,
                null,
                COLUMN_INGREDIENT_NAME + " = ?",
                new String[]{ingredientName},
                null, null, null
        );

        if (ingredient_cursor != null && ingredient_cursor.moveToFirst()) {
            long ingredientId = ingredient_cursor.getLong(ingredient_cursor.getColumnIndexOrThrow(COLUMN_INGREDIENT_ID));

            ingredient = new Ingredient((int) ingredientId,
                    ingredient_cursor.getString(ingredient_cursor.getColumnIndexOrThrow(COLUMN_INGREDIENT_NAME)),
                    ingredient_cursor.getString(ingredient_cursor.getColumnIndexOrThrow(COLUMN_BARCODE)),
                    ingredient_cursor.getDouble(ingredient_cursor.getColumnIndexOrThrow(COLUMN_QUANTITY_BASE)),
                    ingredient_cursor.getString(ingredient_cursor.getColumnIndexOrThrow(COLUMN_UNIT)));


            ingredient_cursor.close();


            Cursor nutrition_cursor = db.query(
                    TABLE_NUTRITION_INFO,
                    null,
                    COLUMN_INGREDIENT_ID + " = ?",
                    new String[]{String.valueOf(ingredientId)},
                    null, null, null);

            if (nutrition_cursor.moveToFirst()) {
                NutritionInfo nutritionInfo = new NutritionInfo(nutrition_cursor.getDouble(nutrition_cursor.getColumnIndexOrThrow(COLUMN_CARBOHYDRATES)),
                        nutrition_cursor.getDouble(nutrition_cursor.getColumnIndexOrThrow(COLUMN_PROTEINS)),
                        nutrition_cursor.getDouble(nutrition_cursor.getColumnIndexOrThrow(COLUMN_FATS)),
                        nutrition_cursor.getDouble(nutrition_cursor.getColumnIndexOrThrow(COLUMN_FIBERS)));

                ingredient.setNutritionInfo(nutritionInfo);

                nutrition_cursor.close();

            }
        }


        return ingredient;
    }

    public void insertTestNutritionData(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_NUTRITION_INFO, null, null, null, null, null, null);

        if (cursor.getCount()>0){
            cursor.close();
            return;
        }

        cursor.close();

        ContentValues poulet = new ContentValues();
        poulet.put(COLUMN_INGREDIENT_ID, 1);
        poulet.put(COLUMN_CALORIES, 100);
        poulet.put(COLUMN_CARBOHYDRATES, 20);
        poulet.put(COLUMN_PROTEINS, 30);
        poulet.put(COLUMN_FATS,3.6);
        poulet.put(COLUMN_FIBERS, 0.5);
        poulet.put(COLUMN_SALT, 0.1);

        db.insert(TABLE_NUTRITION_INFO, null, poulet);

        ContentValues mayo = new ContentValues();
        mayo.put(COLUMN_INGREDIENT_ID, 2);
        mayo.put(COLUMN_CALORIES, 680);
        mayo.put(COLUMN_CARBOHYDRATES,0.6);
        mayo.put(COLUMN_PROTEINS, 25);
        mayo.put(COLUMN_FATS, 75);
        mayo.put(COLUMN_FIBERS, 0.1);
        mayo.put(COLUMN_SALT, 0.1);

        db.insert(TABLE_NUTRITION_INFO, null, mayo);
    }

    protected void insertTestNutritionDataOnCreation(SQLiteDatabase db){

        ContentValues poulet = new ContentValues();
        poulet.put(COLUMN_INGREDIENT_ID, 1);
        poulet.put(COLUMN_CALORIES, 100);
        poulet.put(COLUMN_CARBOHYDRATES, 20);
        poulet.put(COLUMN_PROTEINS, 30);
        poulet.put(COLUMN_FATS,3.6);
        poulet.put(COLUMN_FIBERS, 0.5);
        poulet.put(COLUMN_SALT, 0.1);

        db.insert(TABLE_NUTRITION_INFO, null, poulet);

        ContentValues mayo = new ContentValues();
        mayo.put(COLUMN_INGREDIENT_ID, 2);
        mayo.put(COLUMN_CALORIES, 680);
        mayo.put(COLUMN_CARBOHYDRATES,0.6);
        mayo.put(COLUMN_PROTEINS, 25);
        mayo.put(COLUMN_FATS, 75);
        mayo.put(COLUMN_FIBERS, 0.1);
        mayo.put(COLUMN_SALT, 0.1);

        db.insert(TABLE_NUTRITION_INFO, null, mayo);


    }
    // ============================================================
// 🔹 Vérifier si un ingrédient est déjà lié à une recette
// UTILISÉ PAR : UpdateRecipeActivity
// ============================================================
    public boolean isIngredientLinkedToRecipe(long recipeId, long ingredientId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPE_INGREDIENTS,
                new String[]{COLUMN_RECIPE_ID},
                COLUMN_RECIPE_ID + " = ? AND " + COLUMN_INGREDIENT_ID + " = ?",
                new String[]{String.valueOf(recipeId), String.valueOf(ingredientId)},
                null, null, null
        );

        boolean exists = (cursor != null && cursor.getCount() > 0);

        if (cursor != null) cursor.close();

        return exists;
    }

}