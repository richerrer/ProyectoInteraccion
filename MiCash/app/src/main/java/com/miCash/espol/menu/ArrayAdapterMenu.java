package com.miCash.espol.menu;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miCash.espol.activity.R;

import java.util.ArrayList;

/**
 * Created by usuario on 30/08/2014.
 */
public class ArrayAdapterMenu extends ArrayAdapter<Item> {

    Activity context;
    ArrayList<Item> items;

    public ArrayAdapterMenu(Activity context, ArrayList<Item> items){
        super(context,R.layout.layout_menu,items);
        this.context = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View item = convertView;
        item = context.getLayoutInflater().inflate(R.layout.layout_menu, null);
        Item i = items.get(position);
        ImageView img = (ImageView)item.findViewById(R.id.imagen);
        TextView texto = (TextView) item.findViewById(R.id.texto);

        img.setImageDrawable(context.getResources().getDrawable(i.getIdImage()));
        texto.setText(i.getTexto());
        return item;
    }
}
