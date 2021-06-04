package com.example.appausa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appausa.R;
import com.example.appausa.model.Cuenta;
import com.example.appausa.model.Usuario;

import java.util.List;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {
    public UsuarioAdapter(Context context, List<Usuario> objects) {
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
                    R.layout.list_item_usuario,
                    parent,
                    false);
        }

        // Referencias UI.
        ImageView avatar = (ImageView) convertView.findViewById(R.id.tipousuario);
        TextView cc = (TextView) convertView.findViewById(R.id.documento);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombremp);
        TextView telefono = (TextView) convertView.findViewById(R.id.telefono);
        TextView email = (TextView) convertView.findViewById(R.id.email);

        // Lead actual.
        Usuario lead = getItem(position);

        switch (lead.getRol()) {
            case "ADMINISTRADOR":
                avatar.setImageResource(R.drawable.admon_icon);
                break;
            case "EMPLEADO":
                avatar.setImageResource(R.drawable.pworker_icon_24);
                break;
            case "SUPER":
                avatar.setImageResource(R.drawable.super_icon);
                break;
            case "TALENTO HUMANO":
                avatar.setImageResource(R.drawable.th_icon);
                break;
        }
        switch (lead.getTipoDoc()) {
            case "Cedula Extranjeria":
                cc.setText("CE. " + lead.getCc());
                break;
            case "Cedula Ciudadania":
                cc.setText("CC. " + lead.getCc());
                break;
        }
        nombre.setText(lead.getSnombre().equals(null) ? lead.getPnombre() + " " + lead.getPapellido() + " " + lead.getSapellido() : lead.getPnombre() + " " + lead.getSnombre() + " " + lead.getPapellido() + " " + lead.getSapellido());

        telefono.setText(lead.getTelefono());
        email.setText(lead.getCorreoelectronico());
        return convertView;
    }
}
