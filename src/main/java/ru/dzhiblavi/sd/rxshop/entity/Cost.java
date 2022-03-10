package ru.dzhiblavi.sd.rxshop.entity;

import java.util.HashMap;
import java.util.Map;

public class Cost {
    public enum Currency {
        RUB,
        EUR,
        USD
    }

    private final static Map<Currency, Double> currencyUsdValue = new HashMap<>();

    static {
        currencyUsdValue.put(Currency.RUB, 150.0);
        currencyUsdValue.put(Currency.EUR, 0.8);
        currencyUsdValue.put(Currency.USD, 1.0);
    }

    private final double usdValue;
    private Currency type;

    public Cost(final Currency type, final double value) {
        this.type = type;
        this.usdValue = value / currencyUsdValue.get(type);
    }

    public Currency getCurrency() {
        return type;
    }

    public void setCurrency(final Currency type) {
        this.type = type;
    }

    public double getUsdValue() {
        return usdValue;
    }

    public double getValue() {
        return usdValue * currencyUsdValue.get(type);
    }
}

