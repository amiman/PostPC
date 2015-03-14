package il.ac.huji.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Amitay on 14/03/15.
 */
public class ToDoListAdapter extends ArrayAdapter<ToDoItem> {

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
        TextView itemText = (TextView) rowView.findViewById(R.id.listItemText);

        // Third: change the text according to the position
        itemText.setText(mObjects.get(position).GetText());

        // Forth: change color of the text according gto the position
        if(position % 2 == 0) {
            itemText.setTextColor(Color.RED);
        } else {
            itemText.setTextColor(Color.BLUE);
        }

        return rowView;
    }
}
