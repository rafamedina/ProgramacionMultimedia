package com.example.calculadorarafa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class TextFragment extends Fragment {

    private TextView textView;
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_text, container, false);

        textView = v.findViewById(R.id.fragmentTextView);
        image = v.findViewById(R.id.fragmentImage);





        return v;
    }


//    public void ChangeTextProperties(int fontsize, String text) {
//        if (textView != null) {
//            textView.setTextSize(fontsize);
//            textView.setText(text);
//        }
//    }
    public void ChangeImageAlpha(int alpha){
            int t_value = alpha * 255 / 100;
            image.setImageAlpha(t_value);
        }


    }
