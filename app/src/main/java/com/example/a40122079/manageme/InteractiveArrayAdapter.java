package com.example.a40122079.manageme;

/**
 * Created by 40122079 on 31/03/2016.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class InteractiveArrayAdapter extends ArrayAdapter<Model> {
    String filename = "myfile";
    String string = "Hello world!";
    FileOutputStream outputStream;

    private final List<Model> list;
    private final Activity context;
    public ArrayList<String>selected_items = new ArrayList<String>(); //array for saving the chosen habits


    public InteractiveArrayAdapter(Activity context, List<Model> list) {
        super(context, R.layout.activity_health, list);
        this.context = context;
        this.list = list;


    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
        protected Button save;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.simplerow, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.label);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);

            viewHolder.save = (Button)view.findViewById(R.id.saveSelection);
            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                      //  selected_items.add(habitList.get(position));

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            Model element = (Model) viewHolder.checkbox
                                    .getTag();
                            element.setSelected(buttonView.isChecked());

                        }
                    });



            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));

        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getName());
        holder.checkbox.setChecked(list.get(position).isSelected());
        selected_items.add(Model.class.getName()); //saving the new array of selection
        return view;

    }

    public void writeToFile(String data) {
        try {
          //  if (Model.class.getMethods()){}

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }






}