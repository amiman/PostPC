package il.ac.huji.todolist;

/**
 * Created by Amitay on 14/03/15.
 */
public class ToDoItem {

    private String mText;

    public ToDoItem(String itemText) {

        this.mText = itemText;
    }

    public String GetText() {
        return mText;
    }
}
