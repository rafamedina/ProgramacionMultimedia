package com.example.calculadorarafa;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;


public class ToolbarFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private int seekvalue;

    private EditText editText;


    public  interface ToolbarListener{
        public void onButtonClick(int size, String text);
    }

    ToolbarListener activityCallBack;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_toolbar, container, false);

        editText = v.findViewById(R.id.FragmentEditText);

        SeekBar seekBar = v.findViewById(R.id.seekBar);

        Button button = v.findViewById(R.id.Fragmentbutton);

        seekBar.setOnSeekBarChangeListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonClicked(v);
            }
        });




        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            activityCallBack = (ToolbarListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "Oye que la lias");
        }


    }


    public void ButtonClicked(View v){

        activityCallBack.onButtonClick(seekvalue,editText.getText().toString());



    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    seekvalue = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}