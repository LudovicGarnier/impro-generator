package com.improgenerator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvWord;
    private TextView tvCategory;
    private Button btnGetWord;
    private Button btnCaractere;
    private Button btnLieu;
    private Button btnPays;
    private Button btnVilles;
    private Button btnMetiers;
    private Button btnEmotions;
    private Button btnThemes;
    private Button btnRelations;
    private Button btnCelebrites;
    private Button btnMots;


    private List<String[]> dataList;  // Liste de tableaux pour chaque ligne
    private String[] headers;  // En-têtes des colonnes
    private Random random;
    private String currentFileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        tvWord = findViewById(R.id.tvWord);
        tvCategory = findViewById(R.id.tvCategory);
        btnGetWord = findViewById(R.id.btnGetWord);
        btnCaractere = findViewById(R.id.btnCaractere);
        btnLieu = findViewById(R.id.btnLieux);
        btnPays = findViewById(R.id.btnPays);
        btnVilles = findViewById(R.id.btnVilles);
        btnMetiers = findViewById(R.id.btnMetiers);
        btnEmotions = findViewById(R.id.btnEmotions);
        btnThemes = findViewById(R.id.btnThemes);
        btnRelations = findViewById(R.id.btnRelations);
        btnCelebrites = findViewById(R.id.btnCelebrites);
        btnMots = findViewById(R.id.btnMots);

        // Initialisation
        dataList = new ArrayList<>();
        random = new Random();

        // Actions des boutons de catégorie
        btnCaractere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile("caractere.csv", "Caractère");
            }
        });

        btnLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile("lieu.csv", "Lieu");
            }
        });

        btnPays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile("pays.csv", "Pays");
            }
        });

        btnMetiers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile("metiers.csv", "Métiers");
            }
        });

        btnVilles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile("villes.csv", "Villes");
            }
        });

        btnEmotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile("emotions.csv", "Emotions");
            }
        });

        btnThemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile("themes.csv", "Thèmes");
            }
        });

        btnRelations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile("relations.csv", "Relations");
            }
        });

        btnCelebrites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile("celebrites.csv", "Célébrités");
            }
        });

        btnMots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile("mots.csv", "Mots");
            }
        });


        // Action du bouton pour obtenir un mot
        btnGetWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRandomWord();
            }
        });
    }

    private void loadFile(String fileName, String themeName) {
        // Vider complètement les données
        dataList.clear();
        headers = null;
        currentFileName = themeName;
        tvWord.setText("Appuyez sur le bouton");

        android.util.Log.d("RandomWord", "=== Chargement de " + fileName + " ===");

        try {
            InputStream is = getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            // Lire les en-têtes (première ligne)
            String headerLine = reader.readLine();
            android.util.Log.d("RandomWord", "En-têtes: " + headerLine);

            if (headerLine != null) {
                headers = headerLine.split(",");
                // Nettoyer les en-têtes
                for (int i = 0; i < headers.length; i++) {
                    headers[i] = headers[i].trim();
                    android.util.Log.d("RandomWord", "Colonne " + i + ": " + headers[i]);
                }
            }

            // Lire toutes les lignes de données
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                android.util.Log.d("RandomWord", "Ligne " + lineNumber + ": " + line);
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    // Nettoyer chaque élément
                    for (int i = 0; i < parts.length; i++) {
                        parts[i] = parts[i].trim();
                    }
                    dataList.add(parts);
                    lineNumber++;
                }
            }

            reader.close();
            is.close();

            android.util.Log.d("RandomWord", "Total chargé: " + dataList.size() + " lignes");

            tvCategory.setText("Thème : " + themeName + " - " + dataList.size() + " mots chargés");
//            Toast.makeText(this, dataList.size() + " mots avec " +
//                    headers.length + " catégories chargées !", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            android.util.Log.e("RandomWord", "ERREUR: " + e.getMessage());
            Toast.makeText(this, "Erreur : fichier " + fileName + " introuvable",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void displayRandomWord() {
        if (dataList.isEmpty() || headers == null) {
            Toast.makeText(this, "Veuillez d'abord choisir un thème",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        android.util.Log.d("RandomWord", "=== Affichage mot aléatoire ===");
        android.util.Log.d("RandomWord", "Nombre de lignes disponibles: " + dataList.size());
        android.util.Log.d("RandomWord", "Nombre de colonnes: " + headers.length);

        // Sélectionner une ligne aléatoire
        int randomRow = random.nextInt(dataList.size());
        String[] selectedRow = dataList.get(randomRow);

        android.util.Log.d("RandomWord", "Ligne sélectionnée: " + randomRow);

        // Sélectionner une colonne aléatoire
        int randomColumn = random.nextInt(Math.min(headers.length, selectedRow.length));

        android.util.Log.d("RandomWord", "Colonne sélectionnée: " + randomColumn);

        // Obtenir le mot et la catégorie
        String word = selectedRow[randomColumn];
        String category = headers[randomColumn];

        android.util.Log.d("RandomWord", "Mot: " + word + " | Catégorie: " + category);

        // Afficher le résultat
        tvWord.setText(word);
        tvCategory.setText("Thème : " + currentFileName + " | Catégorie : " + category);
    }
}