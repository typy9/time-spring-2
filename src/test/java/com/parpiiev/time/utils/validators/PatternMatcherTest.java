package com.parpiiev.time.utils.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PatternMatcherTest {

    @Test
    void validateName() {
        Assertions.assertTrue(PatternMatcher.validateName("h"));
        Assertions.assertTrue(PatternMatcher.validateName("77"));
        Assertions.assertTrue(PatternMatcher.validateName("Smith99"));

        Assertions.assertFalse(PatternMatcher.validateName(""));
        Assertions.assertFalse(PatternMatcher.validateName("FoobarFoobarFoobar"));
        Assertions.assertFalse(PatternMatcher.validateName("smith99-"));
    }

    @Test
    void validateLogin() {
        Assertions.assertTrue(PatternMatcher.validateLogin("a"));
        Assertions.assertTrue(PatternMatcher.validateLogin("My_Login"));
        Assertions.assertTrue(PatternMatcher.validateLogin(".log"));
        Assertions.assertTrue(PatternMatcher.validateLogin("log"));

        Assertions.assertFalse(PatternMatcher.validateLogin(""));
        Assertions.assertFalse(PatternMatcher.validateLogin(".Foo_Foo.Foobar_"));
        Assertions.assertFalse(PatternMatcher.validateLogin("&Foobar$"));
        Assertions.assertFalse(PatternMatcher.validateLogin("Blake90"));
    }

    @Test
    void validatePassword() {
        Assertions.assertTrue(PatternMatcher.validatePassword("h%"));
        Assertions.assertTrue(PatternMatcher.validatePassword("77"));
        Assertions.assertTrue(PatternMatcher.validatePassword("dg&_3kBp"));

        Assertions.assertFalse(PatternMatcher.validatePassword("*"));
        Assertions.assertFalse(PatternMatcher.validatePassword("+p"));
        Assertions.assertFalse(PatternMatcher.validatePassword("myPass1234myPass"));
    }

    @Test
    void validateEmail() {
        Assertions.assertTrue(PatternMatcher.validateEmail("a_b@gmail.com"));
        Assertions.assertTrue(PatternMatcher.validateEmail("a.c.block@yahoo.com"));
        Assertions.assertTrue(PatternMatcher.validateEmail("lambert@stanford.edu"));

        Assertions.assertFalse(PatternMatcher.validateEmail("a@gmail.com"));
        Assertions.assertFalse(PatternMatcher.validateEmail("s_kelly.gmail.com"));
        Assertions.assertFalse(PatternMatcher.validateEmail("abc_def@gmail"));
    }
}