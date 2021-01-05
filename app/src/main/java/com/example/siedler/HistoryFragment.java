package com.example.siedler;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryFragment extends Fragment {
    private Context context;

    private DatabaseHelper databaseHelper;

    private RecyclerView recycler;

    private GamesRecyclerAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private List<GamesRecyclerItem> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        context = getContext();

        // init coms
        recycler = v.findViewById(R.id.recycler);

        // init database
        databaseHelper = new DatabaseHelper(context);

        // init recycler
        itemList = new ArrayList<GamesRecyclerItem>(Arrays.asList(
                new GamesRecyclerItem(0, "1%2%1%2%1%2%1%2%1", "patrick#1#bianca#2", "2021/03/13/18/30")
        ));
        recycler.setHasFixedSize(true);
        manager = new LinearLayoutManager(context);
        adapter = new GamesRecyclerAdapter(context, itemList);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        // read items
        onReadAll();

        // edit item
        adapter.setOnItemClickListener(new GamesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GamesRecyclerItem item = itemList.get(position);

                Bundle args = new Bundle();
                args.putString(getString(R.string.intent_numbers), item.getNumbers());
                args.putString(getString(R.string.intent_players), item.getPlayers());
                args.putString(getString(R.string.intent_date), item.getDate());

                GameDialog dialog = new GameDialog();
                dialog.setTargetFragment(HistoryFragment.this, 1);
                dialog.setArguments(args);
                dialog.show(getFragmentManager(), "");
            }
        });

        return v;
    }

    public void sort(){
        Collections.sort(itemList, new Comparator<GamesRecyclerItem>() {
            @Override
            public int compare(GamesRecyclerItem o1, GamesRecyclerItem o2) {
                return - o1.getDate().compareTo(o2.getDate());
            }
        });
        adapter.notifyDataSetChanged();
    }

    /***
     * database connection
     */

    public void onReadAll(){
        itemList.clear();
        adapter.notifyDataSetChanged();
        Cursor games = databaseHelper.readAll();
        while(games.moveToNext()){
            int id = games.getInt(0);
            String numbers = games.getString(1);
            String players = games.getString(2);
            String date = games.getString(3);

            GamesRecyclerItem item = new GamesRecyclerItem(id, numbers, players, date);
            itemList.add(item);
        }
        adapter.notifyDataSetChanged();
        sort();
    }
}
