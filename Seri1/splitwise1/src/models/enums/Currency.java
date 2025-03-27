package models.enums;

/*
Explanation:
- We need to define a currency enum.
- currencies in out app are some constants that we need to define them in our code once and use them in our code.
- each currency has some data, put them here and use some methods to work with currencies so simply.
 */

public enum Currency {
    GTC(1),
    SUD(0.5),
    QTR(0.2);

    private final double value;

    Currency(double value) {this.value = value;}
    public static Currency getCurrency(String currency) {
        return Currency.valueOf(currency.toUpperCase());
    }

    public double getValue() {
        return value;
    }

}
