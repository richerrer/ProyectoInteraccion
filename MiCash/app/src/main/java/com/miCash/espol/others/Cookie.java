package com.miCash.espol.others;

import android.app.Activity;
import android.provider.ContactsContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by usuario on 29/08/2014.
 */
public class Cookie {

    private static String File ="user.txt";

    public static String readCookie(Activity activity){
        try {
            InputStreamReader archivo = new InputStreamReader(activity.openFileInput(File));
            BufferedReader br = new BufferedReader(archivo);
            String texto = br.readLine();
            return texto;
        }catch (Exception e) {
            return null;
        }
    }

    public static boolean writeCookie(String username,Activity activity){
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(activity.openFileOutput(File, Activity.MODE_PRIVATE));
            archivo.write(username);
            archivo.flush();
            archivo.close();
            return true;

        } catch (IOException e) {
            return false;
        }
    }
}
