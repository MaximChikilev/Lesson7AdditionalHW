package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQuery(name = "getAverageCurrencyFromPeriod", query = "SELECT AVG(e.rate) FROM CurrencyUnit e")
@NamedQuery(name = "getMaxCurrencyFromPeriod", query = "SELECT MAX (e.rate) FROM CurrencyUnit e")
@NamedQuery(name = "getCurrencyFromDate", query = "SELECT e.rate FROM CurrencyUnit e WHERE e.exchangedate=:target_date")


public class CurrencyUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date exchangedate;
    private String cc;
    private String txt;
    private double rate;

    public CurrencyUnit() {
    }

    public CurrencyUnit(String cc, String txt, double rate) {
        this.cc = cc;
        this.txt = txt;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "dao.dao.CurrencyUnitDao.entity.CurrencyUnit{" +
                "exchangedate='" + exchangedate + '\'' +
                ", cc='" + cc + '\'' +
                ", txt='" + txt + '\'' +
                ", rate=" + rate +
                '}';
    }
}
