package com.cleanCoders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterTest {


    @Test
    void getPathRootExtractsCorrectPath() {
        Router router = new Router();
        assertEquals("/ping", router.getPathRoot("/ping/1"), "Should extract the root path from dynamic segments");
        assertEquals("/hello", router.getPathRoot("/hello/world"), "Should extract root path from longer paths");
        assertEquals("/", router.getPathRoot("/"), "Should correctly handle the root path");
        assertEquals("/form", router.getPathRoot("/form?foo=1&bar=2"), "Should handle paths with query parameters");
    }
}
