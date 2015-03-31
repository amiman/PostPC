package il.ac.huji.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Amitay nachmani on 31/03/15.
 */
public class DBHeleper extends SQLiteOpenHelper {

    private static final String DB_TABLE_NAME = "todo_db ";
    private static final String CREATE_TABLE = "create table if not exists ";
    public static final String DB_FIELD_TITLE = "title";
    public static final String DB_FIELD_DUE = "due";

    public DBHeleper(Context context) {
        super(context, DB_TABLE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        // if table dose not exists create it
        db.execSQL(CREATE_TABLE + DB_TABLE_NAME + "(" +
                "_id integer primary key autoincrement," +
                DB_FIELD_TITLE + " text," +
                DB_FIELD_DUE + " long" +
                ");");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

    }

    public ArrayList<ToDoItem> GetAllItems() {
        String selectQuery = "SELECT * FROM " + DB_TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<ToDoItem> list = new ArrayList<ToDoItem>();
        Date due = null;
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1);
                long milliSecond = cursor.getLong(2);
                if(milliSecond != 0) {
                    due = new Date(cursor.getLong(2));
                    list.add(new ToDoItem(title,due));
                } else {
                    list.add(new ToDoItem(title,null));
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public void InsertData(String title,long due) {

        // Create content value for sql insert
        ContentValues todoValues = new ContentValues();
        todoValues.put(DBHeleper.DB_FIELD_TITLE,title);
        if(due != -1) {
            todoValues.put(DBHeleper.DB_FIELD_DUE, due);
        }

        // insert data
        SQLiteDatabase db = getWritableDatabase();
        db.insert(DB_TABLE_NAME,null,todoValues);
        db.close();
    }

    public void DeleteData(String title) {
        SQLiteDatabase db = getWritableDatabase();
        // preform delete from data base
        db.delete(DB_TABLE_NAME, DB_FIELD_TITLE + " = " + "'"+title+"'", null);
        db.close();
    }

}
