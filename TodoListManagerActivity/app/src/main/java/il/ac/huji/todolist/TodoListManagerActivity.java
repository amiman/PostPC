package il.ac.huji.todolist;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TodoListManagerActivity extends ActionBarActivity {

    private ArrayList<ToDoItem> mToDoList;
    private ToDoListAdapter mAdapter;
    private ListView mToDoListView;
    private DBHeleper mDBheleper;
    static final String EMPTY_STRING = "";
    static final String DELETE_ITEM = "Delete Item";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        // Create dbhelper and Load data of todo_items from the to todo_db
        mDBheleper = new DBHeleper(this.getApplicationContext());

        // Create the array list for the items
        this.mToDoList = mDBheleper.GetAllItems();

        // Connect the list view to the arrayList
        this.mAdapter = new ToDoListAdapter(this.getApplicationContext(), R.id.list_item, mToDoList);
        this.mToDoListView = (ListView) findViewById(R.id.lstTodoItems);
        this.mToDoListView.setAdapter(mAdapter);
        //String from[] = {mDBheleper.DB_FIELD_TITLE ,mDBheleper.DB_FIELD_DUE};
        //int to[] = {R.id.txtTodoTitle,R.id.txtTodoDueDate};
        //this.mToDoListView.setAdapter(new SimpleCursorAdapter(this.getApplicationContext(),R.id.list_item,cursor,from,to,0));

        // register the listview to context menu
        registerForContextMenu(mToDoListView);

        // create the db helper in order to communicate with sqlite data base
        this.mDBheleper = new DBHeleper(this);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        getMenuInflater().inflate(R.menu.context_menu, menu);

        //menu.add(0, 0, 0, DELETE_ITEM);
        String itemText = mToDoList.get(info.position).GetText();
        menu.setHeaderTitle(itemText);
        menu.getItem(0).setTitle(DELETE_ITEM);

        // check if the title is calling someone
        final String[]  itemTextPart = itemText.split(" ");
        if (itemTextPart.length == 2 && itemTextPart[0].equals("call")) {

            // it is a call itemToDo create another button for calling
            menu.getItem(1).setTitle(itemText);
        } else {
            menu.getItem(1).setEnabled(false);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String itemText = mToDoList.get(info.position).GetText();
        if(item.getItemId()==R.id.menuItemDelete) {
            // Delete item from list

            // First: delete item from list and DB
            mToDoList.remove(info.position);
            mDBheleper.DeleteData(itemText);

            // Second: redraw list
            mAdapter.notifyDataSetChanged();

        } else if(item.getItemId()==R.id.menuItemCall) {
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
            }
            String itemText = data.getStringExtra(AddNewTodoItemActivity.ITEM_TEXT);

            // Add new item to list view and DB
            mToDoList.add(new ToDoItem(itemText,itemDate));
            if(itemDate != null) {
                mDBheleper.InsertData(itemText, itemDate.getTime());
            } else {
                mDBheleper.InsertData(itemText, -1);
            }

            // Refresh view
            mAdapter.notifyDataSetChanged();

        }
    }
}
