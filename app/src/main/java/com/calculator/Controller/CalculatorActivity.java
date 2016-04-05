package com.calculator.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.calculator.Model.Calculator;
import com.calculator.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Formatter;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    TextView input_field;
    TextView sign;
    TextView memory;

    Button btn_one;
    Button btn_two;
    Button btn_three;
    Button btn_four;
    Button btn_five;
    Button btn_six;
    Button btn_seven;
    Button btn_eight;
    Button btn_nine;
    Button btn_plus;
    Button btn_minus;
    Button btn_multiply;
    Button btn_divide;
    Button btn_clear;
    Button btn_equals;
    Button btn_zero;
    Button btn_mem;
    Button btn_recal;
    Button btn_del;
    Button btn_delimiter;

    String btn_sign;
    String prevNum;
    BigDecimal result;
    BigDecimal mem;
    int maxHeight = 40;

    Button otherCalc;
    Intent calcIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        calcIntent = new Intent(this,SecondCalcActivity.class);
        otherCalc = (Button) findViewById(R.id.otherCalc);
        otherCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(calcIntent);
            }
        });

        btn_sign = "";
        result = new BigDecimal(0);
        mem = new BigDecimal(0);

        input_field = (TextView) findViewById(R.id.input_Field);
        sign = (TextView) findViewById(R.id.sign);
        memory = (TextView) findViewById(R.id.memory);
        memory.setTextColor(Color.RED);


        btn_one = (Button) findViewById(R.id.btn_One);
        btn_two = (Button) findViewById(R.id.btn_Two);
        btn_three = (Button) findViewById(R.id.btn_Three);
        btn_four = (Button) findViewById(R.id.btn_Four);
        btn_five = (Button) findViewById(R.id.btn_Five);
        btn_six = (Button) findViewById(R.id.btn_Six);
        btn_seven = (Button) findViewById(R.id.btn_Seven);
        btn_eight = (Button) findViewById(R.id.btn_Eight);
        btn_nine = (Button) findViewById(R.id.btn_Nine);
        btn_plus = (Button) findViewById(R.id.btn_Plus);
        btn_minus = (Button) findViewById(R.id.btn_Minus);
        btn_multiply = (Button) findViewById(R.id.btn_MultiPly);
        btn_divide = (Button) findViewById(R.id.btn_Divide);
        btn_clear = (Button) findViewById(R.id.btn_Clear);
        btn_equals = (Button) findViewById(R.id.btn_Equals);
        btn_zero = (Button) findViewById(R.id.btn_Zero);
        btn_mem = (Button) findViewById(R.id.btn_Memory);
        btn_recal = (Button) findViewById(R.id.btn_Recal);
        btn_del = (Button) findViewById(R.id.btn_Del);
        btn_delimiter = (Button) findViewById(R.id.btn_Delimiter);

        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_five.setOnClickListener(this);
        btn_six.setOnClickListener(this);
        btn_seven.setOnClickListener(this);
        btn_eight.setOnClickListener(this);
        btn_nine.setOnClickListener(this);
        btn_zero.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_multiply.setOnClickListener(this);
        btn_divide.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_equals.setOnClickListener(this);

        btn_mem.setOnClickListener(this);
        btn_recal.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_delimiter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String btn_text = ((Button) v).getText().toString();
        String input = input_field.getText().toString();
        String signField = sign.getText().toString();

        if (input_field.getText().toString().equals("\u221E")){
            input_field.setText("0");
            input_field.setTextColor(Color.BLACK);
        }

        if (btn_text.equals(btn_delimiter.getText().toString())){
            if (!input_field.getText().toString().contains(".") && input.matches("[0-9]")){
                input_field.append(".");
            }
        }

        if (btn_text.equals(btn_mem.getText().toString())){
            if (input.equals("0") || !input.matches("[0-9.]*")){
                mem =new BigDecimal(0);
                memory.setText("");
                input_field.setText("0");
                return;
            }
            mem = new BigDecimal(input);
            memory.setText("M");
        }

        if (btn_text.equals(btn_recal.getText().toString())){
            input_field.setText(mem.toString());
        }

        if (btn_text.equals(btn_del.getText().toString())){
            if (input.length() == 1 || !input.matches("[0-9]*")){
                input_field.setText("0");
                return;
            }
            input_field.setText(input.substring(0,input.length()-1));
        }

        if (btn_text.matches("[*|+|/|-]") && !btn_text.equals(btn_equals.getText().toString())) {
            sign.setText(btn_text);
            btn_sign = btn_text;
            prevNum = input;
            if (prevNum.equals("\u221E"))
                prevNum = "0";
        }
        if (btn_text.matches("[0-9]")) {
            if (input_field.getText().toString().matches("0") ||
                    !btn_sign.equals("") ||
                    result == null ||
                    result.equals(0) ||
                    input.equals("\u221E") ||
                    !input.matches("[0-9.]*")
                    ) {
                result = new BigDecimal(0);
                prevNum = input;
                if (prevNum.equals("\u221E"))
                    prevNum = "0";
                input = "";
                btn_sign = "";
            }
            input_field.setText(input + btn_text);
        }

        if (btn_text.equals(btn_clear.getText().toString())) {
            input_field.setText("0");
            sign.setText("");
        }
        if (btn_text.equals(btn_equals.getText().toString())) {
            this.calcNums(input, signField);
        }

    }

    void calcNums(String input, String signField){
        try{

            result = Calculator.calc(prevNum, input, signField);
            DecimalFormat f = new DecimalFormat();
            f.setDecimalSeparatorAlwaysShown(false);
            f.setGroupingUsed(false);
            f.setMaximumFractionDigits(10);

            sign.setText("");
            input_field.setText(f.format(result));
            result = null;
        }
        catch (ArithmeticException e)
        {
            input_field.setTextColor(Color.RED);
            sign.setText("");
            if (prevNum.equals("0") && input.equals("0")) {
                input_field.setText("Not a number");
                return;
            }
            input_field.setText("\u221E"); //∞ infinity
        }
    }
}
