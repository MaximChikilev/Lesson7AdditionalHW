package entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dao.CurrencyUnitDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Utils {
    private static String url = "https://bank.gov.ua/NBU_Exchange/exchange_site?";
    private static String startDate = "start=";
    private static String finishDate = "&end=";
    private static String currencyCode = "&valcode=";
    private static String sortAndOrdersParams = "&sort=exchangedate&order=desc&json";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPATest");
    private static EntityManager em = emf.createEntityManager();
    private static Scanner scanner = new Scanner(System.in);
    private static CurrencyUnitDao cud;

    public static void printQueryResult(Double result, String outputParam) {
        String formattedString = String.format(outputParam + " is : %.2f", result);
        System.out.println(formattedString);
    }

    private static List<CurrencyUnit> getCurrencyStatistic() {
        Type itemsListType = new TypeToken<List<CurrencyUnit>>() {
        }.getType();
        String response = null;
        try {
            response = getStringFromResponse(getUrlWithParams());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        List<CurrencyUnit> currencyUnits = gson.fromJson(response, itemsListType);
        return currencyUnits;
    }

    private static void saveStatisticToBd(List<CurrencyUnit> list) {
        cud = new CurrencyUnitDao(em);
        for (CurrencyUnit currencyUnit : list) {
            cud.create(currencyUnit);
        }
    }

    private static URL getUrlWithParams() throws MalformedURLException {
        url += (startDate + finishDate + currencyCode + sortAndOrdersParams);
        return new URL(url);
    }

    private static String getStringFromResponse(URL url) throws IOException {
        String strBuf;
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        InputStream is = http.getInputStream();
        try {
            byte[] buf = Utils.responseBodyToArray(is);
            strBuf = new String(buf, StandardCharsets.UTF_8);

        } finally {
            is.close();
        }
        return strBuf;
    }

    private static byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }

    public static void loadStatistic() {
        setStartDate();
        setFinishDate();
        setCurrencyCode();
        saveStatisticToBd(getCurrencyStatistic());
    }

    private static void setStartDate() {
        System.out.println("Enter the start date of the period (yyyyMMdd)");
        startDate += scanner.nextLine();
    }

    private static void setFinishDate() {
        System.out.println("Enter the final date of the period (yyyyMMdd)");
        finishDate += scanner.nextLine();
    }

    private static void setCurrencyCode() {
        System.out.println("Enter currency ticker (usd, eur)");
        currencyCode += scanner.nextLine();
    }
    public static CurrencyUnitDao getCurrencyUnitDao(){
        return cud;
    }
}
