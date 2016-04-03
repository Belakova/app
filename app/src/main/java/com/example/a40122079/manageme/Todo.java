package com.example.a40122079.manageme;



import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;




public class Todo extends Activity {
    public static final String APP_TAG = "yay";
    private ListView taskView;
    private Button btNewTask;
    private EditText etNewTask;
    private TodoProvider provider;

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_todo);
        provider = new TodoProvider(this);
        taskView = (ListView) findViewById(R.id.taskList);
        btNewTask = (Button) findViewById(R.id.btNewTask);
        etNewTask = (EditText) findViewById(R.id.etNewTask);
        btNewTask.setOnClickListener(handleNewTaskEvent);
        renderTodo();
    }

    private OnClickListener handleNewTaskEvent = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(APP_TAG, "add task click received");
            provider.addTask(etNewTask.getText().toString());
            renderTodo();
        }
    };

    /**
     * renders the task list
     */
    private void renderTodo() {
        List<String> beans = provider.findAll();
        if (!beans.isEmpty()) {
            Log.d(APP_TAG, String.format("%d beans found", beans.size()));
            // render the list
            taskView.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, beans.toArray(new String[]{})));

            // dumb item deletion onclick
            taskView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Log.d(APP_TAG, String.format(
                            "item with id: %d and position: %d", id, position));
                    TextView v = (TextView) view;
                    provider.deleteTask(v.getText().toString());

                    renderTodo();
                }
            });
        } else {
            Log.d(APP_TAG, "no tasks found");
        }
    }
}
