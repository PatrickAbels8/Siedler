package com.example.siedler;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class GameDialog extends DialogFragment {
    private Button action_ok;
    private TextView header;
    private TextView game;

    private String numbers;
    private String players;
    private String date;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.dialog_game, container, false);

        header = v.findViewById(R.id.header);
        action_ok = v.findViewById(R.id.ok);
        game = v.findViewById(R.id.game);

        Bundle args = new Bundle();
        numbers = args.getString(getString(R.string.intent_numbers));
        players = args.getString(getString(R.string.intent_players));
        date = args.getString(getString(R.string.intent_date));

        String[] players_data = players.split(getString(R.string.delimiter_players));
        String[] numbers_data = numbers.split(getString(R.string.delimiter_numbers));

        String o = numbers+"\n\n"+players+"\n\n"+date;
        game.setText(o);
        header.setText(date);

        action_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return v;
    }
}
