package com.example.appausa.extra;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appausa.R;

public class Navegador extends Fragment {

    WebView sitio;
    TextView t1;
    private static  String url;
    private View l;

    public Navegador(String s) {
        this.url = s;
    }

    public static Navegador newInstance(String u) {
        Navegador frag = new Navegador(u);
        Bundle args = new Bundle();
        url = u;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.navegador, container, false);
        sitio = l.findViewById(R.id.webvview);
        sitio.setWebChromeClient(new WebChromeClient());
        sitio.setWebViewClient(new ViewClient());
        sitio.getSettings().setJavaScriptEnabled(true);
        sitio.getSettings().setLoadWithOverviewMode(true);
        sitio.getSettings().setUseWideViewPort(true);
        sitio.loadUrl(url);
        return l;
    }

}

