# Java wrapper for Cobinhood REST API

Following wrapper created based on version of API documentation published at February 2018. Cover all endpoints provided by official documentation.
- tickers , orderbook , market stats with public API.
- create, alter and cancel orders.
- viewing your orders and fills
- viewing your wallets and transactions

### Prerequests
This project use few dependencies. Those are
- unirest
- lombok
- apache commons
- junit

use maven to acquire them or .. get them to your classpath the way you like

### Usage

Best way to understand basic use-cases of API is looking at unit-tests. They scope all together
ways you could interact with project.  But for those who love readme's there are few sample usages down below.


#### Get ticker for a given market pair
```
        CobinhoodClient cobinhoodClient = new CobinhoodClient();
        Ticker ticker1 = cobinhoodClient.getTicker("BTC-USD");
        Ticker ticker2 = cobinhoodClient.getTicker("COB-ETH");
        System.out.println(ticker1);
        System.out.println(ticker2);
```
produced output
```
Ticker(tradingPairId=BTC-USD, lastPrice=9550.0, lowestAsk=9550.0, highestBid=9550.3, high24hours=9550.0, low24hours=9550.0, open24hours=9550.0, volume24hours=0.0, timestamp=1518722880000)
Ticker(tradingPairId=COB-ETH, lastPrice=2.098E-4, lowestAsk=2.09E-4, highestBid=2.077E-4, high24hours=2.52E-4, low24hours=1.817E-4, open24hours=2.415E-4, volume24hours=1455918.82281455, timestamp=1518722880000)
``` 

#### Get orderbook for a given market pair
```
        CobinhoodClient cobinhoodClient = new CobinhoodClient();
        OrderBook orderBook1 = cobinhoodClient.getOrderBook("COB-ETH");
        OrderBook orderBook2 = cobinhoodClient
                .getOrderBookBuilder()
                .tradingPairId("BTC-USD")
                .limit(2)
                .getOrderBook();
        System.out.println(orderBook1);
        System.out.println(orderBook2);
        for (OrderBookEntry orderBookEntry : orderBook2.getBids()) {
            System.out.println(orderBookEntry);
        }
```
produced output
```
OrderBook{number of bids=50, number of asks=50} GenericResponse(success=true, message=null, error=null)
OrderBook{number of bids=2, number of asks=0} GenericResponse(success=true, message=null, error=null)
OrderBookEntry(side=bids, price=9550.3, volume=0.00205988)
OrderBookEntry(side=bids, price=9550.0, volume=0.002034)
```

#### Get list of currencies and pairs
```
        CobinhoodClient cobinhoodClient = new CobinhoodClient();
        Currencies marketCurrencies = cobinhoodClient.getMarketCurrencies();
        TradingPairs marketTradingPairs = cobinhoodClient.getMarketTradingPairs();
        System.out.println(marketCurrencies);
        System.out.println(marketTradingPairs);
        for (Currency marketCurrency : marketCurrencies) {
            if(marketCurrency.getCurrency().equals("COB"))
                System.out.println(marketCurrency);
        }
        for (TradingPair marketTradingPair : marketTradingPairs) {
            if(marketTradingPair.getId().contains("COB"))
                System.out.println(marketTradingPair);
        }
```
produced output
```
Currencies{number of items=36} GenericResponse(success=true, message=null, error=null)
TradingPairs{number of items=70} GenericResponse(success=true, message=null, error=null)
Currency(currency=COB, name=Cobinhood Token, minUnit=0.00000001, depositFee=0.0, withdrawalFee=34.63)
TradingPair(id=COB-ETH, baseCurrencyId=COB, quoteCurrencyId=ETH, baseMaxSize=4122776.277, baseMinSize=123.683, quoteIncrement=0.0000001)
TradingPair(id=COB-BTC, baseCurrencyId=COB, quoteCurrencyId=BTC, baseMaxSize=4122776.277, baseMinSize=123.683, quoteIncrement=0.00000001)
TradingPair(id=COB-USD, baseCurrencyId=COB, quoteCurrencyId=USD, baseMaxSize=4122776.277, baseMinSize=0.0, quoteIncrement=0.001)
```

#### Get candles
```
        CobinhoodClient cobinhoodClient = new CobinhoodClient();
        Candles candles = cobinhoodClient.getCandlesBuilder()
                .tradingPairId("COB-ETH")
                .timeframe(Candle.Timeframe.TIMEFRAME_3_HOURS)
                .startTime(1514750400000L)
                .endTime(1514750400000L + 18000000L)
                .getCandles();
        for (Candle candle : candles) {
            System.out.println(candle);
        }
```
produced output
```
Candle(tradingPairId=COB-ETH, timeframe=3h, timestamp=1514743200000, volume=12773.843373489999, open=2.48E-4, close=2.49E-4, high=2.49E-4, low=2.41E-4)
Candle(tradingPairId=COB-ETH, timeframe=3h, timestamp=1514754000000, volume=35073.22709709, open=2.49E-4, close=2.46E-4, high=2.49E-4, low=2.4E-4)
Candle(tradingPairId=COB-ETH, timeframe=3h, timestamp=1514764800000, volume=31544.88761817, open=2.4E-4, close=2.46E-4, high=2.46E-4, low=2.4E-4)
```

### Private API 

#### Create , modify and cancel order
```
        CobinhoodClient cobinhoodClient = new CobinhoodClient("your api key here");
        Order order = new Order();
        order.setTradingPair("COB-ETH");
        order.setSide("ask");
        order.setType("limit");
        order.setSize(135.0);
        order.setPrice(1.0);
        Order placedOrder = cobinhoodClient.placeOrder(order);
        System.out.println("placed order " + placedOrder);

        placedOrder.setPrice(placedOrder.getPrice() + 0.001);
        GenericResponse modificationResponse = cobinhoodClient.modifyOrder(placedOrder);
        System.out.println("modification response " + modificationResponse);
        Order modifiedOrder = cobinhoodClient.getOrder(placedOrder.getUuid());
        System.out.println("updated order " + modifiedOrder);

        GenericResponse cancellationResponse = cobinhoodClient.cancelOrder(placedOrder);
        System.out.println("cancelation response " + cancellationResponse);
        Order canceledOrder = cobinhoodClient.getOrder(placedOrder.getUuid());
        System.out.println("canceled order " + canceledOrder);
```
produced output
```
placed order Order(super=GenericResponse(success=true, message=null, error=null), uuid=90513279-b233-460a-967e-0610310fa93a, tradingPair=COB-ETH, side=ask, type=limit, price=1.0, size=135.0, filled=0.0, state=queued, timestamp=1518725151243, eqPrice=0.0, completedAt=null)
modification response GenericResponse(success=true, message=null, error=null)
updated order Order(super=GenericResponse(success=true, message=null, error=null), uuid=90513279-b233-460a-967e-0610310fa93a, tradingPair=COB-ETH, side=ask, type=limit, price=1.001, size=135.0, filled=0.0, state=open, timestamp=1518725151243, eqPrice=0.0, completedAt=null)
cancelation response GenericResponse(success=true, message=null, error=null)
canceled order Order(super=GenericResponse(success=true, message=null, error=null), uuid=90513279-b233-460a-967e-0610310fa93a, tradingPair=COB-ETH, side=ask, type=limit, price=1.001, size=135.0, filled=0.0, state=cancelled, timestamp=1518725151243, eqPrice=0.0, completedAt=2018-02-15T20:05:52.219912Z)
```

#### View your past or present orders. And some trades maybe ;)
```
        CobinhoodClient cobinhoodClient = new CobinhoodClient("your key here");
        Orders orders = cobinhoodClient.getOrders("COB-ETH");
        Orders orderHistory = cobinhoodClient.getOrderHistory("COB-ETH");
        System.out.println(orders);
        System.out.println(orderHistory);
        for (Order order : orders) {
            System.out.println(order);
        }
        for (Order order : orderHistory) {
            if(order.getFilled() > 0) {
                System.out.println(order);
                Trades orderTrades = cobinhoodClient.getOrderTrades(order);
                for (Trade orderTrade : orderTrades) {
                    System.out.println(orderTrade);
                }
            }
        }
```
produced output
```
Orders{number of items=1} GenericResponse(success=true, message=null, error=null)
Orders{number of items=50} GenericResponse(success=true, message=null, error=null)
Order(super=GenericResponse(success=false, message=null, error=null), uuid=c4bef88c-a620-4eb1-8db5-fa94bb788777, tradingPair=COB-ETH, side=ask, type=limit, price=1.0, size=200.0, filled=0.0, state=open, timestamp=1518725389111, eqPrice=0.0, completedAt=null)
Order(super=GenericResponse(success=false, message=null, error=null), uuid=509650a9-7ad7-4c6f-805e-c435e288c554, tradingPair=COB-ETH, side=bid, type=limit, price=2.7E-4, size=135.0, filled=135.0, state=filled, timestamp=1518289025295, eqPrice=2.7E-4, completedAt=2018-02-11T03:43:05.98026Z)
Trade(super=GenericResponse(success=false, message=null, error=null), uuid=333438c2-53be-4535-b5b6-f05419299934, makerSide=bid, askOrderId=75cfa41e-2d46-487a-8781-d32b26df5dbb, bidOrderId=509650a9-7ad7-4c6f-805e-c435e288c554, timestamp=1518320098924, price=2.7E-4, size=134.54925238)
Trade(super=GenericResponse(success=false, message=null, error=null), uuid=b08fe8c2-b29f-4991-afc4-09ee28a00e28, makerSide=bid, askOrderId=6c123773-84c6-4243-8ae2-42966aa09565, bidOrderId=509650a9-7ad7-4c6f-805e-c435e288c554, timestamp=1518320585978, price=2.7E-4, size=0.45074762)
```

#### Constructor and list of all available methods

There are bunch more methods which are not covered in this quick overview , but yet they are part of API,
and have right to be mentioned ;)
```
    //for public methods use only
    public CobinhoodClient()
    
    //for public and private methods
    public CobinhoodClient(String apiKey)
    
    //system version , build number , current server time
    public SystemInfo getSystemInfo()
    public SystemTime getSystemTime()
    
    //list of currencies , trades , and trading pair statstics
    public Currencies getMarketCurrencies()
    public TradingPairs getMarketTradingPairs()
    public TradingPairStatistics getMarketTradingPairStatistics()
    
    //tickers
    public Ticker getTicker(String tradingPairId)
    public Ticker getTicker(TradingPair tradingPair)

    // recent trades for a given pair
    public RecentTrades getRecentTrades(String tradingPairId)
    public RecentTrades getRecentTrades(TradingPair tradingPair)
    
    //orderbook
    public OrderBook getOrderBook(String tradingPairId)
    public OrderBook getOrderBook(TradingPair tradingPair)
    public getOrderBookBuilder() // for some builder opportunities
    
    //candles
    public Candles getCandles(String tradingPairId,Candle.Timeframe timeframe)
    public Candles getCandles(TradingPair tradingPair,Candle.Timeframe timeframe)
    public getCandlesBuilder() // for some builder opportunities
    
    ////// PRIVATE PART 
    
    //multiple ways to get your order(s) to you
    public Order getOrder(String orderId)
    public Orders getOrders(String tradingPairId)
    public Orders getOrders(TradingPair tradingPair)
    public Orders getOrders()
    public Orders getOrderHistory(String tradingPairId)
    public Orders getOrderHistory(TradingPair tradingPair)
    public Orders getOrderHistory()
    
    //multiple ways to review your trading history
    public Trade getTrade(String tradeId)
    public Trades getOrderTrades(String orderId)
    public Trades getOrderTrades(Order order)
    public Trades getTradeHistory(String tradingPairId)
    public Trades getTradeHistory(TradingPair tradingPair)
    public Trades getTradeHistory()
    
    // getting orders in and out
    public Order placeOrder(Order order)
    public GenericResponseImpl modifyOrder(Order order)
    public GenericResponseImpl cancelOrder(String orderId)
    public GenericResponseImpl cancelOrder(Order order)

    // list your accounts
    public WithdrawalCryptoAddresses getWithdrawalAddresses(String currencyId)
    public WithdrawalCryptoAddresses getWithdrawalAddresses(Currency currency)
    public WithdrawalCryptoAddresses getWithdrawalAddresses()
    public DepositCryptoAddresses getDepositAddresses(String currencyId)
    public DepositCryptoAddresses getDepositAddresses(Currency currency)
    public DepositCryptoAddresses getDepositAddresses()
    
    // get some view on how cryptos flow in and out
    public Deposit getDeposit(String depositId)
    public DepositHistory getDepositHistory(String currencyId)
    public DepositHistory getDepositHistory(Currency currency)
    public DepositHistory getDepositHistory()
    public Withdrawal getWithdrawal(String withdrawalId)
    public WithdrawalHistory getWithdrawalHistory(String currencyId)
    public WithdrawalHistory getWithdrawalHistory(Currency currency)
    public WithdrawalHistory getWithdrawalHistory()
    
    //transactions...
    public Ledger getLedger(String currencyId)
    public Ledger getLedger(Currency currency)
    public Ledger getLedger()
    
```


### Buy me some beer or coffee
```
ETH
0x7318932B90eB97a46DC2873D722B8B2cAeacbf92
```