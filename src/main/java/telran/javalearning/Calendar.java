package telran.javalearning;

import java.time.LocalDate;
import java.time.DayOfWeek;

record MonthYear(Integer month, Integer year) {}

public class Calendar
{
    private final int WIDTH = 12;
    private final String COLOR_OFF = "\033[0m";

    private MonthYear month_year;
    private LocalDate local_month;
    private int first_day_week;
    private boolean use_design;
    private int number_of_weeks = 0;
    private String[][] dates;
    private DayOfWeek[] reordered_days_of_week = new DayOfWeek[7];

    public Calendar(MonthYear month_year, int first_day_week, boolean use_design)
    {
        this.first_day_week = first_day_week;
        this.month_year = month_year;
        this.local_month = LocalDate.of(this.month_year.year(), this.month_year.month(), 1);
        this.use_design = use_design;
        reorderDaysOfWeek();
        prepareDates();
    }

    public String printTitle()
    {
        String color = this.use_design?"\033[1;31m":"";
        String res = color + this.month_year.year().toString() + ", " + this.local_month.getMonth() + this.COLOR_OFF;
        int width = (WIDTH*7 - res.length())/2;
        res = " ".repeat(width) + res;

        return res;
    }

    public String printWeekDaysHeader()
    {
        return printWorkingDays() + printDaysOff();
    }

    private void prepareDates()
    {
        int first_day_of_month = this.getDayOfWeekOfFirstDayOfMonth();
        int days_in_month = this.local_month.lengthOfMonth();

        int days_before_first_week = (first_day_of_month - this.first_day_week + 7) % 7;
        number_of_weeks = (days_before_first_week + days_in_month + 6) / 7;

        this.dates = new String[number_of_weeks][7];
        int day = 1;
        for (int day_of_week = 0; day_of_week < 7; day_of_week++) {
            if (day_of_week < days_before_first_week) {
                dates[0][day_of_week] = "";
            } else {
                dates[0][day_of_week] = String.valueOf(day++);
            }
        }
        for (int week = 1; week < number_of_weeks; week++) {
            for (int day_of_week = 0; day_of_week < 7; day_of_week++) {
                if (day <= this.local_month.lengthOfMonth()) {
                    dates[week][day_of_week] = String.valueOf(day++);
                } else {
                    dates[week][day_of_week] = "";
                }
            }
        }
    }

    public String printDates()
    {
        int space = (WIDTH - 2) / 2;
        StringBuilder res = new StringBuilder();

        for (int week = 0; week < this.number_of_weeks; week++) {
            for (int day = 0; day < 7; day++) {
                int length = this.dates[week][day].length();
                res.append(" ".repeat(space)).append(" ".repeat(2 - length)).append(this.dates[week][day]).append(" ".repeat(space));
            }
            res.append("\n");
        }

        return res.toString();
    }

    public String printHorizontalLine(String symbol)
    {
        return symbol.repeat(7*WIDTH);
    }

    private int getDayOfWeekOfFirstDayOfMonth()
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

    private void reorderDaysOfWeek()
    {
        DayOfWeek[] days_of_week = DayOfWeek.values();
        int index = 0;

        for (int i = first_day_week - 1; i < days_of_week.length; i++) {
            this.reordered_days_of_week[index++] = days_of_week[i];
        }
        for (int i = 0; i < first_day_week - 1; i++) {
            this.reordered_days_of_week[index++] = days_of_week[i];
        }
    }

    private String printWorkingDays()
    {
        StringBuilder res = new StringBuilder();
        for (int day = 0; day<5; day++) {
            int space_before = (this.WIDTH - this.reordered_days_of_week[day].toString().length()) / 2;
            int space_after = this.WIDTH - (space_before + this.reordered_days_of_week[day].toString().length());
            res.append(" ".repeat(space_before)).append(this.reordered_days_of_week[day]).append(" ".repeat(space_after));
        }

        return res.toString();
    }

    private String printDaysOff()
    {
        String day_of_color = this.use_design?"\033[1;33m":"";
        String half_day_color = this.use_design?"\033[;33m":"";
        String reset_color = "\033[0m";
        StringBuilder res = new StringBuilder();

        int space_before = (this.WIDTH - this.reordered_days_of_week[5].toString().length()) / 2;
        int space_after = this.WIDTH - (space_before + this.reordered_days_of_week[5].toString().length());
        res.append(" ".repeat(space_before)).append(half_day_color).append(this.reordered_days_of_week[5]).append(reset_color).append(" ".repeat(space_after));

        space_before = (this.WIDTH - this.reordered_days_of_week[6].toString().length()) / 2;
        space_after = this.WIDTH - (space_before + this.reordered_days_of_week[6].toString().length());
        res.append(" ".repeat(space_before)).append(day_of_color).append(this.reordered_days_of_week[6]).append(reset_color).append(" ".repeat(space_after));

        return res.toString();
    }

}
