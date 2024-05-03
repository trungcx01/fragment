package com.example.fragment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment.R;
import com.example.fragment.adapter.RecycleViewAdapter;
import com.example.fragment.database.DatabaseHelper;
import com.example.fragment.model.Song;

import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private TextView tvTong;
    private Button btnSearch;
    private androidx.appcompat.widget.SearchView searchView;
    private EditText eFrom, eTo;
    private Spinner spSearchGenre;
    private RecycleViewAdapter adapter;
    private DatabaseHelper db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter = new RecycleViewAdapter();
        db = new DatabaseHelper(getContext());
        List<Song> list = db.getAll();
        adapter.setList(list);
        tvTong.setText("Tổng: " + list.size());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Song> list = db.searchByTitle(newText);
                adapter.setList(list);
                tvTong.setText("Tổng: " + list.size());
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        spSearchGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String genre = spSearchGenre.getItemAtPosition(i).toString();
                List<Song> list;
                if (genre.equals("All")){
                    list = db.getAll();
                } else {
                    list = db.searchByGenre(genre);
                }
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                tvTong.setText("Tổng: " + list.size());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.searchRecycleView);
        tvTong = view.findViewById(R.id.tvTong);
        btnSearch = view.findViewById(R.id.btnSearch);
        searchView = view.findViewById(R.id.searchView);
        eFrom = view.findViewById(R.id.eFrom);
        eTo = view.findViewById(R.id.eTo);
        spSearchGenre = view.findViewById(R.id.spSearchGenre);
        String[] arr = getResources().getStringArray(R.array.type);
        String[] arr1 = new String[arr.length + 1];
        arr1[0] = "All";
        for (int i = 0; i< arr.length; i++){
            arr1[i + 1] = arr[i];
        }
        spSearchGenre.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spinner, arr1));

    }
    @Override
    public void onClick(View view) {

    }
}
