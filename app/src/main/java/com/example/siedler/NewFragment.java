package com.example.siedler;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NewFragment extends Fragment implements StartDialog.OnInputSelected, EndDialog.OnInputSelected {
    private Context context;

    private DatabaseHelper databaseHelper;

    private LinearLayout game_layout;
    private TextView cover;
    private TextView delete;
    private TextView[] ns;
    private TextView numbers;
    private TextView sum;
    private FloatingActionButton start;
    private FloatingActionButton end;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_new, container, false);
        context = getContext();

        // init coms
        game_layout = v.findViewById(R.id.game_layout);
        cover = v.findViewById(R.id.cover);
        ns = new TextView[11];
        ns[0] = v.findViewById(R.id.n2);
        ns[1] = v.findViewById(R.id.n3);
        ns[2] = v.findViewById(R.id.n4);
        ns[3] = v.findViewById(R.id.n5);
        ns[4] = v.findViewById(R.id.n6);
        ns[5] = v.findViewById(R.id.n7);
        ns[6] = v.findViewById(R.id.n8);
        ns[7] = v.findViewById(R.id.n9);;
        ns[8] = v.findViewById(R.id.n10);
        ns[9] = v.findViewById(R.id.n11);
        ns[10] = v.findViewById(R.id.n12);
        delete = v.findViewById(R.id.delete);
        numbers = v.findViewById(R.id.numbers);
        sum = v.findViewById(R.id.sum);
        start = v.findViewById(R.id.start);
        end = v.findViewById(R.id.end);

        // init database
        databaseHelper = new DatabaseHelper(context);

        // init layout
        if(getIngame()){
            String _numbers = getNumbers();
            numbers.setText(_numbers);
            String _sum = getSum(_numbers);
            sum.setText(_sum);
            visualizeIngame(true);
        }else{
            visualizeIngame(false);
        }

        // start game
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartDialog dialog = new StartDialog();
                dialog.setTargetFragment(NewFragment.this, 1);
                dialog.show(getFragmentManager(), "");
            }
        });

        // make move
        for(TextView n: ns){
            n.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String _numbers = getNumbers();
                    if(_numbers.isEmpty())
                        _numbers += (String)v.getTag();
                    else
                        _numbers += getString(R.string.delimiter_numbers)+(String)v.getTag();
                    setNumbers(_numbers);
                    numbers.setText(_numbers);
                    String _sum = getSum(_numbers);
                    sum.setText(_sum);
                }
            });
        }

        // undo move
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _numbers = getNumbers();
                if(_numbers.isEmpty()){
                }else if(!_numbers.contains(getString(R.string.delimiter_numbers))){
                    _numbers = "";
                }else{
                    _numbers = _numbers.substring(0, _numbers.lastIndexOf(getString(R.string.delimiter_numbers)));
                }
                setNumbers(_numbers);
                numbers.setText(_numbers);
                String _sum = getSum(_numbers);
                sum.setText(_sum);
            }
        });

        // end game
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numbers.getText().toString().isEmpty())
                    return;
                EndDialog dialog = new EndDialog();
                dialog.setTargetFragment(NewFragment.this, 1);
                dialog.show(getFragmentManager(), "");
            }
        });

        return v;
    }

    @Override
    public void sendStart(){
        visualizeIngame(true);
        setIngame(true);
        String current_date = new SimpleDateFormat(String.join(getString(R.string.delimiter_date), "yyyy", "MM", "dd", "HH", "mm", "ss")).format(new Date());
        setDate(current_date);
        setNumbers("");
        numbers.setText("");
    }

    @Override
    public void sendEnd(String players) {
        onAdd(players);
        visualizeIngame(false);
        setIngame(false);
    }

    public String getSum(String numbers){
        String[] _numbers = numbers.split(getString(R.string.delimiter_numbers));
        return Integer.toString(_numbers.length)+" "+getString(R.string.appendix_sum);
    }

    public void visualizeIngame(boolean ingame){
        if(!ingame){
            cover.setVisibility(View.VISIBLE);
            start.setVisibility(View.VISIBLE);
            end.setVisibility(View.INVISIBLE);
            game_layout.setVisibility(View.INVISIBLE);
        }else{
            cover.setVisibility(View.INVISIBLE);
            start.setVisibility(View.INVISIBLE);
            end.setVisibility(View.VISIBLE);
            game_layout.setVisibility(View.VISIBLE);
        }
    }

    /***
     * shared preferences connection
     */

    public String getNumbers(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(getString(R.string.prefs_numbers), "");
    }

    public void setNumbers(String numbers){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.prefs_numbers), numbers);
        editor.apply();
    }

    public boolean getIngame(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(getString(R.string.prefs_ingame), false);
    }

    public void setIngame(boolean ingame){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getString(R.string.prefs_ingame), ingame);
        editor.apply();
    }

    public String getDate(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(getString(R.string.prefs_date), "");
    }

    public void setDate(String date){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.prefs_date), date);
        editor.apply();
    }


    /***
     * database connection
     */

    public void onAdd(String players){
        long result = databaseHelper.add(getNumbers(), players, getDate());
        if(result == -1){
            Toast.makeText(context, getString(R.string.error_dbError), Toast.LENGTH_SHORT).show();
        }
    }
}
