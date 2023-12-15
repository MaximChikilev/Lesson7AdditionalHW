import dao.CurrencyUnitDao;
import entity.Utils;

import java.util.Scanner;

public class Main {
    static String menu = "What do you want to do with statistic :\n" +
            "1. Get average currency from period\n" +
            "2. Get currency from date. Target date must be between Start and finish date\n" +
            "3. Get max currency from period";
    static Double result;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CurrencyUnitDao cud = Utils.getCurrencyUnitDao();
        Utils.loadStatistic();
        System.out.println(menu);
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                Utils.printQueryResult(cud.getAverageCurrency(), "Average currency");
                break;
            case 3:
                Utils.printQueryResult(cud.getMaxCurrency(), "Max currency");
                break;
            case 2:
                System.out.println("Enter target date (yyyyMMdd)");
                String targetDate = scanner.nextLine();
                Utils.printQueryResult(cud.getCurrencyFromDate(targetDate), "Currency from date");
                break;
        }

    }
}
