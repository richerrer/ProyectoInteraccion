package com.miCash.espol.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miCash.espol.dao.TransaccionDao;
import com.miCash.espol.dao.UsuarioDao;
import com.miCash.espol.dataTime.DatePickerFragment;
import com.miCash.espol.global.GlobalClass;
import com.miCash.espol.menu.ArrayAdapterMenu;
import com.miCash.espol.menu.Item;
import com.miCash.espol.others.Cookie;
import com.miCash.espol.pojos.Transaccion;
import com.miCash.espol.pojos.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MenuPrincipal extends Activity {

    private GlobalClass globalVariable = null;
    private DrawerLayout drawerLayout;
    private Button pickDate;
    private String[] opciones;
    private ViewGroup layout;
    private ArrayList<Item> items ;
    private ProgressBar spinner;
    private ScrollView scrollView;
    private  TextView textView;
    private final SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        globalVariable = (GlobalClass) getApplicationContext();
        inicializarComponentes();
        searchTransactions(Calendar.getInstance());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        ((DatePickerFragment)newFragment).setContext(this);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void searchTransactions(Calendar calendar) {
        layout.removeAllViews();
        new GetConnection(this).execute(calendar);
    }

    private void inicializarDrawer(){
        opciones = getResources().getStringArray(R.array.options_array);
        ListView drawer = (ListView) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        items = new ArrayList<Item>();
        items.add(new Item(R.drawable.ic_action_edit,"Editar Perfil"));
        items.add(new Item(R.drawable.ic_action_paste, "Reporte"));
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
                    Intent ingresoGasto = new Intent(MenuPrincipal.this, IngresoGasto.class);
                    startActivity(ingresoGasto);
                }
                if(items.get(arg2).getTexto().equals("Nuevo Lugar Favorito")){
                    Intent registrarLugar = new Intent(MenuPrincipal.this, RegistrarLugar.class);
                    startActivity(registrarLugar);
                }
                if(items.get(arg2).getTexto().equals("Cerrar Sesión")){
                    Cookie.writeCookie("", MenuPrincipal.this);
                    globalVariable.setUsuario(null);
                    Intent iniciarSesion = new Intent(MenuPrincipal.this, Start.class);
                    iniciarSesion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(iniciarSesion);
                    finish();
                }
            }
        });
    }

    private void inicializarComponentes(){
        inicializarDrawer();
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        textView = (TextView)findViewById(R.id.text_transacciones_anteriores);
        scrollView = (ScrollView)findViewById(R.id.scroll_menu_principal);
        layout = (ViewGroup) findViewById(R.id.content);
        pickDate = (Button)findViewById(R.id.pick_date);
    }

    public void agregarTransaccionesAnteriores(ArrayList<Transaccion> transacciones){
        LayoutInflater inflater = LayoutInflater.from(this);
        int id = R.layout.layout_all_transactions;

        for(Transaccion transaction : transacciones){
            TableLayout tableLayout = (TableLayout)inflater.inflate(id,null,false);
            TextView textView = (TextView) tableLayout.findViewById(R.id.transactions_description);
            textView.setText(transaction.getDescripcion());

            textView = (TextView) tableLayout.findViewById(R.id.transactions_categoria);
            textView.setText(transaction.getCategoria());

            textView = (TextView) tableLayout.findViewById(R.id.transactions_fecha);
            textView.setText(date.format(transaction.getFecha().getTime()));

            textView = (TextView) tableLayout.findViewById(R.id.transactions_valor);
            if(transaction.isIngreso()){
                textView.setText(" + $"+transaction.getValor().toString());
                textView.setBackgroundColor(Color.GREEN);
            }
            else{
                textView.setText(" - $"+transaction.getValor().toString());
                textView.setBackgroundColor(Color.RED);
            }
            layout.addView(tableLayout);
        }
    }


    private class GetConnection extends AsyncTask<Calendar, Integer, ArrayList<Transaccion>> {
        Context context;
        public GetConnection(Context context){this.context = context;}
        @Override
        protected ArrayList<Transaccion> doInBackground(Calendar... params){
            ArrayList<Transaccion> transacciones = null;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            transacciones = TransaccionDao.getInstance().getTransactions(globalVariable.getUsuario(),params[0]);
            return transacciones;
        }

        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
            pickDate.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(ArrayList<Transaccion> transaccions){
            spinner.setVisibility(View.GONE);
            showElements();
            if(TransaccionDao.getInstance().getConection()!=null) {
                if (transaccions != null) {
                    agregarTransaccionesAnteriores(transaccions);
                } else{
                    Toast.makeText(getApplicationContext(), "No existen transacciones para la fecha seleccionada", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Error no se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        }

        public void showElements(){
            textView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            pickDate.setVisibility(View.VISIBLE);
        }

    }
}
