package com.cobinhood;

import com.cobinhood.responses.trading.Order;
import com.cobinhood.responses.trading.Orders;
import com.cobinhood.responses.trading.Trade;
import com.cobinhood.responses.trading.Trades;
import org.junit.BeforeClass;
import com.cobinhood.responses.entity.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class CobinhoodOrdersAndTradesTest {

    private CobinhoodClient cobinhoodClient = new CobinhoodClient(ApiKey.ApiKey);
    private static String COBETH = "COB-ETH";
    private static String BADPAIR = "LADY-GAGA";
    private static TradingPair COBETHPAIR;

    private final String orderUUID = "45f01513-e482-49ce-b399-a6bc39aa9aa5";
    private final String tradeUUID = "b08fe8c2-b29f-4991-afc4-09ee28a00e28";

    @BeforeClass
    public static void beforeClass(){
        COBETHPAIR = new TradingPair();
        COBETHPAIR.setId(COBETH);
    }

    @Test
    public void authenticatedMethodFailsWhenApiKeyIsWrong(){
        CobinhoodClient cobinhoodClient = new CobinhoodClient("WRONGKEY");
        Orders orders = cobinhoodClient.getOrders();
        assertFalse(orders.isSuccess());
        assertTrue(orders.getError().equals("authentication_error"));
    }

    @Test
    public void getOrder(){
        if(orderUUID == null || orderUUID == "")
            return;
        Order order = cobinhoodClient.getOrder(orderUUID);
        TestingUtils.assertGenericResponseSuccess(order);
        assertNotNull(order);
        assertNotNull(order.getUuid());
        assertNotNull(order.getTradingPair());
        assertNotNull(order.getSide());
        assertNotNull(order.getState());
        assertNotNull(order.getType());
        assertNotNull(order.getTimestamp());
        assertNotNull(order.getSize());
        assertNotNull(order.getFilled());
        assertNotNull(order.getPrice());
        assertNotNull(order.getEqPrice());
    }

    @Test
    public void getOrderWithWrongOrderId(){
        Order order = cobinhoodClient.getOrder("bad-uuid");
        assertFalse(order.isSuccess());
        assertTrue(order.getError().equals("parameter_error"));
    }

    private void validateOrders(Orders orders, String tickerid, boolean isCompleted){
        TestingUtils.assertGenericListResponseSuccess(orders);
        for (Order order : orders) {
            assertNotNull(order);
            assertNotNull(order.getUuid());
            if(tickerid != null)
                assertEquals(order.getTradingPair(),tickerid);
            else
                assertNotNull(order.getTradingPair());
            assertNotNull(order.getSide());
            assertNotNull(order.getState());
            assertNotNull(order.getType());
            assertNotNull(order.getTimestamp());

            if(isCompleted)
                assertNotNull(order.getCompletedAt());
            else
                assertNull(order.getCompletedAt());

            assertNotNull(order.getSize());
            assertNotNull(order.getFilled());
            assertNotNull(order.getPrice());
            assertNotNull(order.getEqPrice());
        }
    }

    @Test
    public void ordersByTradingPairId(){
        Orders orders = cobinhoodClient.getOrders(COBETH);
        validateOrders(orders,COBETH,false);
    }

    @Test
    public void ordersByTradingPair(){
        Orders orders = cobinhoodClient.getOrders(COBETHPAIR);
        validateOrders(orders,COBETHPAIR.getId(),false);
    }

    @Test
    public void ordersForAllTradingPairs(){
        Orders orders = cobinhoodClient.getOrders();
        validateOrders(orders,null,false);
    }

    @Test
    public void orderHistoryByTradingPairId(){
        Orders orders = cobinhoodClient.getOrderHistory(COBETH);
        validateOrders(orders,COBETH,true);
    }

    @Test
    public void orderHistoryByTradingPair(){
        Orders orders = cobinhoodClient.getOrderHistory(COBETHPAIR);
        validateOrders(orders,COBETHPAIR.getId(),true);
    }

    @Test
    public void orderHistoryForAllTradingPairs(){
        Orders orders = cobinhoodClient.getOrderHistory();
        validateOrders(orders,null,true);
    }

    private void validateTrade(Trade trade){
        assertNotNull(trade.getUuid());
        assertNotNull(trade.getTimestamp());
        assertNotNull(trade.getMakerSide());
        assertNotNull(trade.getAskOrderId());
        assertNotNull(trade.getBidOrderId());
        assertNotNull(trade.getSize());
        assertNotNull(trade.getPrice());
    }

    @Test
    public void getTrade(){
        if(tradeUUID == null || tradeUUID == "")
            return;
        Trade trade = cobinhoodClient.getTrade(tradeUUID);
        TestingUtils.assertGenericResponseSuccess(trade);
        validateTrade(trade);
    }

    @Test
    public void getTradeWithWrongTradeId(){
        Trade trade = cobinhoodClient.getTrade("bad-uuid");
        assertFalse(trade.isSuccess());
        assertTrue(trade.getError().equals("invalid_trade_id"));
    }

    private void validateTrades(Trades trades){
        TestingUtils.assertGenericListResponseSuccess(trades);
        for (Trade trade : trades) {
            validateTrade(trade);
        }
    }

    @Test
    public void getOrderTradesByOrderId(){
        Trades orderTrades = cobinhoodClient.getOrderTrades(orderUUID);
        validateTrades(orderTrades);
    }

    @Test
    public void getOrderTradesByOrder(){
        Order order = new Order();
        order.setUuid(orderUUID);
        Trades orderTrades = cobinhoodClient.getOrderTrades(order);
        validateTrades(orderTrades);
    }

    @Test
    public void getOrderTradesWithWrongOrderId(){
        Trades orderTrades = cobinhoodClient.getOrderTrades("bad-uuid");
        assertFalse(orderTrades.isSuccess());
        assertTrue(orderTrades.getError().equals("parameter_error"));
    }

    @Test
    public void getTradeHistoryByTradingPairId(){
        Trades tradeHistory = cobinhoodClient.getTradeHistory(COBETH);
        validateTrades(tradeHistory);
    }

    @Test
    public void getTradeHistoryByTradingPair(){
        Trades tradeHistory = cobinhoodClient.getTradeHistory(COBETHPAIR);
        validateTrades(tradeHistory);
    }

    @Test
    public void getTradeHistory(){
        Trades tradeHistory = cobinhoodClient.getTradeHistory();
        validateTrades(tradeHistory);
    }

    @Test
    public void getOrderTradesWithWrongTradingPairId(){
        Trades tradeHistory = cobinhoodClient.getTradeHistory(BADPAIR);
        assertFalse(tradeHistory.isSuccess());
        assertTrue(tradeHistory.getError().equals("parameter_error"));
    }



}
