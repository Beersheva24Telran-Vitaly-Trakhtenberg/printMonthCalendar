package telran.javalearning;

import java.time.LocalDate;
import java.util.Scanner;

import telran.javalearning.Calendar;

public class Main
{
    public static void main(String args[])
    {
        LocalDate ld = LocalDate.now();
        int year = ld.getYear();
        int month = ld.getMonthValue();
        int firstDayWeek = 1;
        boolean validInput = false;

        Scanner scanner = new Scanner(System.in);

        if (args.length >= 3) {
            try {
                year = Integer.parseInt(args[0]);
                month = Integer.parseInt(args[1]);
                firstDayWeek = Integer.parseInt(args[2]);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input from command line. Please enter valid integers for year, month and number of first day of week.");
            }
        }

        while (!validInput) {
            System.out.printf("Enter year (or 'q' to quit). Default %d: ", year);
            String yearInput = scanner.nextLine();
            if (yearInput.equalsIgnoreCase("q")) {
                System.out.println("Exiting...");
                return;
            } else if (!yearInput.isEmpty() && !(Integer.parseInt(yearInput) > 1970 &&  Integer.parseInt(yearInput) < 2050)) {
                System.out.println("Year incorrect. Please enter a valid year.");
                continue;
            }

            System.out.printf("Enter month (or 'q' to quit). Default %d: ", month);
            String monthInput = scanner.nextLine();
            if (monthInput.equalsIgnoreCase("q")) {
                System.out.println("Exiting...");
                return;
            } else if (!monthInput.isEmpty() && !(Integer.parseInt(monthInput) > 0 &&  Integer.parseInt(monthInput) < 13)) {
                System.out.println("Month incorrect. Please enter a valid year and month.");
                continue;
            }

            System.out.print("Enter number of first day of week (or 'q' to quit). Default value is 1: ");
            String firstDayWeekInput = scanner.nextLine();
            if (firstDayWeekInput.equalsIgnoreCase("q")) {
                System.out.println("Exiting...");
                return;
            } else if (!firstDayWeekInput.isEmpty() && !(Integer.parseInt(firstDayWeekInput) > 0 &&  Integer.parseInt(firstDayWeekInput) < 8)) {
                System.out.println("Number of first day of week incorrect. Please enter a valid year, month and first day.");
                continue;
            }

            try {
                year = yearInput.isEmpty()?year:Integer.parseInt(yearInput);
                month = monthInput.isEmpty()?month:Integer.parseInt(monthInput);
                firstDayWeek = firstDayWeekInput.isEmpty()?1:Integer.parseInt(firstDayWeekInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid integers for year, month and first day of week.");
            }
        }

        Calendar calendar = new Calendar(new MonthYear(month, year), firstDayWeek);
        System.out.println(calendar.printTitle(true));
        System.out.println(calendar.printHorizontalLine("="));
        System.out.println(calendar.printWeekDaysHeader(true));
        System.out.println(calendar.printHorizontalLine("-"));
        System.out.println(calendar.printDates());
    }
}

