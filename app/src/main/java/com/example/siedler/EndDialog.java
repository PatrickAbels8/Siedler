package com.example.siedler;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

public class EndDialog extends DialogFragment {
    private TextInputLayout player1, player2, player3, player4, player5, player6, player7, player8;
    private TextInputLayout points1, points2, points3, points4, points5, points6, points7, points8;
    private Button action_ok;
    private Button action_cancel;

    private String delimiter;

    public interface OnInputSelected{
        // patrick#3#till#10#bianca#8
        void sendEnd(String players);
    }
    public OnInputSelected onInputSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.dialog_end, container, false);

        delimiter = getString(R.string.delimiter_players);

        player1 = v.findViewById(R.id.player1);
        player2 = v.findViewById(R.id.player2);
        player3 = v.findViewById(R.id.player3);
        player4 = v.findViewById(R.id.player4);
        player5 = v.findViewById(R.id.player5);
        player6 = v.findViewById(R.id.player6);
        player7 = v.findViewById(R.id.player7);
        player8 = v.findViewById(R.id.player8);
        points1 = v.findViewById(R.id.points1);
        points2 = v.findViewById(R.id.points2);
        points3 = v.findViewById(R.id.points3);
        points4 = v.findViewById(R.id.points4);
        points5 = v.findViewById(R.id.points5);
        points6 = v.findViewById(R.id.points6);
        points7 = v.findViewById(R.id.points7);
        points8 = v.findViewById(R.id.points8);
        action_ok = v.findViewById(R.id.ok);
        action_cancel = v.findViewById(R.id.cancel);

        action_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        action_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _player1 = player1.getEditText().getText().toString();
                String _player2 = player2.getEditText().getText().toString();
                String _player3 = player3.getEditText().getText().toString();
                String _player4 = player4.getEditText().getText().toString();
                String _player5 = player5.getEditText().getText().toString();
                String _player6 = player6.getEditText().getText().toString();
                String _player7 = player7.getEditText().getText().toString();
                String _player8 = player8.getEditText().getText().toString();
                String _points1 = points1.getEditText().getText().toString();
                String _points2 = points2.getEditText().getText().toString();
                String _points3 = points3.getEditText().getText().toString();
                String _points4 = points4.getEditText().getText().toString();
                String _points5 = points5.getEditText().getText().toString();
                String _points6 = points6.getEditText().getText().toString();
                String _points7 = points7.getEditText().getText().toString();
                String _points8 = points8.getEditText().getText().toString();

                if((_player1.isEmpty() && !_points1.isEmpty()) || (!_player1.isEmpty() && _points1.isEmpty()) ||
                        (_player2.isEmpty() && !_points2.isEmpty()) || (!_player2.isEmpty() && _points2.isEmpty()) ||
                        (_player3.isEmpty() && !_points3.isEmpty()) || (!_player3.isEmpty() && _points3.isEmpty()) ||
                        (_player4.isEmpty() && !_points4.isEmpty()) || (!_player4.isEmpty() && _points4.isEmpty()) ||
                        (_player5.isEmpty() && !_points5.isEmpty()) || (!_player5.isEmpty() && _points5.isEmpty()) ||
                        (_player6.isEmpty() && !_points6.isEmpty()) || (!_player6.isEmpty() && _points6.isEmpty()) ||
                        (_player7.isEmpty() && !_points7.isEmpty()) || (!_player7.isEmpty() && _points7.isEmpty()) ||
                        (_player8.isEmpty() && !_points8.isEmpty()) || (!_player8.isEmpty() && _points8.isEmpty())){
                    Toast.makeText(getContext(), getString(R.string.error_wrongInput), Toast.LENGTH_SHORT).show();
                }else{
                    onInputSelected.sendEnd(String.join(delimiter,
                            _player1, _points1, _player2, _points2, _player3, _points3, _player4, _points4,
                            _player5, _points5, _player6, _points6, _player7, _points7, _player8, _points8));
                    getDialog().dismiss();
                }
            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        try{
            onInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){
            Log.e("EndDialog", "onAttach: ClassCastException : "+e.getMessage());
        }
    }
}
