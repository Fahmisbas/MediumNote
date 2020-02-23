package com.fahmisbas.mediumnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;


public class NotesEditor extends AppCompatActivity {

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);
        setTitle("Note");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        EditText edtNote = findViewById(R.id.edt_note);
        EditText edtTitle = findViewById(R.id.edt_title);

        Intent intent = getIntent();
        id = intent.getIntExtra("position", -1);

        if (id != -1) {
            Notes noted = NotesAdapter.userNotes.get(id);
            edtTitle.setText(noted.getTitle());
            edtNote.setText(noted.getNotes());
        } else {
            Notes noted = new Notes();
            noted.setTitle("");
            noted.setNotes("");
            MainActivity.userNotes.add(noted);
            id = NotesAdapter.userNotes.size() - 1;
            Log.i("huehue", String.valueOf(id));
        }


        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Notes noted = NotesAdapter.userNotes.get(id);
                noted.setTitle(String.valueOf(s));
                MainActivity.notesAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.fahmisbas.mediumnote", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(MainActivity.userNotes); //convert ArrayList dengan custom object(Notes) ke json
                editor.putString("list", json); //supaya bisa di terima ama sharedPreference, soal e ga bisa make HashSet dengan custom object
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        edtNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Notes noted = NotesAdapter.userNotes.get(id);
                noted.setNotes(String.valueOf(s));
                MainActivity.notesAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.fahmisbas.mediumnote", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(MainActivity.userNotes);
                editor.putString("list", json);
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
