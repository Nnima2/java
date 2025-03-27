package models;
import models.enums.Currency;
/*
Explanation:
- when we create an expense, we need to store some information about it.
- Expense is something that we need to make it an object.
- put those information here and use them in your code.
 */
public class Expense{
    private final User debtor;
    private final Group group;
    private double amount;
    private Currency currency;

    public Expense(User debtor, double amount, Currency currency,Group group) {
        this.debtor = debtor;
        this.group = group;
        this.amount = amount;
        this.currency = currency;
    }

    public User getDebtor() {
        return debtor;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Group getGroup() {
        return group;
    }

    public void setValue(double amount,Currency currency) {
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "debtor = '" + debtor.getUsername() + "'\n amount = '" + amount + "'\n currency = '" + currency + "'";
    }
}