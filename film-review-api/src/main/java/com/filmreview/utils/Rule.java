package com.filmreview.utils;

import com.filmreview.exception.ForbiddenRequestException;

public class Rule {
    private Rule(){}

    public static void check(boolean expression, String message) {
        if(!expression) {
            throw new ForbiddenRequestException(message);
        }
    }
}
