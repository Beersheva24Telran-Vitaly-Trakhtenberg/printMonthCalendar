package telran.javalearning;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
record MonthYear(Integer month, Integer year) {}

public class Calendar
{
    private MonthYear month_year;
    private LocalDate local_month;
    final int WIDTH = 12;
    private int first_day_week = 1;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");

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
    public String printTitle(boolean use_design)
    {
        String res = "";
        if (!use_design) {
            res = this.printTitle();
        } else {
            res = "\033[1;31m" + this.month_year.year().toString() + ", " + this.local_month.getMonth() + "\033[0m";
            int width = (WIDTH*7 - res.length())/2;
            res = " ".repeat(width) + res;
        }

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
    public String printWeekDaysHeader(boolean use_design)
    {
        String res = "";
        String tmp = "";
        if (!use_design) {
            res = this.printWeekDaysHeader();
        } else {
            String color = "";
            for (int i=1; i<8; i++) {
                if (i==6) {
                    color = "\033[1;33m";
                } else {
                    color = "";
                }
                int space_before = (WIDTH - DayOfWeek.of(i).toString().length()) / 2;
                int space_after = WIDTH - (space_before + DayOfWeek.of(i).toString().length());
                if (i<this.first_day_week) {
                    tmp += " ".repeat(space_before) + color + DayOfWeek.of(i) + "\033[0m" + " ".repeat(space_after);
                } else {
                    res += " ".repeat(space_before) + color + DayOfWeek.of(i) + "\033[0m" + " ".repeat(space_after);
                }
            }
        }

        return res + tmp;
    }
    public String printDates()
    {
        int date_week_start = 1;
        int number_week = 0;
        int delta = this.calculateFirstPosition();
        StringBuilder res = new StringBuilder(this.generateDatesString(delta, date_week_start) + "\n");
        while (true) {
            number_week++;
            try {
                res.append(this.generateDatesString(1, (number_week * 7 - delta)) + "\n");
            }
            catch (DateTimeException e) {
                res.append(e.getMessage());
                break;
            }
        }
        return res.toString();
    }

    public String printHorizontalLine(String symbol)
    {
        return symbol.repeat(7*WIDTH);
    }

    private int calculateFirstPosition()
    {
        int first_day_of_month = this.numberFirstDayOfMonth();
        return (int)(first_day_of_month - this.first_day_week + 8) % 7;
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

    private String generateDatesString(int day_week_start, int date_week_start)
    {
        StringBuilder res = new StringBuilder(" ".repeat(WIDTH * (day_week_start - 1)));
        int space = (WIDTH - 2) / 2;

        int current_day_number = day_week_start;
        while (current_day_number < 8) {
            try {
                LocalDate date = LocalDate.of(this.month_year.year(), this.month_year.month(), date_week_start);
                res.append(" ".repeat(space)).append(date.format(this.formatter)).append(" ".repeat(space));
            } catch (DateTimeException e) {
                throw new DateTimeException(res.toString());
            }
            current_day_number++;
            date_week_start++;
        }
        return res.toString();
    }
}
