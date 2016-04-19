package by.maesens.android.utils;

/**
 * Created by Sol on 24.03.2016.
 */
public class Calculate {
    public static int calculateCurrentPercent(long currentAmount, long requiredAmount) {
        if (requiredAmount > 0)
            return (int) Math.round((double) currentAmount * 100 / requiredAmount);
        else
            return 100;
    }
}
