package com.example.appausa.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.appausa.Listados.VerComentarios;
import com.example.appausa.Listados.VerCuentas;
import com.example.appausa.Listados.VerReportes;
import com.example.appausa.Listados.VerReportesEnviados;
import com.example.appausa.Listados.VerReportesRecibidos;
import com.example.appausa.Listados.VerUsuarios;
import com.example.appausa.R;
import com.example.appausa.actializaciones.CambiarEstadoCuenta;
import com.example.appausa.actializaciones.CambiarDatosUsuario;
import com.example.appausa.actializaciones.ModificarComentario;
import com.example.appausa.actializaciones.ModificarCuenta;
import com.example.appausa.actializaciones.ModificarReporte;
import com.example.appausa.actializaciones.ModificarUsuario;
import com.example.appausa.adiciones.AnadirCuenta;
import com.example.appausa.adiciones.EnviarComentario;
import com.example.appausa.adiciones.EnviarReporte;
import com.example.appausa.eliminacion.EliminarComentario;
import com.example.appausa.eliminacion.EliminarCuenta;
import com.example.appausa.eliminacion.EliminarReporte;
import com.example.appausa.eliminacion.EliminarUsuario;
import com.example.appausa.extra.Informacion;
import com.example.appausa.extra.Navegador;
import com.example.appausa.model.Log;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;
    private String username, n,rol_asociado,doc,lu;
    public Intent intent;
    private Log log = new Log();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        intent = getIntent();
        username = intent.getStringExtra("user");
        rol_asociado = intent.getStringExtra("rol");
        doc = intent.getStringExtra("doc");
        n = intent.getStringExtra("n");
        lu = intent.getStringExtra("ul");
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);
        drawerLayout.addDrawerListener(this);
        onCreateOptionsMenu(navigationView.getMenu(),rol_asociado);
        View header = navigationView.getHeaderView(0);
        TextView tn = header.findViewById(R.id.userNameRol);
        tn.setText(n);
        header.findViewById(R.id.header_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        Fragment fragment = HomeContentFragment.newInstance(username,lu);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                salir();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    public void salir(){
        AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
        a.setMessage("¿Desea finalizar sesion?");
        a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                log.insertlog(username,"Fin Sesión","Finalizo sesion normalmente","http://192.168.0.13:80/appausamovil/insertarlog.php",getApplicationContext());
                intent.putExtra("mensaje","Sesión finalizada");
                startActivity(intent);
            }
        });
        AlertDialog r = a.create();
        r.setTitle("Alerta");
        r.show();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.inicio:
                fragment = HomeContentFragment.newInstance(username,lu);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .commit();
                setTitle("Inicio");
                break;
            case R.id.datosuser:
                fragment = CambiarDatosUsuario.newInstance(username,doc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Datos de Usuario");
                break;
            /// Empleados
            case R.id.verusuarios:
                fragment = VerUsuarios.newInstance(username,doc,true);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Usuarios");
                break;
            case R.id.verempleados:
                fragment = VerUsuarios.newInstance(username,doc,false);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Empleados");
                break;
            case R.id.modusuario:
                fragment = ModificarUsuario.newInstance(username);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Modificar Datos Usuario");
                break;
            case R.id.eliusuario:
                fragment = EliminarUsuario.newInstance(username,doc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Eliminar Datos Usuario");
                break;
            case R.id.anadircuenta:
                fragment = AnadirCuenta.newInstance(username);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Añadir Cuenta");
                break;
            case R.id.vercuentas:
                fragment = VerCuentas.newInstance(username,doc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Ver Cuentas");
                break;
            case R.id.modcuenta:
                fragment = ModificarCuenta.newInstance(username);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Modificar Datos Cuenta");
                break;
            case R.id.elicuenta:
                fragment = EliminarCuenta.newInstance(username);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .commit();
                setTitle("Eliminar Datos Cuenta");
                break;
            case R.id.cambiarestadocuenta:
                fragment = CambiarEstadoCuenta.newInstance(username);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Cambiar Estado Cuenta");
                break;
            case R.id.verReportes:
                fragment = VerReportes.newInstance(username,doc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Ver Reportes");
                break;
            case R.id.ModReporte:
                fragment = ModificarReporte.newInstance(username,doc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Modificar Reporte");
                break;
            case R.id.EliminarReporte:
                fragment = EliminarReporte.newInstance(username,doc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Eliminar Reporte");
                break;
            case R.id.verReportesEnv:
                fragment = VerReportesEnviados.newInstance(username,doc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Reportes Enviados");
                break;

            case R.id.verReportesRec:
                fragment = VerReportesRecibidos.newInstance(username,doc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Reportes Recibidos");
                break;
            case R.id.verAyuda:
                fragment = Navegador.newInstance("file:///android_asset/help.html");
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Ayuda");
                break;
            case R.id.sobreAppausa:
                fragment = Informacion.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Sobre Appausa");
                break;
            case R.id.enviarComentario:
                fragment = EnviarComentario.newInstance(username);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Enviar comentario");
                break;
            case R.id.verComentarios:
                fragment = VerComentarios.newInstance(username,doc,lu);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Comentarios");
                break;
            case R.id.modComentario:
                fragment = ModificarComentario.newInstance(username);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Modificar Comentario");
                break;
            case R.id.eliComentario:
                fragment = EliminarComentario.newInstance(username);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Eliminar Comentario");
                break;
            case R.id.enviarReporte:
                fragment = EnviarReporte.newInstance(username,doc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                        .replace(R.id.home_content, fragment)
                        .addToBackStack(null)
                        .commit();
                setTitle("Enviar Reporte");
                break;
            case R.id.logOut:
                salir();
                break;
            default:
                throw new IllegalArgumentException("menu option not implemented!!");
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public boolean onCreateOptionsMenu(Menu menu, String rol) {
        if (rol.equals("TALENTO HUMANO")) {
            getMenuInflater().inflate(R.menu.th_menu, menu);
            return true;
        } else if (rol.equals("EMPLEADO")) {
            getMenuInflater().inflate(R.menu.empleados_menu, menu);
            return true;
        } else if (rol.equals("ADMINISTRADOR")){
            getMenuInflater().inflate(R.menu.admon_menu, menu);
            return true;
        }
        return true;
    }


    @Override
    public void onDrawerSlide(@NonNull View view, float v) {
        //cambio en la posición del drawer
    }

    @Override
    public void onDrawerOpened(@NonNull View view) {
        //el drawer se ha abierto completamente
    }

    @Override
    public void onDrawerClosed(@NonNull View view) {
        //el drawer se ha cerrado completamente
    }

    @Override
    public void onDrawerStateChanged(int i) {
        //cambio de estado, puede ser STATE_IDLE, STATE_DRAGGING or STATE_SETTLING
    }

}
