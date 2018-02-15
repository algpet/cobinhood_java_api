package com.cobinhood;

import org.junit.BeforeClass;
import org.junit.Test;
import com.cobinhood.responses.entity.*;
import com.cobinhood.responses.market.*;
import com.cobinhood.responses.system.SystemInfo;
import com.cobinhood.responses.system.SystemTime;

import java.util.List;
import java.util.Map;


import static org.junit.Assert.*;

public class CobinhoodPublicClientTest {

    private CobinhoodClient cobinhoodClient = new CobinhoodClient();

    private static String COBETH = "COB-ETH";
    private static String BADPAIR = "LADY-GAGA";

    private static TradingPair COBETHPAIR;

    @BeforeClass
    public static void beforeClass(){
        COBETHPAIR = new TradingPair();
        COBETHPAIR.setId(COBETH);
    }

    @Test
    public void systemInfo(){
        SystemInfo systemInfo = cobinhoodClient.getSystemInfo();
        TestingUtils.assertGenericResponseSuccess(systemInfo);
        assertNotNull(systemInfo.getPhase());
        assertNotNull(systemInfo.getRevision());
    }

    @Test
    public void systemTime(){
        SystemTime systemTime = cobinhoodClient.getSystemTime();
        TestingUtils.assertGenericResponseSuccess(systemTime);
        assertNotNull(systemTime.getTime());
    }

    @Test
    public void marketCurrencies(){
        Currencies currencies = cobinhoodClient.getMarketCurrencies();
        TestingUtils.assertGenericListResponseSuccess(currencies);

        for (Currency currency : currencies) {
            assertNotNull(currency);
            assertNotNull(currency.getCurrency());
            assertNotNull(currency.getDepositFee());
            assertNotNull(currency.getMinUnit());
            assertNotNull(currency.getName());
            assertNotNull(currency.getWithdrawalFee());
        }
    }

    @Test
    public void marketTradingPairs(){
        TradingPairs tradingPairs = cobinhoodClient.getMarketTradingPairs();
        TestingUtils.assertGenericListResponseSuccess(tradingPairs);

        for (TradingPair tradingPair : tradingPairs) {
            assertNotNull(tradingPair);
            assertNotNull(tradingPair.getId());
            assertNotNull(tradingPair.getBaseCurrencyId());
            assertNotNull(tradingPair.getQuoteCurrencyId());
            assertNotNull(tradingPair.getBaseMinSize());
            assertNotNull(tradingPair.getBaseMaxSize());
            assertNotNull(tradingPair.getQuoteIncrement());
        }
    }

    @Test
    public void tradingPairStatistics(){
        TradingPairStatistics tradingPairStatistics = cobinhoodClient.getMarketTradingPairStatistics();
        TestingUtils.assertGenericResponseSuccess(tradingPairStatistics);

        Map<String, TradingPairStat> statistics = tradingPairStatistics.getStatistics();
        assertNotNull(statistics);
        assertTrue(statistics.size() > 0);

        for (Map.Entry<String, TradingPairStat> stringTradingPairStatEntry : statistics.entrySet()) {
            assertNotNull(stringTradingPairStatEntry.getKey());
            TradingPairStat tradingPairStat = stringTradingPairStatEntry.getValue();
            assertNotNull(tradingPairStat);
            assertNotNull(tradingPairStat.getId());
            assertNotNull(tradingPairStat.getBaseVolume());
            assertNotNull(tradingPairStat.getQuoteVolume());
            assertNotNull(tradingPairStat.getHigh24hours());
            assertNotNull(tradingPairStat.getLow24hours());
            assertNotNull(tradingPairStat.getPercentChanged24hours());
            assertNotNull(tradingPairStat.getLowestAsk());
            assertNotNull(tradingPairStat.getHighestBid());
            assertNotNull(tradingPairStat.getLastPrice());
        }
    }

    private void validateRecentTrades(RecentTrades recentTrades){
        TestingUtils.assertGenericListResponseSuccess(recentTrades);
        for (Trade recentTrade : recentTrades) {
            assertNotNull(recentTrade);
            assertNotNull(recentTrade.getUuid());
            assertNotNull(recentTrade.getTimestamp());
            assertNotNull(recentTrade.getMakerSide());
            assertNotNull(recentTrade.getPrice());
            assertNotNull(recentTrade.getSize());
            assertNotNull(recentTrade.getAskOrderId());
            assertNotNull(recentTrade.getBidOrderId());
        }
    }

    @Test
    public void recentTradesByTradingPairId(){
        RecentTrades recentTrades = cobinhoodClient.getRecentTrades(COBETH);
        validateRecentTrades(recentTrades);
    }

    @Test
    public void recentTradesByTradingPair(){
        RecentTrades recentTrades = cobinhoodClient.getRecentTrades(COBETHPAIR);
        validateRecentTrades(recentTrades);
    }

    @Test
    public void recentTradesFailsWithInvalidTradingPairId(){
        RecentTrades recentTrades = cobinhoodClient.getRecentTrades(BADPAIR);
        assertFalse(recentTrades.isSuccess());
        assertTrue(recentTrades.getError().equals("trading_pair_not_found"));
    }

    private void validateTicker(Ticker ticker){
        TestingUtils.assertGenericResponseSuccess(ticker);
        assertNotNull(ticker.getTradingPairId());
        assertNotNull(ticker.getTimestamp());
        assertNotNull(ticker.getHigh24hours());
        assertNotNull(ticker.getLow24hours());
        assertNotNull(ticker.getHigh24hours());
        assertNotNull(ticker.getVolume24hours());
        assertNotNull(ticker.getHighestBid());
        assertNotNull(ticker.getLowestAsk());
        assertNotNull(ticker.getLastPrice());
    }

    @Test
    public void tickerByTradingPairId(){
        Ticker ticker = cobinhoodClient.getTicker(COBETH);
        validateTicker(ticker);
    }

    @Test
    public void tickerByTradingPair(){
        Ticker ticker = cobinhoodClient.getTicker(COBETHPAIR);
        validateTicker(ticker);
        assertTrue(ticker.getTradingPairId().equals(COBETHPAIR.getId()));
    }

    @Test
    public void tickerFailsWithInvalidTradingPairId(){
        Ticker ticker = cobinhoodClient.getTicker(BADPAIR);
        assertFalse(ticker.isSuccess());
        assertTrue(ticker.getError().equals("trading_pair_not_found"));
    }


    private void validateOrderBook(OrderBook orderBook){
        TestingUtils.assertGenericResponseSuccess(orderBook);
        List<OrderBookEntry> asks = orderBook.getAsks();
        List<OrderBookEntry> bids = orderBook.getBids();
        assertNotNull(asks);
        assertNotNull(bids);
        for (OrderBookEntry ask : asks) {
            assertNotNull(ask);
            assertNotNull(ask.getPrice());
            assertTrue(ask.getSide().equals("asks"));
            assertNotNull(ask.getVolume());
        }
        for (OrderBookEntry bid : bids) {
            assertNotNull(bid);
            assertNotNull(bid.getPrice());
            assertTrue(bid.getSide().equals("bids"));
            assertNotNull(bid.getVolume());
        }
    }

    @Test
    public void orderBookByTradingPairId(){
        OrderBook orderBook = cobinhoodClient.getOrderBook(COBETH);
        validateOrderBook(orderBook);
    }
    @Test
    public void orderBookByTradingPair(){
        OrderBook orderBook = cobinhoodClient.getOrderBook(COBETHPAIR);
        validateOrderBook(orderBook);
    }

    @Test
    public void orderBookWithInvalidTradingPairId(){
        OrderBook orderBook = cobinhoodClient.getOrderBook(BADPAIR);
        assertFalse(orderBook.isSuccess());
        assertTrue(orderBook.getError().equals("trading_pair_not_found"));
    }

    @Test
    public void orderBookLimit(){
        OrderBook orderBook = cobinhoodClient.getOrderBook(COBETH);
        int asks = orderBook.getAsks().size();
        int bids = orderBook.getBids().size();

        int useLimit = Math.min(asks,bids) - 1;
        if(useLimit > 10)
            useLimit = 3;
        if(useLimit < 1)
            fail("Unable to check order book limit param");

        OrderBook trimmedOrderBook = cobinhoodClient.getOrderBookBuilder()
                .tradingPairId(COBETH)
                .limit(useLimit)
                .getOrderBook();

        validateOrderBook(trimmedOrderBook);
        assertTrue(trimmedOrderBook.getBids().size() == useLimit);
        assertTrue(trimmedOrderBook.getAsks().size() == useLimit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void orderBookFailsWithArgumentConflict(){
        cobinhoodClient.getOrderBookBuilder()
                .tradingPairId(COBETH)
                .tradingPair(COBETHPAIR)
                .getOrderBook();
    }

    @Test
    public void orderBookCallFailsWithInvalidTradingPairId(){
        OrderBook orderBook = cobinhoodClient.getOrderBook(BADPAIR);
        assertFalse(orderBook.isSuccess());
        assertTrue(orderBook.getError().equals("trading_pair_not_found"));
    }


    private void validateCandles(Candles candles, String tickerId, Candle.Timeframe timeframe){
        TestingUtils.assertGenericListResponseSuccess(candles);
        for (Candle candle : candles) {
            assertNotNull(candle);
            assertNotNull(candle.getOpen());
            assertNotNull(candle.getLow());
            assertNotNull(candle.getHigh());
            assertNotNull(candle.getClose());
            assertNotNull(candle.getVolume());
            assertNotNull(candle.getTimeframe());
            assertNotNull(candle.getTimestamp());
            assertTrue(candle.getTradingPairId().equals(tickerId));
            assertTrue(candle.getTimeframe().equals(timeframe.getTimeframe()));
        }
    }

    @Test
    public void candlesByTradingPairId(){
        String tickerId = COBETH;
        Candle.Timeframe timeframe = Candle.Timeframe.TIMEFRAME_1_HOUR;
        Candles candles = cobinhoodClient.getCandles(tickerId,timeframe);
        validateCandles(candles,tickerId,timeframe);
    }

    @Test
    public void candlesByTradingPair(){
        String tickerId = COBETHPAIR.getId();
        Candle.Timeframe timeframe = Candle.Timeframe.TIMEFRAME_1_DAY;
        Candles candles = cobinhoodClient.getCandles(COBETHPAIR,timeframe);
        validateCandles(candles,tickerId,timeframe);
    }

    @Test
    public void candlesWithTimeboundsSet(){
        Candle.Timeframe timeframe = Candle.Timeframe.TIMEFRAME_1_MINUTE;
        Candles candles = cobinhoodClient.getCandles(COBETH,timeframe);
        Long minTimestamp;
        Long maxTimestamp;
        Long difference;
        Long useMin;
        Long useMax;
        minTimestamp = candles.getItems().get(0).getTimestamp();
        maxTimestamp = candles.getItems().get(candles.getItems().size() - 1).getTimestamp();

        difference = maxTimestamp - minTimestamp;

        useMin = Math.round(minTimestamp + (difference * 0.45));
        useMax = Math.round(maxTimestamp - (difference * 0.45));

        Candles candlesForPeriod = cobinhoodClient.getCandlesBuilder()
                .tradingPairId(COBETH)
                .timeframe(timeframe)
                .startTime(useMin)
                .endTime(useMax)
                .getCandles();
        validateCandles(candlesForPeriod,COBETH, timeframe);

        assertTrue(candlesForPeriod.getItems().size() < candles.getItems().size());
        assertTrue(candlesForPeriod.getItems().get(0).getTimestamp() <= useMax);
        assertTrue(candlesForPeriod.getItems().get(candlesForPeriod.getItems().size() - 1).getTimestamp() >= useMin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void candlesFailsWhenArgumentConflict(){
        cobinhoodClient.getCandlesBuilder()
                .tradingPairId(COBETH)
                .tradingPair(COBETHPAIR)
                .getCandles();
    }

    @Test(expected = IllegalArgumentException.class)
    public void candlesFailsWhenNoTradingPairIdAndNoTradingPair(){
        cobinhoodClient.getCandlesBuilder().getCandles();
    }

    @Test
    public void candlesFailsWithInvalidTradingPairId(){
        Candles candles = cobinhoodClient.getCandles(BADPAIR,Candle.Timeframe.TIMEFRAME_1_MINUTE);
        assertFalse(candles.isSuccess());
        assertTrue(candles.getError().equals("trading_pair_not_found"));
    }
}