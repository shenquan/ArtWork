package com.example.sqhan.artwork.annotation.test3;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hanshenquan.
 */
public class Test3 {
    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    @WeekDays
    private int currentDay = SUNDAY;

    public static void main(String[] args) {
        Test3 test3 = new Test3();
        test3.setCurrentDay(WEDNESDAY);

        @WeekDays int today = test3.getCurrentDay();

        switch (today) {
            case SUNDAY:
                System.out.println(7);
                break;
            case MONDAY:
                System.out.println(1);
                break;
            case TUESDAY:
                System.out.println(2);
                break;
            case WEDNESDAY:
                System.out.println(3);
                break;
            case THURSDAY:
                System.out.println(4);
                break;
            case FRIDAY:
                System.out.println(5);
                break;
            case SATURDAY:
                System.out.println(6);
                break;
            default:
                break;
        }
    }

    @WeekDays
    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(@WeekDays int currentDay) {
        this.currentDay = currentDay;
    }

    @IntDef({SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WeekDays {
    }
}
