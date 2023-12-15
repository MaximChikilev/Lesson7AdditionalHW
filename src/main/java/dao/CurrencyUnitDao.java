package dao;

import entity.CurrencyUnit;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CurrencyUnitDao extends AbstractDao<CurrencyUnit> {
    public CurrencyUnitDao(EntityManager em) {
        super(em);
    }

    @Override
    public List<CurrencyUnit> getAll() {
        Query query = em.createQuery("SELECT c FROM entity.CurrencyUnit c", CurrencyUnit.class);
        List<CurrencyUnit> list = (List<CurrencyUnit>) query.getResultList();
        return list;
    }

    public Double getAverageCurrency() {
        TypedQuery<Double> query = em.createNamedQuery(
                "getAverageCurrencyFromPeriod", Double.class);
        return query.getSingleResult();
    }

    public Double getMaxCurrency() {
        TypedQuery<Double> query = em.createNamedQuery(
                "getMaxCurrencyFromPeriod", Double.class);
        return query.getSingleResult();
    }

    public Double getCurrencyFromDate(String targetDate) throws NoResultException {
        Double result;
        TypedQuery<Double> query = em.createNamedQuery(
                "getCurrencyFromDate", Double.class);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = null;
        try {
            date = formatter.parse(targetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.setParameter("target_date", date);
        try {
            result = query.getSingleResult();
        }catch (NoResultException e){
            result = 0.0;
            System.out.println("Date out of range");
        }
        return result;
    }

}
