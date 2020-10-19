package org.ecs160.a1;

import com.codename1.ui.Button;

import java.util.Stack;

public class Calc {
    Stack<Double> stack = new Stack<Double>();
    String cur_entry = ""; // Keep as string to allow more digit to be added

    public void digitOrPeriodPressed(char c) {
        cur_entry += c;
    }

    public void enterPressed() {
        if (cur_entry.equals(".") || cur_entry.isEmpty()) {
            stack.push(0.);
        }
        else {
            stack.push(Double.parseDouble(cur_entry)); // Enter entry into stack
        }

        cur_entry = ""; // Clear entry
    }

    public void operatorPressed(Button button) {
        double x = stack.pop();
        double y = stack.pop();
        double result = Float.NEGATIVE_INFINITY; // Keep as -inf in case something bad happened and no cases matched

        switch (button.getText()) {
            case "/":
                result = y / x;
                break;
            case "*":
                result = y * x;
                break;
            case "-":

                break;
            case "+":

                break;
            case "Y^X":

                break;
        }

        stack.push(result);
    }
}
