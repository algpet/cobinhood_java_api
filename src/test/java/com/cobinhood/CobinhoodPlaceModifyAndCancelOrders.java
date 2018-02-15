package com.cobinhood;

import com.cobinhood.responses.GenericResponse;
import com.cobinhood.responses.trading.Order;
import com.cobinhood.responses.trading.Orders;
import com.cobinhood.responses.trading.Trade;
import com.cobinhood.responses.trading.Trades;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.BeforeClass;
import com.cobinhood.responses.entity.*;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
public class CobinhoodPlaceModifyAndCancelOrders {
    private CobinhoodClient cobinhoodClient = new CobinhoodClient(ApiKey.ApiKey);
    private static String COBETH = "COB-ETH";
    private static TradingPair COBETHPAIR;

    @BeforeClass
    public static void beforeClass(){
        COBETHPAIR = new TradingPair();
        COBETHPAIR.setId(COBETH);
    }

    @Test
    @Ignore
    public void placeLimitOrderThenModifyItThenCancel(){
        Order order = new Order();
        order.setTradingPair(COBETH);
        order.setSide("ask");
        order.setType("limit");
        order.setSize(135.0);
        order.setPrice(1.0);

        Order placedOrder = cobinhoodClient.placeOrder(order);
        TestingUtils.assertGenericResponseSuccess(placedOrder);

        assertEquals(order.getTradingPair(),placedOrder.getTradingPair());
        assertEquals(order.getSide(),placedOrder.getSide());
        assertEquals(order.getType(),placedOrder.getType());
        assertEquals(order.getSize(),placedOrder.getSize());
        assertEquals(order.getPrice(),placedOrder.getPrice());
        assertNotNull(placedOrder.getUuid());

        placedOrder.setPrice(placedOrder.getPrice() + 0.001);
        GenericResponse modificationResponse = cobinhoodClient.modifyOrder(placedOrder);
        TestingUtils.assertGenericResponseSuccess(modificationResponse);
        Order modifiedOrder = cobinhoodClient.getOrder(placedOrder.getUuid());

        TestingUtils.assertGenericResponseSuccess(modifiedOrder);
        assertEquals(modifiedOrder.getSize(),placedOrder.getSize());
        assertEquals(modifiedOrder.getPrice(),placedOrder.getPrice());

        GenericResponse cancellationResponse = cobinhoodClient.cancelOrder(placedOrder);
        TestingUtils.assertGenericResponseSuccess(cancellationResponse);
        Order canceledOrder = cobinhoodClient.getOrder(placedOrder.getUuid());
        assertNotNull(canceledOrder.getCompletedAt());
        assertEquals(canceledOrder.getState(),"cancelled");
    }
}
