package tipcalculator.huji.ac.il.tipcalculator;

/**
 * Created by amitai on 05/03/15.
 */
public class TipCalculator {

    private double mTipPercent;

    public TipCalculator(double tipPercent) {
        this.mTipPercent = tipPercent;
    }

    public double CalculateTip(double billValue, boolean round) {

        if(round) {
            return Math.round((this.mTipPercent*billValue)/100);
        } else {
            return (this.mTipPercent*billValue)/100;
        }
    }
}
