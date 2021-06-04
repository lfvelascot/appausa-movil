package com.example.appausa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appausa.R;
import com.example.appausa.model.Historial;
import com.example.appausa.model.Reporte;

import java.util.List;

public class HistorialAdapter extends ArrayAdapter<Historial> {

    public HistorialAdapter(Context context, List<Historial> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_reporte_historial,
                    parent,
                    false);
        }

        // Referencias UI.
        ImageView avatar = (ImageView) convertView.findViewById(R.id.estadoact);
        TextView cuenta = (TextView) convertView.findViewById(R.id.cuentah);
        TextView fecha = (TextView) convertView.findViewById(R.id.fecha);
        TextView tipo = (TextView) convertView.findViewById(R.id.estadoprevio);
        TextView id_r = (TextView) convertView.findViewById(R.id.id_reporte);

        // Lead actual.
        Historial lead = getItem(position);

        // Setup. 1. Aceptado 2. Rechazado 3. En Espera
        switch (lead.getEstado_a()) {
            case "Aceptado":
                avatar.setImageResource(R.drawable.check_r);
                break;
            case "Negado":
                avatar.setImageResource(R.drawable.denied_r);
                break;
            case "En espera":
                avatar.setImageResource(R.drawable.wait_r);
                break;
        }
        cuenta.setText(lead.getCuenta());
        fecha.setText(lead.getFecha());
        tipo.setText(lead.getEstado_p());
        return convertView;
    }
}
