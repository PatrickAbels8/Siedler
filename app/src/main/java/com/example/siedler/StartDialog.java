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

public class StartDialog extends DialogFragment {
    private Button action_ok;
    private Button action_cancel;

    public interface OnInputSelected{
        void sendStart();
    }
    public OnInputSelected onInputSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.dialog_start, container, false);

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
                onInputSelected.sendStart();
                getDialog().dismiss();
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
            Log.e("StartDialog", "onAttach: ClassCastException : "+e.getMessage());
        }
    }
}
