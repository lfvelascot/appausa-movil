package com.example.appausa.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appausa.R;


public class HomeContentFragment extends Fragment {

  public static String user,lastlog;

  public static HomeContentFragment newInstance(String u, String lastloging) {
    HomeContentFragment frag = new HomeContentFragment();
    Bundle args = new Bundle();
    user = u;
    lastlog = lastloging;
    return frag;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View layout = inflater.inflate(R.layout.home_fragment, container, false);
    TextView tu = (TextView) layout.findViewById(R.id.userName),lu = (TextView) layout.findViewById(R.id.lastLogin);
    tu.setText(user);
    if (lastlog.isEmpty()){
      lu.setText("Por favor cambie su usuario y contraseña");
    } else {
      lu.setText(lastlog);
    }

    return layout;
  }
}

