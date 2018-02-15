package com.cobinhood;

import com.cobinhood.responses.entity.*;
import com.cobinhood.responses.wallet.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CobinhoodAccountingTest {

    private static final String CURRENCY_ID = "ETH";
    private static Currency CURRENCY;
    private static final String  DEPOSIT_ID = "f361946c-2abc-4219-9f19-6fbc2e5190f1";
    private static final String  WITHDRAWAL_ID = "bfc6f810-0b4e-4d10-80ad-affe7dfdb163";

    @BeforeClass
    public static void beforeClass(){
        CURRENCY = new Currency();
        CURRENCY.setCurrency(CURRENCY_ID);
    }

    private CobinhoodClient cobinhoodClient = new CobinhoodClient(ApiKey.ApiKey);

    private void testWithdrawalCryptoAddresses(WithdrawalCryptoAddresses cryptoAddresses){
        for (WithdrawalCryptoAddress cryptoAddress : cryptoAddresses) {
            assertNotNull(cryptoAddress);
            assertNotNull(cryptoAddress.getAddress());
            assertNotNull(cryptoAddress.getType());
            assertNotNull(cryptoAddress.getCurrency());
            assertNotNull(cryptoAddress.getCreatedAt());
            assertNotNull(cryptoAddress.getUuid());
            assertNotNull(cryptoAddress.getName());
        }
    }

    @Test
    public void withdrawalAddresses(){
        WithdrawalCryptoAddresses withdrawalAddresses = cobinhoodClient.getWithdrawalAddresses();
        TestingUtils.assertGenericListResponseSuccess(withdrawalAddresses);
        testWithdrawalCryptoAddresses(withdrawalAddresses);
    }

    @Test
    public void withdrawalAddressesByCurrencyId(){
        WithdrawalCryptoAddresses withdrawalAddresses = cobinhoodClient.getWithdrawalAddresses(CURRENCY_ID);
        TestingUtils.assertGenericListResponseSuccess(withdrawalAddresses);
        testWithdrawalCryptoAddresses(withdrawalAddresses);
    }

    @Test
    public void withdrawalAddressesByCurrency(){
        WithdrawalCryptoAddresses withdrawalAddresses = cobinhoodClient.getWithdrawalAddresses(CURRENCY);
        TestingUtils.assertGenericListResponseSuccess(withdrawalAddresses);
        testWithdrawalCryptoAddresses(withdrawalAddresses);
    }

    @Test
    public void withdrawalAddressesFailsWithWrongCurrency(){
        WithdrawalCryptoAddresses withdrawalAddresses = cobinhoodClient.getWithdrawalAddresses("LADYGAGA");
        assertFalse(withdrawalAddresses.isSuccess());
        assertEquals(withdrawalAddresses.getError(),"parameter_error");
    }

    private void testDepositCryptoAddresses(DepositCryptoAddresses cryptoAddresses){
        for (DepositCryptoAddress cryptoAddress : cryptoAddresses) {
            assertNotNull(cryptoAddress);
            assertNotNull(cryptoAddress.getAddress());
            assertNotNull(cryptoAddress.getType());
            assertNotNull(cryptoAddress.getCurrency());
            assertNotNull(cryptoAddress.getCreatedAt());
        }
    }

    @Test
    public void depositAddresses(){
        DepositCryptoAddresses depositAddresses = cobinhoodClient.getDepositAddresses();
        TestingUtils.assertGenericListResponseSuccess(depositAddresses);
        testDepositCryptoAddresses(depositAddresses);
    }

    @Test
    public void depositAddressesByCurrencyId(){
        DepositCryptoAddresses depositAddresses = cobinhoodClient.getDepositAddresses(CURRENCY_ID);
        TestingUtils.assertGenericListResponseSuccess(depositAddresses);
        testDepositCryptoAddresses(depositAddresses);
    }

    @Test
    public void depositAddressesByCurrency(){
        DepositCryptoAddresses depositAddresses = cobinhoodClient.getDepositAddresses(CURRENCY);
        TestingUtils.assertGenericListResponseSuccess(depositAddresses);
        testDepositCryptoAddresses(depositAddresses);
    }

    @Test
    public void depositAddressesFailsWithWrongCurrency(){
        DepositCryptoAddresses depositAddresses = cobinhoodClient.getDepositAddresses("LADYGAGA");
        assertFalse(depositAddresses.isSuccess());
        assertEquals(depositAddresses.getError(),"parameter_error");
    }


    private void testLedger(Ledger ledgerEntries){
        for (LedgerEntry ledgerEntry : ledgerEntries) {
            assertNotNull(ledgerEntry);
            assertNotNull(ledgerEntry.getAction());
            assertNotNull(ledgerEntry.getAmount());
            assertNotNull(ledgerEntry.getBalance());
            assertNotNull(ledgerEntry.getCurrency());
            assertNotNull(ledgerEntry.getTimestamp());
            assertNotNull(ledgerEntry.getType());

            int ids = 0;
            if(ledgerEntry.getDepositId() != null)
                ids++;
            if(ledgerEntry.getWithdrawalId() != null)
                ids++;
            if(ledgerEntry.getFiatDepositId() != null)
                ids++;
            if(ledgerEntry.getFiatWithdrawalId() != null)
                ids++;
            if(ledgerEntry.getTradeId() != null)
                ids++;
            assertEquals(ids,1);
        }
    }

    @Test
    public void getLedger(){
        Ledger ledger = cobinhoodClient.getLedger();
        TestingUtils.assertGenericListResponseSuccess(ledger);
        testLedger(ledger);
    }

    @Test
    public void getLedgerByCurrencyId(){
        Ledger ledger = cobinhoodClient.getLedger(CURRENCY_ID);
        TestingUtils.assertGenericListResponseSuccess(ledger);
        testLedger(ledger);
    }

    @Test
    public void getLedgerByCurrency(){
        Ledger ledger = cobinhoodClient.getLedger(CURRENCY);
        TestingUtils.assertGenericListResponseSuccess(ledger);
        testLedger(ledger);
    }

    @Test
    public void getLedgerFailsWithWrongCurrency(){
        Ledger ledger = cobinhoodClient.getLedger("LADYGAGA");
        assertFalse(ledger.isSuccess());
        assertEquals(ledger.getError(),"parameter_error");
    }

    private void testDeposit(Deposit deposit){
        assertNotNull(deposit.getAmount());
        assertNotNull(deposit.getCompletedAt());
        assertNotNull(deposit.getConfirmations());
        assertNotNull(deposit.getCreatedAt());
        assertNotNull(deposit.getCurrency());
        assertNotNull(deposit.getDepositId());
        assertNotNull(deposit.getFee());
        assertNotNull(deposit.getFromAddress());
        assertNotNull(deposit.getRequiredConfirmations());
        assertNotNull(deposit.getStatus());
        assertNotNull(deposit.getTxHash());
        assertNotNull(deposit.getUserId());
    }

    private void testDepositHistory(DepositHistory deposits){
        for (Deposit deposit : deposits) {
            testDeposit(deposit);
        }
    }

    @Test
    public void getDeposit(){
        Deposit deposit = cobinhoodClient.getDeposit(DEPOSIT_ID);
        TestingUtils.assertGenericResponseSuccess(deposit);
        testDeposit(deposit);
    }

    @Test
    public void getDepositHistory(){
        DepositHistory depositHistory = cobinhoodClient.getDepositHistory();
        TestingUtils.assertGenericListResponseSuccess(depositHistory);
        testDepositHistory(depositHistory);
    }

    @Test
    public void getDepositHistoryByCurrencyId(){
        DepositHistory depositHistory = cobinhoodClient.getDepositHistory(CURRENCY_ID);
        TestingUtils.assertGenericListResponseSuccess(depositHistory);
        testDepositHistory(depositHistory);
    }

    @Test
    public void getDepositHistoryByCurrency(){
        DepositHistory depositHistory = cobinhoodClient.getDepositHistory(CURRENCY);
        TestingUtils.assertGenericListResponseSuccess(depositHistory);
        testDepositHistory(depositHistory);
    }


    private void testWithdrawal(Withdrawal withdrawal){
        assertNotNull(withdrawal.getAmount());
        assertNotNull(withdrawal.getCompletedAt());
        assertNotNull(withdrawal.getConfirmations());
        assertNotNull(withdrawal.getCreatedAt());
        assertNotNull(withdrawal.getCurrency());
        assertNotNull(withdrawal.getWithdrawalId());
        assertNotNull(withdrawal.getFee());
        assertNotNull(withdrawal.getToAddress());
        assertNotNull(withdrawal.getRequiredConfirmations());
        assertNotNull(withdrawal.getStatus());
        assertNotNull(withdrawal.getTxHash());
        assertNotNull(withdrawal.getUserId());
        assertNotNull(withdrawal.getUpdatedAt());
        assertNotNull(withdrawal.getSentAt());
    }

    private void testWithdrawalHistory(WithdrawalHistory withdrawals){
        for (Withdrawal withdrawal : withdrawals) {
            testWithdrawal(withdrawal);
        }
    }

    @Test
    public void getWithdrawal(){
        Withdrawal withdrawal = cobinhoodClient.getWithdrawal(WITHDRAWAL_ID);
        TestingUtils.assertGenericResponseSuccess(withdrawal);
        testWithdrawal(withdrawal);
    }

    @Test
    public void getWithdrawalHistory(){
        WithdrawalHistory withdrawalHistory = cobinhoodClient.getWithdrawalHistory();
        TestingUtils.assertGenericListResponseSuccess(withdrawalHistory);
        testWithdrawalHistory(withdrawalHistory);
    }

    @Test
    public void getWithdrawalHistoryByCurrencyId(){
        WithdrawalHistory withdrawalHistory = cobinhoodClient.getWithdrawalHistory(CURRENCY_ID);
        TestingUtils.assertGenericListResponseSuccess(withdrawalHistory);
        testWithdrawalHistory(withdrawalHistory);
    }

    @Test
    public void getWithdrawalHistoryByCurrency(){
        WithdrawalHistory withdrawalHistory = cobinhoodClient.getWithdrawalHistory(CURRENCY);
        TestingUtils.assertGenericListResponseSuccess(withdrawalHistory);
        testWithdrawalHistory(withdrawalHistory);
    }
}
