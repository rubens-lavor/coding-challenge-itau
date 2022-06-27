package com.filmreview.utils;

import com.filmreview.exception.BadRequestException;

public class Rule {
    private Rule(){}

    public static void check(boolean expression, String message) {
        if(!expression) {
            throw new BadRequestException(message);
        }
    }
}
