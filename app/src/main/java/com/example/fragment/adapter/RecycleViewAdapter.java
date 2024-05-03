package com.example.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment.R;
import com.example.fragment.model.Song;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ListViewHolder> {
    private List<Song> list;
    private ItemListenser itemListenser;

    public RecycleViewAdapter() {
        this.list = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setList(List<Song> list) {
        this.list = list;
    }

    public void setItemListenser(ItemListenser itemListenser) {
        this.itemListenser = itemListenser;
    }
    public Song getItem(int position){
        return list.get(position);
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Song song = list.get(position);
        holder.title.setText(song.getTitle());
        holder.artist.setText(song.getArtist());
        holder.album.setText(song.getAlbum());
        holder.genre.setText(song.getGenre());
        holder.favorite.setChecked(song.getFavorite() == 1 ? true : false);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, artist, genre, album;
        public CheckBox favorite;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            artist = itemView.findViewById(R.id.artist);
            genre = itemView.findViewById(R.id.genre);
            album = itemView.findViewById(R.id.album);
            favorite = itemView.findViewById(R.id.favorite);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemListenser != null){
                itemListenser.onItemClick(view,getAdapterPosition());
            }
        }
    }

    public interface ItemListenser{
        void onItemClick(View view, int position);
    }
}
