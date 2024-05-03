package com.example.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fragment.database.DatabaseHelper;
import com.example.fragment.model.Song;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    public Spinner spGenre, spAlbum;
    public EditText eTitle, eArtist;
    public CheckBox cbFavorite;
    public Button btnUpdate, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btnCancel.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    public void initView(){
        spGenre = findViewById(R.id.spGenre);
        spAlbum = findViewById(R.id.spAlbum);
        eTitle = findViewById(R.id.eTitle);
        eArtist = findViewById(R.id.eArtist);
        cbFavorite = findViewById(R.id.cbFavorite);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
        spGenre.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.type)));
        spAlbum.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.album)));
    }

    @Override
    public void onClick(View view) {
        if (view==btnCancel){
            finish();
        }
        if (view == btnUpdate){
            String t = eTitle.getText().toString();
            String a = eArtist.getText().toString();
            String ab = spAlbum.getSelectedItem().toString();
            String g = spGenre.getSelectedItem().toString();
            int f = cbFavorite.isChecked() ? 1 : 0;

            if (!t.isEmpty() && !a.isEmpty()){
                Song song = new Song(t, a, ab, g, f);
                DatabaseHelper db = new DatabaseHelper(this);
                db.addItem(song);
                finish();
            }
        }
    }
}