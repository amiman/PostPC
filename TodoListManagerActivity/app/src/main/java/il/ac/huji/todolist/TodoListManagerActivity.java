package il.ac.huji.todolist;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class TodoListManagerActivity extends ActionBarActivity {

    private ArrayList<ToDoItem> mToDoList;
    private ToDoListAdapter mAdapter;
    private ListView mToDoListView;

    static final String EMPTY_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        // Create the array list for the items
        this.mToDoList = new ArrayList<ToDoItem>();

        // Connect the list view to the arrayList
        this.mAdapter = new ToDoListAdapter(this.getApplicationContext(),R.id.list_item,mToDoList);
        mToDoListView = (ListView) findViewById(R.id.lstTodoItems);
        mToDoListView.setAdapter(mAdapter);

        // Add on click lisetner to the list items
        mToDoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                // Add a dialog
                final Dialog inviteBuilder = new Dialog(parent.getContext());
                inviteBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View bodyView = inflater.inflate(R.layout.list_todo_dialog, null);
                ((TextView) bodyView.findViewById(R.id.itemDialogTitle)).setText(mToDoList.get(position).GetText());
                Button deleteButton = (Button) bodyView.findViewById(R.id.itemBtnDialogDelete);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // First: delete item from list
                        mToDoList.remove(position);
                        // Second: redraw list
                        mAdapter.notifyDataSetChanged();
                    }
                });
                inviteBuilder.setContentView(bodyView);
                inviteBuilder.show();
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.add) {
            // Add a new item to the list

            // First: get the text from the text view
            TextView newItemTextView = (TextView) findViewById(R.id.edtNewItem);
            String newItemText = newItemTextView.getText().toString();

            // Second: check that this is not an empty string and if no add the item to list.
            if(newItemText != EMPTY_STRING) {
                this.mToDoList.add(new ToDoItem(newItemText));

                // Third: redraw the list
                mAdapter.notifyDataSetChanged();

                // Forth: delete the current text from the text view
                newItemTextView.setText(EMPTY_STRING);
            }


        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
