package il.ac.huji.tipcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


public class TipCalculatorActivity extends ActionBarActivity {

    private TipCalculator mTipCalculator;
    static final String NULL_STRING = "";
    static final String NULL_STRING_MASSAGE = "There is not any bill input! please insert the bill";
    static final String TIP = "TIP: ";
    static final String DOLLAR_SIGN = "$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(il.ac.huji.tipcalculator.R.layout.activity_tip_calculator);

        // Create the TipCalculator
        mTipCalculator = new TipCalculator(12);

        // Add listeners to calculator button
        Button btnCalculator = (Button) findViewById(il.ac.huji.tipcalculator.R.id.btnCalculate);
        btnCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // First: Get the value of the bill
                TextView billView = (TextView) findViewById(il.ac.huji.tipcalculator.R.id.edtBillAmount);

                // Second: Check that the string is a valid number
                String billStr  = billView.getText().toString();
                if(billStr.equals(NULL_STRING)) {

                    Toast.makeText(getApplicationContext(),NULL_STRING_MASSAGE,Toast.LENGTH_LONG).show();
                    return;
                }

                // Third: Get the check box
                CheckBox roundCheckBox = (CheckBox) findViewById(il.ac.huji.tipcalculator.R.id.chkRound);

                // Forth: use the tip calculator
                double tipValue = mTipCalculator.CalculateTip(Double.parseDouble(billStr),roundCheckBox.isChecked());

                // Fifth: display the tip
                TextView tipView = (TextView) findViewById(il.ac.huji.tipcalculator.R.id.txtTipResult);
                tipView.setText(TIP + DOLLAR_SIGN + String.valueOf(tipValue));

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(il.ac.huji.tipcalculator.R.menu.menu_tip_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == il.ac.huji.tipcalculator.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
