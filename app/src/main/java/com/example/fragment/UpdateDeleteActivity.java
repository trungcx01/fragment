package com.example.fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fragment.database.DatabaseHelper;
import com.example.fragment.model.Song;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    public Spinner spGenre, spAlbum;
    public EditText eTitle, eArtist;
    public CheckBox cbFavorite;
    public Button btnUpdate, btnCancel, btnDelete;
    private Song song;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btnCancel.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        Intent intent = getIntent();
        song = (Song) intent.getSerializableExtra("song");
        eTitle.setText(song.getTitle());
        eArtist.setText(song.getArtist());
        cbFavorite.setChecked(song.getFavorite() == 1 ? true : false);
        int p1 = 0;
        for (int i = 0; i < spGenre.getCount(); i++){
            if (spGenre.getItemAtPosition(i).toString().equalsIgnoreCase(song.getGenre())){
                p1 = i;
                break;
            }
        }
        spGenre.setSelection(p1);

        int p2 = 0;
        for (int i = 0; i < spAlbum.getCount(); i++){
            if (spAlbum.getItemAtPosition(i).toString().equalsIgnoreCase(song.getAlbum())){
                p2 = i;
                break;
            }
        }
        spAlbum.setSelection(p2);
    }

    public void initView(){
        spGenre = findViewById(R.id.spGenre);
        spAlbum = findViewById(R.id.spAlbum);
        eTitle = findViewById(R.id.eTitle);
        eArtist = findViewById(R.id.eArtist);
        cbFavorite = findViewById(R.id.cbFavorite);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);
        spGenre.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.type)));
        spAlbum.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.album)));
    }

    @Override
    public void onClick(View view) {
        DatabaseHelper db = new DatabaseHelper(this);
        if (view == btnUpdate){
            String t = eTitle.getText().toString();
            String a = eArtist.getText().toString();
            String ab = spAlbum.getSelectedItem().toString();
            String g = spGenre.getSelectedItem().toString();
            int f = cbFavorite.isChecked() ? 1 : 0;

            if (!t.isEmpty() && !a.isEmpty()){
                int id = song.getId();
                Song song = new Song(id, t, a, ab, g, f);
                db = new DatabaseHelper(this);
                db.updateItem(song);
                finish();
            }
        }
        if (view == btnDelete){
            int id = song.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Thông báo xóa");
            builder.setMessage("Bạn có chắc muốn xóa " + song.getTitle() + " không?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseHelper dbb = new DatabaseHelper(getApplicationContext());
                    dbb.deleteItem(id);
                    finish();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if (view == btnCancel){
            finish();
        }
    }
}