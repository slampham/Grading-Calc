package org.ecs160.a1;

import com.codename1.ui.Button;

import java.util.Iterator;
import java.util.Stack;

public class Calc {
    final static int NUM_ZEROS_INITIAL = 10; // When initializing stack, it starts off with this many zeroes
    final static int NUM_REGISTERS_DISPLAYED = 4;

    static Stack<Double> stack = new Stack<>();

    // CURRENT ENTRY ON CALC DISPLAY. The first line displayed where you enter numbers
    String X = "0";  // Keep as string to add on digit / period.

    boolean operator_last_pressed = false;
    boolean enter_last_pressed = false;

    public Calc() {
        for (int i = 0; i < NUM_ZEROS_INITIAL; ++i) {
            pushStack("0");
        }
    }

    public void pushStack(String num) {
        /* Use this function over stack.push() because you have to deal with user
        * sometimes entering without any number */
        if (num.equals(".") || num.isEmpty()) {
            stack.push(0.);
        }
        else {
            stack.push(Double.parseDouble(num)); // Enter entry into stack
        }
    }

    public double[] getRegisters() {
        /* Return values in the registers displayed */
        Iterator<Double> iter = stack.iterator();
        double[] registers = new double[NUM_REGISTERS_DISPLAYED];

        registers[0] = Double.parseDouble(X);
        for (int i = 1; i < NUM_REGISTERS_DISPLAYED; ++i) {
            registers[i] = iter.next();
        }

        return registers;
    }

    public void digitOrDecimal(Button button) {
        /* Ex: 3, . , 9,*/
        if (operator_last_pressed) {
            pushStack(X);
            X = "";
        }
        else if (enter_last_pressed) {
            X = "";
        }

        X += button.getText(); // Concatenate digits pressed
        operator_last_pressed = enter_last_pressed = false;
    }

    public void enter() {
        pushStack(X);
        enter_last_pressed = true;
    }

    public void unaryOpOrConst(Button button) {
        /* Ex: log, pi, e^x, x^2 */
        double X_double = Double.parseDouble(X);

        switch (button.getText()) {
            case "log":
                X_double = Math.log(X_double);
                break;
            // TODO: add rest of cases
        }

        X = String.valueOf(X_double);
        operator_last_pressed = true;
    }

    public void binaryOp(Button button) {
        /* Ex: *, +, - */
        double Y = stack.pop();
        double result = Float.NEGATIVE_INFINITY; // Keep as -inf in case something bad happened and no cases matched
        double X_double = Double.parseDouble(X);

        switch (button.getText()) {
            case "/":
                result = Y / X_double;
                break;
            case "*":
                result = Y * X_double;
                break;
            case "-":
                result = Y - X_double;
                break;
            case "+":
                result = Y + X_double;
                break;
            case "Y^X":
                result = Math.pow(Y, X_double);
                break;
            // TODO: add rest of cases
        }

        X = String.valueOf(result);
        operator_last_pressed = true;
    }
}
