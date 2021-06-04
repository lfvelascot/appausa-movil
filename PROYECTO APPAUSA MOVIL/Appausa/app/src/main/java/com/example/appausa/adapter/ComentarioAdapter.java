package com.example.appausa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appausa.R;
import com.example.appausa.model.Comentario;

import java.util.List;

public class ComentarioAdapter extends ArrayAdapter<Comentario> {
    public ComentarioAdapter(Context context, List<Comentario> objects) {
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
                    R.layout.list_item_comentario,
                    parent,
                    false);
        }

        // Referencias UI.
        ImageView avatar = (ImageView) convertView.findViewById(R.id.estado);
        TextView fecha = (TextView) convertView.findViewById(R.id.fecha);
        TextView comentario = (TextView) convertView.findViewById(R.id.contenido);
        TextView id_emp = (TextView) convertView.findViewById(R.id.id_empleado);
        TextView id = (TextView) convertView.findViewById(R.id.id);

        // Lead actual.
        Comentario lead = getItem(position);
        id.setText(lead.getId());
        avatar.setImageResource(R.drawable.comment);
        id_emp.setText(lead.getCuenta());
        fecha.setText(lead.getFecha());
        comentario.setText(lead.getContenido());
        return convertView;
    }
}