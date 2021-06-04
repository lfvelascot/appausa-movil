package com.example.appausa.extra;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appausa.R;

public class Informacion  extends Fragment{

    private View l;

    public static Informacion newInstance() {
        Informacion frag = new Informacion();
        Bundle args = new Bundle();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.sobrelaapp, container, false);
        return l;
    }
}
