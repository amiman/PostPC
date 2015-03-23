package il.ac.huji.todolist;

import java.util.Date;

/**
 * Created by Amitay on 14/03/15.
 */
public class ToDoItem {

    private String mText;
    private Date mDate;

    public ToDoItem(String itemText, Date itemDate) {

        this.mText = itemText;
        this.mDate = itemDate;
    }

    public String GetText() {
        return mText;
    }
    public Date GetDate() { return mDate; }
}
