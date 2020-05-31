package bank.account;

import bank.Bank;
import bank.NationalBank;
import bank.NonSufficientFundsException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepositAccount extends Account {
    public DepositAccount(BigDecimal percents) {
        super(percents);
    }

    @Override
    public BigDecimal withDraw(BigDecimal amount) throws NonSufficientFundsException {

        if(this.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0) {
            this.setBalance(this.getBalance().subtract(amount));
            this.addTransactionLog("Withdrawing from DepositAccount", LocalDateTime.now());

        }
        else
            throw new NonSufficientFundsException(this.getBalance());
        return null;
    }

    @Override
    public BigDecimal applyPercentage() {
        if(this.getBalance().compareTo(BigDecimal.ZERO) >= 0)
        {
            this.setBalance(this.getBalance().multiply(this.getPercents().add(BigDecimal.ONE)));
            this.addTransactionLog("Applying percents on Deposit Account", LocalDateTime.now());

        }
        return null;
    }

    @Override
    public BigDecimal transferMoney(String bankName, int accountNumber, BigDecimal amount) throws NonSufficientFundsException{
        if(this.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0) {
            this.setBalance(this.getBalance().subtract(amount));
            Bank b1 =NationalBank.getByName(bankName).get();
            if(b1.getByNumber(accountNumber).isPresent())
            {
                Account account = NationalBank.getByName(bankName).get().getByNumber(accountNumber).get();
                account.setBalance(NationalBank.getByName(bankName).get().getByNumber(accountNumber).get().getBalance().add(amount));

            }
            this.addTransactionLog("Transferring money from Deposit Account", LocalDateTime.now());
        }
        else
            throw new NonSufficientFundsException(this.getBalance());


        return null;
    }
}