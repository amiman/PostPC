package il.ac.huji.todolist;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Amitay on 14/03/15.
 */
public class ToDoListAdapter extends ArrayAdapter<ToDoItem> {

    private final static String NO_DUE_DATE = "No Due Date";
    private final Context mContext;
    private final List<ToDoItem> mObjects;

    public ToDoListAdapter(Context context, int textViewResourceId, List<ToDoItem> objects) {
        super(context, textViewResourceId, objects);

        mContext = context;
        mObjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // First: create an inflater service in order to inflate the current row in the layout
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Second: get the id for the item text in order to change ot to the appropriate text according to the position
        View rowView = inflater.inflate(R.layout.list_todo_item, parent, false);
        TextView itemText = (TextView) rowView.findViewById(R.id.txtTodoTitle);
        TextView itemDate = (TextView) rowView.findViewById(R.id.txtTodoDueDate);

        // Third: change the text according to the position
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        itemText.setText(mObjects.get(position).GetText());
        if(mObjects.get(position).GetDate()!= null) {
            itemDate.setText(formatter.format(mObjects.get(position).GetDate()));

            // Forth: change color for the overdue items
            Calendar c = Calendar.getInstance();
            Date currentDate = c.getTime();

            if (currentDate.after(mObjects.get(position).GetDate())) {
                itemText.setTextColor(Color.RED);
                itemDate.setTextColor(Color.RED);
            }
        } else {
            itemDate.setText(NO_DUE_DATE);
        }

        return rowView;
    }
}
