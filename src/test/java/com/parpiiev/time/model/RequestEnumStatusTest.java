package com.parpiiev.time.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestEnumStatusTest {

    @Test
    void getValue() {
        assertEquals("DECLINED", Status.DECLINED.getValue());
    }

    @Test
    void values() {
        assertEquals(4, Status.values().length);
    }

    @Test
    void valueOf() {
        assertEquals(Status.APPROVED, Status.valueOf("APPROVED"));
    }
}