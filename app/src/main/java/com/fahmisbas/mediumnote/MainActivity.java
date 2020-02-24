package com.fahmisbas.mediumnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Notes> userNotes = new ArrayList<Notes>();
    public static NotesAdapter notesAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Medium Note");


        SharedPreferences sharedPreferences = this.getSharedPreferences("com.fahmisbas.mediumnote", MODE_PRIVATE);
        String json = sharedPreferences.getString("list", null);

        if (json == null) {
            Notes noted = new Notes();
            noted.setTitle("Example Note");
            noted.setNotes("This is a simple note");
            userNotes.add(noted);
            Log.i("hehe", String.valueOf(userNotes.size()));
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Notes>>() {
            }.getType();
            userNotes = gson.fromJson(json, type);

        }

        RecyclerView rvNotes = findViewById(R.id.rv_notes);
        notesAdapter = new NotesAdapter(this, userNotes);
        rvNotes.setAdapter(notesAdapter);
        notesAdapter.setOnItemLongClickCallback(new NotesAdapter.OnItemLongClickCallback() {
            @Override
            public void onItemLongClick(int position) {
                openDialog(position);
            }
        });


        notesAdapter.setOnItemClickCallback(new NotesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), NotesEditor.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    private void openDialog(int position) {
        final int itemSlected = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Delete This Item")
                .setMessage("Are you sure you want to delete this?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userNotes.remove(itemSlected);
                        notesAdapter.notifyDataSetChanged();

                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.fahmisbas.mediumnote", MODE_PRIVATE);
                        Gson gson = new Gson();
                        String json = gson.toJson(MainActivity.userNotes);
                        sharedPreferences.edit().putString("list", json).apply();
                    }
                })
                .setNegativeButton("No",null);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        selectedMenu(item);
        return super.onOptionsItemSelected(item);
    }

    private void selectedMenu(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            Intent intent = new Intent(getApplicationContext(), NotesEditor.class);
            startActivity(intent);
        }
    }
}

