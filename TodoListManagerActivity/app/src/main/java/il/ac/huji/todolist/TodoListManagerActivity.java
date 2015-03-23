package il.ac.huji.todolist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TodoListManagerActivity extends ActionBarActivity {

    private ArrayList<ToDoItem> mToDoList;
    private ToDoListAdapter mAdapter;
    private ListView mToDoListView;
    static final String EMPTY_STRING = "";
    static final String DELETE_ITEM = "Delete Item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        // Create the array list for the items
        this.mToDoList = new ArrayList<ToDoItem>();

        // Connect the list view to the arrayList
        this.mAdapter = new ToDoListAdapter(this.getApplicationContext(), R.id.list_item, mToDoList);
        mToDoListView = (ListView) findViewById(R.id.lstTodoItems);
        mToDoListView.setAdapter(mAdapter);

        registerForContextMenu(mToDoListView);

        // Add on click lisetner to the list items
        /*mToDoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                // Add a dialog
                final Dialog inviteBuilder = new Dialog(parent.getContext());
                inviteBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View bodyView = inflater.inflate(R.layout.list_todo_dialog, null);
                final String itemText = mToDoList.get(position).GetText();
                ((TextView) bodyView.findViewById(R.id.itemDialogTitle)).setText(itemText);
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

                // check if the title is calling someone
                final String[]  itemTextPart = itemText.split(" ");
                if (itemTextPart.length == 2 && itemTextPart[0].equals("call")) {
                    // it is a call itemToDo create another button for calling
                    LinearLayout lay = (LinearLayout) bodyView.findViewById(R.id.dialogToDoList);
                    Button callButton = new Button(getApplicationContext());
                    callButton.setText(itemText);
                    lay.addView(callButton);
                    callButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+itemTextPart[1]));
                            startActivity(dial);
                        }
                    });

                }

                inviteBuilder.setContentView(bodyView);
                inviteBuilder.show();
                return false;
            }

            ;
        });*/
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.add(0, 0, 0, DELETE_ITEM);
        String itemText = mToDoList.get(info.position).GetText();
        //Date itemDate = mToDoList.get(info.position).GetDate();

        menu.setHeaderTitle(itemText);
        // check if the title is calling someone
        final String[]  itemTextPart = itemText.split(" ");
        if (itemTextPart.length == 2 && itemTextPart[0].equals("call")) {
            // it is a call itemToDo create another button for calling
            menu.add(0, 1, 0, itemText);
        }


    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String itemText = mToDoList.get(info.position).GetText();
        if(item.getItemId()==0) {
            // Delete item from list
            // First: delete item from list
            mToDoList.remove(info.position);
            // Second: redraw list
            mAdapter.notifyDataSetChanged();

        } else if(item.getItemId()==1) {
            // Call
            final String[]  itemTextPart = itemText.split(" ");
            Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+itemTextPart[1]));
            startActivity(dial);
        } else {
            return false;
        }
        return true;
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

        if(id == R.id.menuItemAdd) {
            // Add a new item to the list
            Intent intent = new Intent(this, AddNewTodoItemActivity.class);
            startActivityForResult(intent, 42);

        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        if (reqCode == 42 && resCode == RESULT_OK) {

            // Get data from add item activity
            String isDate = data.getStringExtra(AddNewTodoItemActivity.ITEM_IS_DATE);
            Date itemDate = null;
            if(isDate.equals(AddNewTodoItemActivity.ITEM_IS_DATE)) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                itemDate = (Date) data.getSerializableExtra(AddNewTodoItemActivity.ITEM_DATE);
            } else {

            }
            String itemText = data.getStringExtra(AddNewTodoItemActivity.ITEM_TEXT);

            // Add new item to list view
            mToDoList.add(new ToDoItem(itemText,itemDate));

            // Refresh view
            mAdapter.notifyDataSetChanged();

        }
    }
}
