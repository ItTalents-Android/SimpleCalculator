package com.calculator.Model;

import java.math.BigDecimal;

/**
 * Created by Petkata on 18.2.2016 г..
 */
public class Calculator {

    public static BigDecimal calc(String num1, String num2, String sign) throws ArithmeticException {

        BigDecimal val1 = new BigDecimal(num1);
        BigDecimal val2 = new BigDecimal(num2);
        if (sign.equals("+")) {
            return val1.add(val2);
        }
        if (sign.equals("-")) {
            return val1.subtract(val2);
        }
        if (sign.equals("*")) {
            return val1.multiply(val2);
        }
        if (sign.equals("/")) {
            return val1.divide(val2, 10, BigDecimal.ROUND_HALF_EVEN); // 10 digits after decimal separator
        }
        return new BigDecimal(0);
    }
}
