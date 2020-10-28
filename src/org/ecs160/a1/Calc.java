package org.ecs160.a1;

import com.codename1.ui.Button;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Calc {
    final static int NUM_ZEROS_INITIAL = 10; // When initializing stack, it starts off with this many zeroes
    final static int NUM_REGISTERS_DISPLAYED = 4;
    final static int NUM_LISTS = 4;
    final static String lists_filename = "lists.txt";

    static Stack<Double> stack = new Stack<>();
    HashMap<String, Stack<Double>> stacks = new HashMap<>();
    boolean operator_last_pressed = false;
    boolean enter_last_pressed = false;
    String X = "";  // Keep as string to add on digit / period.

    public Calc() {
        for (int i = 0; i < NUM_ZEROS_INITIAL; ++i) {
            pushStack("0");
        }

        File list_file = new File(lists_filename);
        if (!list_file.isDirectory()) { // If file containing lists do not exist (ie no previous session)
            for (int i = 0; i < NUM_LISTS; ++i) {
                stacks.put(String.valueOf(i), stack); // FIXME: not sure if clone is necessary?
            }
            storeLists();
        }
        else {
            loadLists();
        }
    }

    public List<Double> rootCurve(int a) {
        List<Double> nums = new ArrayList(stack); // convert global var 'stack' into array
        Collections.reverse(nums);
        /* TODO: Test code. My simulation isnt working right now */
        //Root Curve Function: F(X)=(100^(1-a))*(x^a)
        double ans = 0.00;
        double val = 0.00;
        for (int i=0; i<nums.size(); i++) {
            val =  nums.get(i);
            ans = Math.pow(100, (1-a)) * Math.pow(val, a);
            nums.set(i, ans);
        }
        return nums;
    }

    public List<Double> bellCurve() {
        List<Double> nums = new ArrayList(stack); // convert global var 'stack' into array
        Collections.reverse(nums);
        /* TODO: curve nums using bell curve. **************************************/
        double val = 0.00;
        for (Double num : nums) {
            val += num;
        }
        double mean = val/nums.size();
        return nums;
    }

    public void storeStack(String register) { // source: https://mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
        stacks.put(register, stack);
        storeLists();
    }

    public void storeLists() {
        try {
            FileOutputStream fos = new FileOutputStream(new File(lists_filename));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(stacks);

            fos.close();
            oos.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    public void loadLists() { // source: https://mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
        try {
            FileInputStream fis = new FileInputStream(new File(lists_filename));
            ObjectInputStream ois = new ObjectInputStream(fis);
            stacks = (HashMap<String, Stack<Double>>) ois.readObject();

            ois.close();
            fis.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("Error initializing stream");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadList(String register) {
        stack = stacks.get(register);
    }

    private void pushStack(String num) {
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
        double[] registers = new double[NUM_REGISTERS_DISPLAYED];

        for (int i = stack.size() - 1, j = 0; j < NUM_REGISTERS_DISPLAYED && j < stack.size(); --i, ++j){
            registers[j] = stack.get(i);
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

    public void backspace() {
        if (!X.isEmpty() && !operator_last_pressed && !enter_last_pressed) {
            X = X.substring(0, X.length() - 1);
        }
    }

    public void unaryOpOrConst(Button button) {
        /* Ex: log, pi, e^x, x^2 */
        double X_double = Double.parseDouble(X);

        switch (button.getText()) {
            case "log":
                X_double = Math.log(X_double); break;
            case "sin":
                X_double = Math.sin(X_double); break;
            case "cos":
                X_double = Math.cos(X_double); break;
            case "tan":
                X_double = Math.tan(X_double); break;
            case "x^2":
                X_double = Math.pow(X_double, 2); break;
            case "e^x":
                X_double = Math.exp(X_double); break;
                // TODO: add rest of comments
	    }

        X = String.valueOf(X_double);
        operator_last_pressed = true;
    }

    public void binaryOp(Button button) {
        /* Ex: *, +, - */
        if (stack.size() <= 0) { // Error: if nothing on stack
            return;
        }

        double result = Float.NEGATIVE_INFINITY; // Keep as -inf in case something bad happened and no cases matched
        double X_double = Double.parseDouble(X);
        double Y = stack.pop();

        switch (button.getText()) {
            case "/":
                result = Y / X_double; break;
            case "*":
                result = Y * X_double; break;
            case "-":
                result = Y - X_double; break;
            case "+":
                result = Y + X_double; break;
            case "Y^X":
                result = Math.pow(Y, X_double); break;
            // TODO: add rest of cases *******************************************
        }

        X = String.valueOf(result);
        operator_last_pressed = true;
    }

    public void pop() {
        X = String.valueOf(stack.pop());
        operator_last_pressed = true;
    }

    public HashMap<String, Integer> gradeDistr(double[] grades) {
        HashMap<String, Integer> grade_distr = new HashMap<String, Integer>() {{
            put("A+", 0); put("A", 0); put("A-", 0); put("B+", 0); put("B", 0);
            put("B-", 0); put("C+", 0); put("C", 0); put("C-", 0); put("D+", 0);
            put("D", 0); put("D-", 0); put("F+", 0); put("F", 0); put("Z", 0);
        }};

        for (double grade: grades) {
            if (97 <= grade) {
                grade_distr.compute("A+", (k, v) -> v + 1);
            }
            else if (93 <= grade && grade < 97) {
                grade_distr.compute("A", (k, v) -> v + 1);
            }
            else if (90 <= grade && grade < 93) {
                grade_distr.compute("A-", (k, v) -> v + 1);
            }
            else if (87 <= grade && grade < 90) {
                grade_distr.compute("B+", (k, v) -> v + 1);
            }
            else if (83 <= grade && grade < 87) {
                grade_distr.compute("B", (k, v) -> v + 1);
            }
            else if (80 <= grade && grade < 83) {
                grade_distr.compute("B-", (k, v) -> v + 1);
            }
            else if (77 <= grade && grade < 80) {
                grade_distr.compute("C+", (k, v) -> v + 1);
            }
            else if (73 <= grade && grade < 77) {
                grade_distr.compute("C", (k, v) -> v + 1);
            }
            else if (70 <= grade && grade < 73) {
                grade_distr.compute("C-", (k, v) -> v + 1);
            }
            else if (67 <= grade && grade < 70) {
                grade_distr.compute("D+", (k, v) -> v + 1);
            }
            else if (63 <= grade && grade < 67) {
                grade_distr.compute("D", (k, v) -> v + 1);
            }
            else if (60 <= grade && grade < 63) {
                grade_distr.compute("D-", (k, v) -> v + 1);
            }
            else if (57 <= grade && grade < 60) {
                grade_distr.compute("F+", (k, v) -> v + 1);
            }
            else if (.0001 <= grade && grade < 57) {
                grade_distr.compute("F", (k, v) -> v + 1);
            }
            else if (grade <= .0001) {
                grade_distr.compute("Z", (k, v) -> v + 1);
            }
        }

        return grade_distr;
    }
}
