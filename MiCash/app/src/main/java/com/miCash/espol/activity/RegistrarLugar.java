package com.miCash.espol.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.miCash.espol.activity.R;
import com.miCash.espol.dao.LugarDao;
import com.miCash.espol.dao.TransaccionDao;
import com.miCash.espol.global.GlobalClass;
import com.miCash.espol.menu.ArrayAdapterMenu;
import com.miCash.espol.menu.Item;
import com.miCash.espol.others.Cookie;
import com.miCash.espol.pojos.Lugar;
import com.miCash.espol.pojos.Transaccion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

public class RegistrarLugar extends Activity {

    private GlobalClass globalVariable = null;
    private DrawerLayout drawerLayout;
    private String[] opciones;
    private ArrayList<Item> items;
    private Spinner categorias;
    private EditText nombre;
    private Button aceptar, cancelar;
    private String categoriaSeleccionada;
    private LinearLayout layoutPrincipal;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_lugar);
        inicializarComponentes();
        globalVariable = (GlobalClass) getApplicationContext();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registrar_lugar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    private void inicializarComponentes() {
        inicializarDrawer();
        layoutPrincipal = (LinearLayout) findViewById(R.id.layoutRegistrarLugar);
        progressBar = (ProgressBar) findViewById(R.id.progressBarl);
        nombre = (EditText) findViewById(R.id.nombre_lugar);
        aceptar = (Button) findViewById(R.id.guardarTransaccionl);
        cancelar = (Button) findViewById(R.id.cancelarTransaccionl);
        categorias = (Spinner) findViewById(R.id.categoriasl);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(adapter);
        categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                categoriaSeleccionada = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void inicializarDrawer(){
        opciones = getResources().getStringArray(R.array.options_array);
        ListView drawer = (ListView) findViewById(R.id.drawer_lugar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_lugar);
        items = new ArrayList<Item>();
        items.add(new Item(R.drawable.ic_action_edit,"Editar Perfil"));
        items.add(new Item(R.drawable.ic_action_paste,"Reporte"));
        items.add(new Item(R.drawable.ic_action_new,"Añadir Ingreso/Gasto"));
        //items.add(new Item(R.drawable.ic,"Proyección"));
        items.add(new Item(R.drawable.ic_action_alarms,"Configurar Alarma"));
        items.add(new Item(R.drawable.ic_action_location_searching,"Nuevo Lugar Favorito"));
        items.add(new Item(R.drawable.ic_action_favorite,"Ver Lugares Favoritos"));
        items.add(new Item(R.drawable.ic_action_secure,"Cerrar Sesión"));
        ArrayAdapterMenu adapter = new ArrayAdapterMenu(this,items);
        drawer.setAdapter(adapter);
        //drawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,opciones));
        drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                drawerLayout.closeDrawers();
                if(items.get(arg2).getTexto().equals("Añadir Ingreso/Gasto")){
                    Intent ingresoGasto = new Intent(RegistrarLugar.this, IngresoGasto.class);
                    startActivity(ingresoGasto);
                }
                if(items.get(arg2).getTexto().equals("Nuevo Lugar Favorito")){
                    Intent registrarLugar = new Intent(RegistrarLugar.this, RegistrarLugar.class);
                    startActivity(registrarLugar);
                }
                if(items.get(arg2).getTexto().equals("Cerrar Sesión")){
                    Cookie.writeCookie("", RegistrarLugar.this);
                    globalVariable.setUsuario(null);
                    Intent iniciarSesion = new Intent(RegistrarLugar.this, Start.class);
                    iniciarSesion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(iniciarSesion);
                    finish();
                }
            }
        });
    }

    public void guardarTransaccionl(View v){
        if(!fillData())
            new GetConnection(this).execute();
    }


    public boolean fillData(){
        View focusView = null;
        boolean cancelar = false;
        // Check for a valid password.
        if (TextUtils.isEmpty(nombre.getText().toString())) {
            nombre.setError("Se requiere llenar este campo");
            focusView = nombre;
            cancelar = true;
        }
        return cancelar;
    }



    private class GetConnection extends AsyncTask<String, Integer, Boolean> {
        Context context;
        public GetConnection(Context context){this.context = context;}
        @Override
        protected Boolean doInBackground(String... params){
            boolean lugares = false;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Lugar l = new Lugar(nombre.getText().toString(),categoriaSeleccionada,1,1,1);
            lugares = LugarDao.getInstance().addLugar(l);
            return  lugares;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            layoutPrincipal.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Boolean lugares){
            progressBar.setVisibility(View.GONE);
            showElements();
            if(LugarDao.getInstance().getConection()!=null) {
                if ( lugares) {
                    Toast.makeText(getApplicationContext(), "Correcto", Toast.LENGTH_SHORT).show();
                    Intent start = new Intent(context,MenuPrincipal.class);
                    start.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(start);
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), "No correcto", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Error no se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        }

        public void showElements(){
            layoutPrincipal.setVisibility(View.VISIBLE);
        }

    }

}
