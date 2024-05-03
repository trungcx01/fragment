package com.example.fragment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fragment.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SongsDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SONGS_TABLE = "CREATE TABLE songs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "artist TEXT," +
                "album TEXT," +
                "genre TEXT," +
                "favorite INTEGER)";
        db.execSQL(CREATE_SONGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Song> getAll(){
        List<Song> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
//        String order = "DESC"
        Cursor rs = st.query("songs", null, null, null, null, null, null);
        while(rs!= null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String artist = rs.getString(2);
            String album = rs.getString(3);
            String genre = rs.getString(4);
            int favorite = rs.getInt(5);
            list.add(new Song(id, title, artist, album, genre, favorite));
        }
        return list;
    }

    public long addItem(Song song){
        ContentValues values = new ContentValues();
        values.put("title", song.getTitle());
        values.put("artist", song.getArtist());
        values.put("album", song.getAlbum());
        values.put("genre", song.getGenre());
        values.put("favorite", song.getFavorite());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("songs",null, values);
    }

    public int updateItem(Song song) {
        ContentValues values = new ContentValues();
        values.put("title", song.getTitle());
        values.put("artist", song.getArtist());
        values.put("album", song.getAlbum());
        values.put("genre", song.getGenre());
        values.put("favorite", song.getFavorite());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(song.getId())};
        return sqLiteDatabase.update("songs",
                values, whereClause, whereArgs);

    }

    public int deleteItem(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("songs",
                whereClause, whereArgs);

    }

    public List<Song> searchByTitle(String key) {
        List<Song> list= new ArrayList<>();
        String whereClause = "title like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("songs",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String artist = rs.getString(2);
            String album = rs.getString(3);
            String genre = rs.getString(4);
            int favorite = rs.getInt(5);
            list.add(new Song(id,title,artist, album,genre, favorite));
        }
        return list;
    }

    public List<Song> searchByGenre(String key) {
        List<Song> list = new ArrayList<>();
        String whereClause = "genre = ?";
        String[] whereArgs = { key };
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("songs",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String artist = rs.getString(2);
            String album = rs.getString(3);
            String genre = rs.getString(4);
            int favorite = rs.getInt(5);
            list.add(new Song(id, title, artist, album, genre, favorite));
        }
        return list;
    }

    public Map<String, Integer> countSongsByGenre() {
        Map<String, Integer> genreCount = new HashMap<>();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.rawQuery("SELECT genre, COUNT(id) AS count FROM songs GROUP BY genre", null);
        while ((rs != null) && (rs.moveToNext())) {
            String genre = rs.getString(0);
            int count = rs.getInt(1);
            genreCount.put(genre, count);
        }
        rs.close();
        return genreCount;
    }
}
