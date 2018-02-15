package com.cobinhood;

import com.cobinhood.responses.entity.Currency;
import com.cobinhood.responses.trading.Order;
import com.cobinhood.responses.trading.Orders;
import com.cobinhood.responses.trading.Trade;
import com.cobinhood.responses.trading.Trades;
import com.cobinhood.responses.wallet.*;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import lombok.Builder;
import com.cobinhood.responses.*;
import com.cobinhood.responses.entity.Candle;
import com.cobinhood.responses.entity.TradingPair;
import com.cobinhood.responses.market.*;
import com.cobinhood.responses.system.SystemInfo;
import com.cobinhood.responses.system.SystemTime;
import org.json.JSONObject;


public class CobinhoodClient {

    private static final String LIVE_API = "https://api.cobinhood.com";

    private static final String SYSTEM_INFO = "/v1/system/info";
    private static final String SYSTEM_TIME = "/v1/system/time";
    private static final String MARKET_CURRENCIES = "/v1/market/currencies";
    private static final String MARKET_TRADING_PAIRS = "/v1/market/trading_pairs";
    private static final String MARKET_STATS = "/v1/market/stats";
    private static final String MARKET_TICKER = "/v1/market/tickers/{pathParam.tradingPair}";
    private static final String MARKET_RECENT_TRADES = "/v1/market/trades/{pathParam.tradingPair}";
    private static final String MARKET_ORDER_BOOK = "/v1/market/orderbooks/{pathParam.tradingPair}";

    private static final String CHART_CANDLES = "/v1/chart/candles/{pathParam.tradingPair}";

    private static final String TRADING_ORDERS = "/v1/trading/orders";
    private static final String TRADING_ORDERS_HISTORY = "/v1/trading/order_history";
    private static final String TRADING_ORDER = "/v1/trading/orders/{pathParam.orderId}";

    private static final String TRADING_TRADE = "/v1/trading/trades/{pathParam.tradeId}";
    private static final String TRADING_ORDER_TRADES = "/v1/trading/orders/{pathParam.orderId}/trades";
    private static final String TRADING_TRADE_HISTORY = "/v1/trading/trades";

    private static final String WALLET_DEPOSIT_ADDRESSES = "/v1/wallet/deposit_addresses";
    private static final String WALLET_WITHDRAWAL_ADDRESSES = "/v1/wallet/withdrawal_addresses";
    private static final String WALLET_LEDGER = "/v1/wallet/ledger";

    private static final String WALLET_DEPOSIT = "/v1/wallet/deposits/{pathParam.depositId}";
    private static final String WALLET_WITHDRAWAL = "/v1/wallet/withdrawals/{pathParam.withdrawalId}";
    private static final String WALLET_WITHDRAWALS = "/v1/wallet/withdrawals";
    private static final String WALLET_DEPOSITS = "/v1/wallet/deposits";

    private String apiKey = null;
    private boolean isLive = true;
    private boolean consoleLog = false;
    private String logFile = "cobinhood_api.log";
    private boolean log_responses = false;
    private String apiPath;

    public CobinhoodClient(){
        this.apiPath = LIVE_API;
    }
    public CobinhoodClient(String apiKey){
        this();
        this.apiKey = apiKey;
    }

    public SystemInfo getSystemInfo(){
        return new CobinhoodHttpRequest(HttpMethod.GET, SYSTEM_INFO)
                .build(SystemInfo.class);
    }

    public SystemTime getSystemTime(){
        return new CobinhoodHttpRequest(HttpMethod.GET, SYSTEM_TIME)
                .build(SystemTime.class);
    }

    public Currencies getMarketCurrencies(){
        return new CobinhoodHttpRequest(HttpMethod.GET, MARKET_CURRENCIES)
                .build(Currencies.class);
    }

    public TradingPairs getMarketTradingPairs(){
        return new CobinhoodHttpRequest(HttpMethod.GET, MARKET_TRADING_PAIRS)
                .build(TradingPairs.class);
    }

    public TradingPairStatistics getMarketTradingPairStatistics(){
        return new CobinhoodHttpRequest(HttpMethod.GET, MARKET_STATS)
                .build(TradingPairStatistics.class);
    }


    public Ticker getTicker(String tradingPairId){
        return  new CobinhoodHttpRequest(HttpMethod.GET, MARKET_TICKER)
                .routeParam("pathParam.tradingPair", tradingPairId)
                .build(Ticker.class);
    }
    public Ticker getTicker(TradingPair tradingPair){
        return this.getTicker(tradingPair.getId());
    }


    public RecentTrades getRecentTrades(String tradingPairId){
        return new CobinhoodHttpRequest(HttpMethod.GET, MARKET_RECENT_TRADES)
                .routeParam("pathParam.tradingPair", tradingPairId)
                .build(RecentTrades.class);
    }
    public RecentTrades getRecentTrades(TradingPair tradingPair){
        return this.getRecentTrades(tradingPair.getId());
    }


    private String consolidateTradingPairParams(String tradingPairId,TradingPair tradingPair){
        if((tradingPairId == null) == (tradingPair == null))
            throw new IllegalArgumentException("Both or neither tradingPairId and TradingPair are set");
        if (tradingPair != null)
            tradingPairId = tradingPair.getId();
        return tradingPairId;
    }


    @Builder(builderMethodName = "getOrderBookBuilder",buildMethodName = "getOrderBook")
    private OrderBook getOrderBook(String tradingPairId,TradingPair tradingPair,Integer limit){
        tradingPairId = this.consolidateTradingPairParams(tradingPairId,tradingPair);
        return new CobinhoodHttpRequest(HttpMethod.GET, MARKET_ORDER_BOOK)
                .routeParam("pathParam.tradingPair", tradingPairId)
                .queryString("limit", limit)
                .build(OrderBook.class);
    }
    public OrderBook getOrderBook(String tradingPairId){
        return getOrderBook(tradingPairId,null,null);
    }
    public OrderBook getOrderBook(TradingPair tradingPair){
        return getOrderBook(null,tradingPair,null);
    }


    @Builder(builderMethodName = "getCandlesBuilder",buildMethodName = "getCandles")
    private Candles getCandles(String tradingPairId,TradingPair tradingPair,Candle.Timeframe timeframe ,Long startTime,Long endTime){
        tradingPairId = this.consolidateTradingPairParams(tradingPairId,tradingPair);

        String timeFrameValue = null;
        if (timeframe != null)
            timeFrameValue = timeframe.getTimeframe();

        CobinhoodHttpRequest cobinhoodHttpRequest = new CobinhoodHttpRequest(HttpMethod.GET, CHART_CANDLES)
                .routeParam("pathParam.tradingPair", tradingPairId)
                .queryString("timeframe", timeFrameValue)
                .queryString("start_time", startTime)
                .queryString("end_time", endTime);

        return cobinhoodHttpRequest.build(Candles.class);
    }

    public Candles getCandles(String tradingPairId,Candle.Timeframe timeframe){
        return this.getCandles(tradingPairId,null,timeframe,null,null);
    }
    public Candles getCandles(TradingPair tradingPair,Candle.Timeframe timeframe){
        return this.getCandles(null,tradingPair,timeframe,null,null);
    }

    public Order getOrder(String orderId){
        return new CobinhoodHttpRequest(HttpMethod.GET,TRADING_ORDER)
                .routeParam("pathParam.orderId",orderId)
                .withApiKey(this.apiKey)
                .build(Order.class);
    }

    private Orders getPastOrPresentOrders(String tradingPairId, String ordersEndpoint){
        return new CobinhoodHttpRequest(HttpMethod.GET,ordersEndpoint)
                .queryString("trading_pair_id",tradingPairId)
                .withApiKey(this.apiKey)
                .build(Orders.class);
    }

    public Orders getOrders(String tradingPairId){
        return getPastOrPresentOrders(tradingPairId,TRADING_ORDERS);
    }
    public Orders getOrders(TradingPair tradingPair){
        return getPastOrPresentOrders(tradingPair.getId(),TRADING_ORDERS);
    }
    public Orders getOrders(){
        String tradingPairId = null;
        return getPastOrPresentOrders(tradingPairId,TRADING_ORDERS);
    }

    public Orders getOrderHistory(String tradingPairId){
        return getPastOrPresentOrders(tradingPairId,TRADING_ORDERS_HISTORY);
    }
    public Orders getOrderHistory(TradingPair tradingPair){
        return getPastOrPresentOrders(tradingPair.getId(),TRADING_ORDERS_HISTORY);
    }
    public Orders getOrderHistory(){
        String tickerId = null;
        return getPastOrPresentOrders(tickerId,TRADING_ORDERS_HISTORY);
    }

    public Trade getTrade(String tradeId){
        return new CobinhoodHttpRequest(HttpMethod.GET,TRADING_TRADE)
                .routeParam("pathParam.tradeId",tradeId)
                .withApiKey(this.apiKey)
                .build(Trade.class);
    }

    public Trades getOrderTrades(String orderId){
        return new CobinhoodHttpRequest(HttpMethod.GET,TRADING_ORDER_TRADES)
                .routeParam("pathParam.orderId",orderId)
                .withApiKey(this.apiKey)
                .build(Trades.class);
    }

    public Trades getOrderTrades(Order order){
        return getOrderTrades(order.getUuid());
    }

    public Trades getTradeHistory(String tradingPairId){
        return new CobinhoodHttpRequest(HttpMethod.GET,TRADING_TRADE_HISTORY)
                .queryString("trading_pair_id",tradingPairId)
                .withApiKey(this.apiKey)
                .build(Trades.class);
    }

    public Trades getTradeHistory(TradingPair tradingPair){
        return getTradeHistory(tradingPair.getId());
    }

    public Trades getTradeHistory(){
        String tradingPairId = null;
        return getTradeHistory(tradingPairId);
    }


    public Order placeOrder(Order order){
        return new CobinhoodHttpRequest(HttpMethod.POST,TRADING_ORDERS)
                .withJsonField("trading_pair_id",order.getTradingPair())
                .withJsonField("side",order.getSide())
                .withJsonField("type",order.getType())
                .withJsonField("size",order.getSize().toString())
                .withJsonField("price",order.getPrice().toString())
                .asJsonBody()
                .withApiKey(this.apiKey)
                .withNonceProvided()
                .build(Order.class);
    }

    public GenericResponseImpl modifyOrder(Order order){
        if(order.getUuid() == null || order.getUuid().equals(""))
            throw new IllegalArgumentException("Wrong order UUID");

        return new CobinhoodHttpRequest(HttpMethod.PUT,TRADING_ORDER)
                .routeParam("pathParam.orderId",order.getUuid())
                    .withJsonField("size",order.getSize().toString())
                    .withJsonField("price",order.getPrice().toString())
                .asJsonBody()
                .withApiKey(this.apiKey)
                .withNonceProvided()
                .build(GenericResponseImpl.class);
    }

    public GenericResponseImpl cancelOrder(String orderId){
        if(orderId == null || orderId.equals(""))
            throw new IllegalArgumentException("Wrong order UUID");
        return new CobinhoodHttpRequest(HttpMethod.DELETE,TRADING_ORDER)
                .routeParam("pathParam.orderId",orderId)
                .withApiKey(this.apiKey)
                .withNonceProvided()
                .build(GenericResponseImpl.class);
    }

    public GenericResponseImpl cancelOrder(Order order){
        return this.cancelOrder(order.getUuid());
    }


    public WithdrawalCryptoAddresses getWithdrawalAddresses(String currencyId){
        return new  CobinhoodHttpRequest(HttpMethod.GET,WALLET_WITHDRAWAL_ADDRESSES)
                .queryString("currency",currencyId)
                .withApiKey(this.apiKey)
                .build(WithdrawalCryptoAddresses.class);
    }

    public WithdrawalCryptoAddresses getWithdrawalAddresses(Currency currency){
        return getWithdrawalAddresses(currency.getCurrency());
    }

    public WithdrawalCryptoAddresses getWithdrawalAddresses(){
        String currencyId = null;
        return getWithdrawalAddresses(currencyId);
    }

    public DepositCryptoAddresses getDepositAddresses(String currencyId){
        return new  CobinhoodHttpRequest(HttpMethod.GET,WALLET_DEPOSIT_ADDRESSES)
                .queryString("currency",currencyId)
                .withApiKey(this.apiKey)
                .build(DepositCryptoAddresses.class);
    }

    public DepositCryptoAddresses getDepositAddresses(Currency currency){
        return getDepositAddresses(currency.getCurrency());
    }

    public DepositCryptoAddresses getDepositAddresses(){
        String currencyId = null;
        return getDepositAddresses(currencyId);
    }

    public Ledger getLedger(String currencyId){
        return new CobinhoodHttpRequest(HttpMethod.GET,WALLET_LEDGER)
                .queryString("currency",currencyId)
                .withApiKey(this.apiKey)
                .build(Ledger.class);
    }

    public Ledger getLedger(Currency currency){
        return getLedger(currency.getCurrency());
    }

    public Ledger getLedger(){
        String currencyId = null;
        return this.getLedger(currencyId);
    }

    public Deposit getDeposit(String depositId){
        return new CobinhoodHttpRequest(HttpMethod.GET,WALLET_DEPOSIT)
                .routeParam("pathParam.depositId",depositId)
                .withApiKey(this.apiKey)
                .build(Deposit.class);
    }

    public DepositHistory getDepositHistory(String currencyId){
        return new CobinhoodHttpRequest(HttpMethod.GET,WALLET_DEPOSITS)
                .queryString("currency",currencyId)
                .withApiKey(this.apiKey)
                .build(DepositHistory.class);
    }

    public DepositHistory getDepositHistory(Currency currency){
        return getDepositHistory(currency.getCurrency());
    }

    public DepositHistory getDepositHistory(){
        String currencyId = null;
        return getDepositHistory(currencyId);
    }

    public Withdrawal getWithdrawal(String withdrawalId){
        return new CobinhoodHttpRequest(HttpMethod.GET,WALLET_WITHDRAWAL)
                .routeParam("pathParam.withdrawalId",withdrawalId)
                .withApiKey(this.apiKey)
                .build(Withdrawal.class);
    }

    public WithdrawalHistory getWithdrawalHistory(String currencyId){
        return new CobinhoodHttpRequest(HttpMethod.GET,WALLET_WITHDRAWALS)
                .queryString("currency",currencyId)
                .withApiKey(this.apiKey)
                .build(WithdrawalHistory.class);
    }

    public WithdrawalHistory getWithdrawalHistory(Currency currency){
        return getWithdrawalHistory(currency.getCurrency());
    }

    public WithdrawalHistory getWithdrawalHistory(){
        String currencyId = null;
        return getWithdrawalHistory(currencyId);
    }
}


