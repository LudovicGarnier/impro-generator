package com.improgenerator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.improgenerator.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvWord;
    private Button btnGetWord;
    private List<String> wordList;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        tvWord = findViewById(R.id.tvWord);
        btnGetWord = findViewById(R.id.btnGetWord);

        // Initialisation
        wordList = new ArrayList<>();
        random = new Random();

        // Charger les mots depuis le fichier CSV
        loadWordsFromCSV();

        // Action du bouton
        btnGetWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRandomWord();
            }
        });
    }

    private void loadWordsFromCSV() {
        System.out.println("pays.csv");
        try {
            // Lire le fichier CSV depuis assets
            InputStream is = getAssets().open("pays.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            // Ignorer la première ligne si c'est un en-tête
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                // Séparer par virgule et prendre le premier élément
                String[] parts = line.split(",");
                if (parts.length > 0 && !parts[0].trim().isEmpty()) {
                    wordList.add(parts[0].trim());
                }
            }

            reader.close();
            is.close();

            Toast.makeText(this, wordList.size() + " mots chargés",
                    Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur de chargement du fichier",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void displayRandomWord() {
        if (wordList.isEmpty()) {
            tvWord.setText("Aucun mot disponible");
            return;
        }

        // Sélectionner un mot aléatoire
        int randomIndex = random.nextInt(wordList.size());
        String randomWord = wordList.get(randomIndex);

        // Afficher le mot
        tvWord.setText(randomWord);
    }
}