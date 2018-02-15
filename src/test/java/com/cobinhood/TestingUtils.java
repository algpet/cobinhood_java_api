package com.cobinhood;

import com.cobinhood.responses.GenericListResponse;
import com.cobinhood.responses.GenericResponse;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestingUtils {
    public static void assertGenericResponseSuccess(GenericResponse genericResponse){
        assertNotNull(genericResponse);
        assertTrue(genericResponse.isSuccess());
        assertNull(genericResponse.getMessage());
    }
    public static void assertGenericListResponseSuccess(GenericListResponse genericResponse){
        assertGenericResponseSuccess(genericResponse);
        assertNotNull(genericResponse.getItems());
        assertTrue(genericResponse.getItems().size() > 0);
    }
}
