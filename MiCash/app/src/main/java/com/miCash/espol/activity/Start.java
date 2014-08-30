package com.miCash.espol.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miCash.espol.dao.UsuarioDao;
import com.miCash.espol.global.GlobalClass;
import com.miCash.espol.others.Cookie;
import com.miCash.espol.pojos.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Start extends Activity {
    Button iniciarSesion,registrarse;
    EditText username;
    EditText password;
    ProgressBar spinner;
    LinearLayout layout1,layout2;
    ImageView imagenPrincipal;
    GlobalClass globalVariable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        inicializarComponentes();
        globalVariable = (GlobalClass) getApplicationContext();
        if(globalVariable.getUsername()!=null){
            Intent menuPrincipal = new Intent(this,MenuPrincipal.class);
            this.startActivity(menuPrincipal);
            finish();
        }else{
            String usernameFromCookie = Cookie.readCookie(this);
            if(usernameFromCookie!=null){
                new GetConnection(this).execute(usernameFromCookie,null);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
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

    private void inicializarComponentes(){
        iniciarSesion = (Button) findViewById (R.id.btn_iniciarSesion );
        registrarse = (Button) findViewById (R.id.btn_registrarse );
        iniciarSesion.setOnClickListener(new EventoMenuPrincipal(this, 0));
        registrarse.setOnClickListener(new EventoMenuPrincipal(this, 1));
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        layout1 = (LinearLayout)findViewById(R.id.layout1);
        layout2 = (LinearLayout)findViewById(R.id.layout2);
        imagenPrincipal = (ImageView)findViewById(R.id.imagePrincipal);
    }



    private class GetConnection extends AsyncTask<String, Integer, Usuario> {
        Context context;
        public GetConnection(Context context){this.context = context;}
        @Override
        protected Usuario doInBackground(String... params){
            Usuario usuario = null;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(params[1]!=null){//Si el ingreso del usuario se lo hace por medio del boton ingresar
                usuario = UsuarioDao.getInstance().getUsuario(params[0],params[1]);
            }
            else {//Si el logeo del usuario se lo hace por cookie
                usuario = UsuarioDao.getInstance().getUsuario(params[0]);
            }
            globalVariable.setUsername(usuario);
            return usuario;
        }

        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            imagenPrincipal.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Usuario usuario){
            spinner.setVisibility(View.GONE);
            if(UsuarioDao.getInstance().getConection()!=null) {
                if (usuario != null) {
                    Cookie.writeCookie(usuario.getUsername(),Start.this);
                    Intent menuPrincipal = new Intent(context, MenuPrincipal.class);
                    menuPrincipal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(menuPrincipal);
                    finish();
                } else{
                    Cookie.writeCookie("",Start.this);
                    Toast.makeText(getApplicationContext(), "Error usuario o contraseña incorrectas", Toast.LENGTH_SHORT).show();
                    showElements();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Error no se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                showElements();
            }

        }

        public void showElements(){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            imagenPrincipal.setVisibility(View.VISIBLE);
        }

    }

    private class EventoMenuPrincipal implements View.OnClickListener {

        private Context contexto;
        private int opcion;

        public EventoMenuPrincipal(Context contexto, int opcion) {
            super();
            this.contexto = contexto;
            this.opcion = opcion;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (this.opcion){
                case 0:{
                    new GetConnection(contexto).execute(username.getText().toString(),password.getText().toString());
                    break;
                }
                case 1:{
                    Intent registrarse = new Intent(this.contexto, Registrarse.class);
                    this.contexto.startActivity(registrarse);
                    break;
                }
            }
        }

    }

}
