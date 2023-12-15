package org.example;

import entity.CurrencyUnit;
import org.junit.Test;

import java.sql.Date;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertTrue;

public class CurrencyUnitDaoTest extends BaseTest {
    private CurrencyUnit saveTestCurrencyUnit(final String cc, final String txt, final double rate) {
        Callable<CurrencyUnit> c = new Callable<CurrencyUnit>() {
            public CurrencyUnit call() throws Exception {
                CurrencyUnit currencyUnit = new CurrencyUnit(cc, txt, rate);
                em.persist(currencyUnit);
                return currencyUnit;
            }
        };

        return performTransaction(c);
    }

    @Test
    public void addNewCurrencyUnitTest() {
        java.util.Date date = new java.util.Date();
        CurrencyUnit currencyUnit = saveTestCurrencyUnit("usd", "Dollar", 36.8);
        long id = currencyUnit.getId();
        assertTrue(id > 0);
    }
}
