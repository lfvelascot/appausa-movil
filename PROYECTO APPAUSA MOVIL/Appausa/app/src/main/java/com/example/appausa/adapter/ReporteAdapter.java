package com.example.appausa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appausa.R;
import com.example.appausa.model.Reporte;

import java.util.List;

public class ReporteAdapter extends ArrayAdapter<Reporte> {
    public ReporteAdapter(Context context, List<Reporte> objects) {
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
                    R.layout.list_item_reporte,
                    parent,
                    false);
        }

        // Referencias UI.
        ImageView avatar = (ImageView) convertView.findViewById(R.id.estado);
        TextView id_emp = (TextView) convertView.findViewById(R.id.id_empleado);
        TextView fecha = (TextView) convertView.findViewById(R.id.fecha);
        TextView tipo = (TextView) convertView.findViewById(R.id.tipo_reporte);
        TextView id_r = (TextView) convertView.findViewById(R.id.id_reporte);

        // Lead actual.
        Reporte lead = getItem(position);

        // Setup. 1. Aceptado 2. Rechazado 3. En Espera
        if (lead.getEstado().equals("Aceptado")){
            avatar.setImageResource(R.drawable.check_r);
        } else if (lead.getEstado().equals("Negado")){
            avatar.setImageResource(R.drawable.denied_r);
        } else if (lead.getEstado().equals("En espera")){
            avatar.setImageResource(R.drawable.wait_r);
        }
        id_emp.setText(lead.getEmpleado());
        fecha.setText(lead.getFecha());
        tipo.setText(lead.getTipo());
        id_r.setText(lead.getId());
        return convertView;
    }
}