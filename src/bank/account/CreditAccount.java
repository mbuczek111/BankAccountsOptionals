package bank.account;

import bank.NationalBank;
import bank.ReachedCreditLimitException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreditAccount extends Account {
    private BigDecimal creditLimit;

    public CreditAccount(BigDecimal percents, BigDecimal creditLimit) {
        super(percents);
        this.creditLimit = creditLimit;
    }

    @Override
    public BigDecimal withDraw(BigDecimal amount) throws ReachedCreditLimitException {
        if(this.getBalance().subtract(amount).compareTo(creditLimit) > 0) {
            this.setBalance(this.getBalance().subtract(amount));
            this.addTransactionLog("Withdrawing from CreditAccount", LocalDateTime.now());

        }
        else
            throw new ReachedCreditLimitException(this.getBalance(),creditLimit);

        return null;
    }

    @Override
    public BigDecimal applyPercentage() {
        if(this.getBalance().compareTo(BigDecimal.ZERO) < 0)
        {
            this.setBalance(this.getBalance().multiply(getPercents().add(BigDecimal.ONE)));
            this.addTransactionLog("Applying percents on Credit Account", LocalDateTime.now());

        }
        return null;
    }

    @Override
    public BigDecimal transferMoney(String bankName, int accountNumber, BigDecimal amount) throws ReachedCreditLimitException{
        if(this.getBalance().subtract(amount).compareTo(creditLimit) > 0) {
            this.setBalance(this.getBalance().subtract(amount));
            NationalBank.getByName(bankName).get().getByNumber(accountNumber).get().setBalance(NationalBank.getByName(bankName).get().getByNumber(accountNumber).get().getBalance().add(amount));
            this.addTransactionLog("Transferring money from Credit Account", LocalDateTime.now());

        }
            else
            throw new ReachedCreditLimitException(this.getBalance(),creditLimit);
        return null;
    }
}
