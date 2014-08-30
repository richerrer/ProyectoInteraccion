package com.miCash.espol.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miCash.espol.dao.UsuarioDao;
import com.miCash.espol.pojos.Usuario;

public class Registrarse extends Activity {

    EditText email,username,password,confirmPassword;
    Button registrar;
    ProgressBar spinner;
    LinearLayout layout3,layout4,layout5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        email = (EditText)findViewById(R.id.txt_email);
        username = (EditText)findViewById(R.id.txt_username);
        password = (EditText)findViewById(R.id.txt_password);
        confirmPassword = (EditText)findViewById(R.id.txt_passwordAgain);
        registrar = (Button)findViewById(R.id.confirmarRegistro);
        registrar.setOnClickListener(new EventoMenuPrincipal(this, 0));
        spinner = (ProgressBar)findViewById(R.id.progressBarRegistrar);
        layout3 = (LinearLayout)findViewById(R.id.layout3);
        layout4 = (LinearLayout)findViewById(R.id.layout4);
        layout5 = (LinearLayout)findViewById(R.id.layout5);
    }

    public boolean fillData(){
        View focusView = null;
        boolean cancelar = false;
        // Check for a valid password.
        if (TextUtils.isEmpty(username.getText().toString())) {
            username.setError("Se requiere llenar este campo");
            focusView = username;
            cancelar = true;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Se requiere llenar este campo");
            focusView = password;
            cancelar = true;
        }
        if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
            confirmPassword.setError("Se requiere llenar este campo");
            focusView = confirmPassword;
            cancelar = true;
        }
        return cancelar;
    }

    public boolean verifyPassword(){
        View focusView = null;
        boolean cancelar = false;
        if(!password.getText().toString().equals(confirmPassword.getText().toString())){
            password.setError("Las contraseñas no coinciden");
            password.setText("");
            confirmPassword.setError("Las contraseñas no coinciden");
            confirmPassword.setText("");
            cancelar = true;
        }
        return cancelar;
    }

    private class GetConnection extends AsyncTask<String, Integer, Boolean> {
        Context context;
        public GetConnection(Context context){this.context = context;}

        @Override
        protected Boolean doInBackground(String... params){
            Boolean insertion = false;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            insertion = UsuarioDao.getInstance().addUsuario(params[0],params[1]);
            return insertion;
        }

        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.GONE);
            layout5.setVisibility(View.GONE);
            registrar.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Boolean insertion){

            spinner.setVisibility(View.GONE);
            layout3.setVisibility(View.VISIBLE);
            layout4.setVisibility(View.VISIBLE);
            layout5.setVisibility(View.VISIBLE);
            registrar.setVisibility(View.VISIBLE);
            if(UsuarioDao.getInstance().getConection()!=null) {
                if (insertion) {
                    Toast.makeText(getApplicationContext(), "El usuario se agregó correctamente", Toast.LENGTH_SHORT).show();
                    Intent start = new Intent(context,Start.class);
                    start.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(start);
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "Error el usuario ya existe", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(getApplicationContext(), "Error no se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();

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
                    boolean cancelar = false;
                    if(fillData()) cancelar = true;
                    if(verifyPassword())    cancelar = true;
                    if(!cancelar) new GetConnection(contexto).execute(username.getText().toString(),password.getText().toString());
                    break;
                }

            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registrarse, menu);
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
}
