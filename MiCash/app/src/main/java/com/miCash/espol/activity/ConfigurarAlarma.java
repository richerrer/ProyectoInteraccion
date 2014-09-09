package com.miCash.espol.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import java.io.IOException;

import com.miCash.espol.activity.R;

public class ConfigurarAlarma extends Activity{

    Button guardar,cancelar;
    TextView timeShown;
    Spinner sonidos, precisiones;
    ToggleButton activacion;
    LinearLayout layout0;

    String sonidoSeleccionado, precisionSeleccionada;
    int hora, min;
    static final int TIME_DIALOG_ID = 999;

    public MediaPlayer mpBells, mpXperia, mpTick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_alarma);

        mpBells = MediaPlayer.create(ConfigurarAlarma.this, R.raw.bells);
        mpXperia = MediaPlayer.create(ConfigurarAlarma.this, R.raw.xperia_z_theme);
        mpTick = MediaPlayer.create(ConfigurarAlarma.this, R.raw.tick_tock);

        inicializarComponentes();
        //stopSounds();

    }

    @Override
    public void onUserInteraction(){
        super.onUserInteraction();
        sonidos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                sonidoSeleccionado = arg0.getItemAtPosition(arg2).toString();

                if (sonidoSeleccionado.equals("Bells")){stopSounds();mpBells.start();}
                if (sonidoSeleccionado.equals("Different")){stopSounds();mpXperia.start();}
                if (sonidoSeleccionado.equals("Tick Tock")){stopSounds();mpTick.start();}

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    public void stopSounds(){
        if (mpBells.isPlaying()){
            mpBells.stop();
            try {
                mpBells.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mpXperia.isPlaying()){
            mpXperia.stop();
            try {
                mpXperia.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mpTick.isPlaying()) {
            mpTick.stop();
            try {
                mpTick.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.configurar_alarma, menu);
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
        guardar = (Button) findViewById (R.id.btn_guardarAlarma);
        cancelar = (Button) findViewById (R.id.btn_cancelarAlarma);
        timeShown = (TextView) findViewById(R.id.timeShown);
        sonidos = (Spinner) findViewById(R.id.sonidos);
        precisiones = (Spinner) findViewById(R.id.precisiones);
        activacion = (ToggleButton) findViewById(R.id.btn_activacion);
        layout0 = (LinearLayout) findViewById(R.id.layout0);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sonidos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sonidos.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.precisiones, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        precisiones.setAdapter(adapter1);
        precisiones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                stopSounds();
                precisionSeleccionada = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        guardar.setOnClickListener(new EventoAlarma(this, 0));
        cancelar.setOnClickListener(new EventoAlarma(this, 1));
        timeShown.setOnClickListener(new EventoAlarma(this, 2));
        layout0.setOnClickListener(new EventoAlarma(this, 3));
        activacion.setOnClickListener(new EventoAlarma(this, 4));
        //precisiones.setOnClickListener(new EventoAlarma(this, 5));
    }

    public boolean isAM(int h){
        boolean flag;
        if (h <  12){flag = true;}
        else{flag = false;}
        return flag;
    }

    public String printedTime(int h, int m){
        String print, hora, min;
        int h1;
        if (m < 10){min = "0"+m;}
        else{min = ""+m;}

        if (h > 11){h1 = (h-12);}
        else {h1 = h;}
        if (h1 == 0 && !isAM(h)){h1 = 12;}

        if (h1 < 10){hora = "0"+h1;}
        else{hora = ""+h1;}

        if(isAM(h)){
            print = hora+" : "+min+" AM";
        }
        else {
            print = hora+" : "+min+" PM";
        }
        return print;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(ConfigurarAlarma.this, timePickerListener, hora, min,false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                    hora = selectedHour;
                    min = selectedMinute;

                    // set current time into textview
                    timeShown.setText(printedTime(hora, min));
                }
            };

    private class EventoAlarma implements View.OnClickListener {

        private Context contexto;
        private int opcion;

        public EventoAlarma(Context contexto, int opcion) {
            super();
            this.contexto = contexto;
            this.opcion = opcion;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (this.opcion){
                case 0:{
                    //new GetConnection(contexto).execute(username.getText().toString(),password.getText().toString());
                    stopSounds();
                    break;
                }
                case 1:{
                    //Intent registrarse = new Intent(this.contexto, Registrarse.class);
                    //this.contexto.startActivity(registrarse);
                    stopSounds();
                    break;
                }
                case 2:{
                    stopSounds();
                    showDialog(TIME_DIALOG_ID);
                    break;
                }
                case 3:{
                    stopSounds();
                    break;
                }
                case 4:{
                    stopSounds();
                    break;
                }
            }
        }

    }
}
