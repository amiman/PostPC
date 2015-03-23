package il.ac.huji.todolist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddNewTodoItemActivity extends Activity {

    static final String ITEM_TEXT = "Text";
    static final String ITEM_DATE = "Date";
    static final String ITEM_IS_DATE = "IsDate";
    static final String ITEM_IS_NOT_DATE = "IsNotDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo_item);

        Button btnOK = (Button) findViewById(R.id.btnOK);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        final CheckBox radioUseDatePicker = (CheckBox) findViewById(R.id.useDatePicker);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent data = new Intent();

                TextView itemText = (TextView) findViewById(R.id.edtNewItem);
                data.putExtra(ITEM_TEXT,itemText.getText().toString());

                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
                Date datePickerDate = getDateFromDatePicker(datePicker);
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                String datePickerString = formatter.format(datePickerDate);
                //data.putExtra(ITEM_DATE,datePickerString);
                if(radioUseDatePicker.isChecked()) {
                    data.putExtra(ITEM_IS_DATE, ITEM_IS_DATE);
                    data.putExtra(ITEM_DATE, (Serializable) datePickerDate);
                } else {
                    data.putExtra(ITEM_IS_DATE, ITEM_IS_NOT_DATE);
                }

                if (getParent() == null) {
                    setResult(Activity.RESULT_OK, data);
                } else {
                    getParent().setResult(Activity.RESULT_OK, data);
                }
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParent().setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    /**
     *
     * @param datePicker
     * @return a java.util.Date
     */
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
