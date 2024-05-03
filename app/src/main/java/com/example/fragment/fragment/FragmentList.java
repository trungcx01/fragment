package com.example.fragment.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment.R;
import com.example.fragment.UpdateDeleteActivity;
import com.example.fragment.adapter.RecycleViewAdapter;
import com.example.fragment.database.DatabaseHelper;
import com.example.fragment.model.Song;

import java.util.List;

public class FragmentList extends Fragment implements RecycleViewAdapter.ItemListenser {
    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        adapter = new RecycleViewAdapter();
        db = new DatabaseHelper(getContext());
        List<Song> list = db.getAll();
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListenser(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Song song = adapter.getItem(position);
        Intent i = new Intent(getActivity(), UpdateDeleteActivity.class);
        i.putExtra("song", song);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Song> list = db.getAll();
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }


}
