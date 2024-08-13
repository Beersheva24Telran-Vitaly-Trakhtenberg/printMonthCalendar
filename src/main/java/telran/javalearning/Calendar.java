package telran.javalearning;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
record MonthYear(Integer month, Integer year) {}

public class Calendar
{
    private MonthYear month_year;
    private LocalDate local_month;
    final int WIDTH = 12;
    int first_day_week = 1;

    public Calendar(MonthYear month_year, int first_day_week)
    {
        this.month_year = month_year;
        this.local_month = LocalDate.of(this.month_year.year(), this.month_year.month(), 1);
        this.first_day_week = first_day_week;
    }

    public Calendar(MonthYear month_year)
    {
        this.month_year = month_year;
        this.local_month = LocalDate.of(this.month_year.year(), this.month_year.month(), 1);
    }

    public String printTitle()
    {
        String res = this.month_year.year().toString() + ", " + this.local_month.getMonth();
        int width = (WIDTH*7 - res.length())/2;
        res = " ".repeat(width) + res;

        return res;
    }
    public String printWeekDaysHeader()
    {
        String res = "";
        String tmp = "";
        for (int i=1; i<8; i++) {
            int space_before = (WIDTH - DayOfWeek.of(i).toString().length()) / 2;
            int space_after = WIDTH - (space_before + DayOfWeek.of(i).toString().length());
            if (i<this.first_day_week) {
                tmp += " ".repeat(space_before) + DayOfWeek.of(i) + " ".repeat(space_after);
            } else {
                res += " ".repeat(space_before) + DayOfWeek.of(i) + " ".repeat(space_after);
            }
        }

        return res + tmp;
    }
    public String printDates()
    {
        String res = "";

        return res;
    }

    private int numberFirstDayOfMonth()
    {
        int res = -1;
        try {
            res = this.local_month.getDayOfWeek().getValue();
        }
        catch (RuntimeException e) {
            System.out.println("Something went wrong. ");
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return res;
    }

}
