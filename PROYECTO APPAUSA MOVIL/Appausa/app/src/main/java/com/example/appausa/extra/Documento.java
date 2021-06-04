package com.example.appausa.extra;

import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appausa.R;
import com.github.barteksc.pdfviewer.PDFView;

public class Documento extends AppCompatActivity {

    ProgressBar progressBar;
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navegador2);
        pdfView = findViewById(R.id.pdf_viewer);
        progressBar = findViewById(R.id.progressBar);
        String urlPdf = "http://192.168.0.13:80/appausamovil/documento.pdf";
        new RecibirPDFStream(pdfView,progressBar).execute(urlPdf);
    }
}