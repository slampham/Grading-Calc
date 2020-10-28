package org.ecs160.a1;

import com.codename1.ui.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Stack;

public class Calc {
  static final int NUM_ZEROS_INITIAL =
      4; // When initializing stack, it starts off with this many zeroes
  static final int NUM_REGISTERS_DISPLAYED = 4;
  static final int NUM_LISTS = 4;
  static final String lists_filename = "lists.txt";

  static Stack<Double> stack = new Stack<>();
  HashMap<String, Stack<Double>> stacks = new HashMap<>();
  boolean operator_last_pressed = false;
  boolean enter_last_pressed = false;
  String X = ""; // Keep as string to add on digit / period.

  public Calc() {
    for (int i = 0; i < NUM_ZEROS_INITIAL; ++i) {
      pushStack("0");
    }

    File list_file = new File(lists_filename);
    if (!list_file
        .isDirectory()) { // If file containing lists do not exist (ie no previous session)
      for (int i = 0; i < NUM_LISTS; ++i) {
        stacks.put(
            String.valueOf(i),
            (Stack<Double>) stack.clone()); // FIXME: not sure if clone is necessary?
      }
      storeLists();
    } else {
      loadLists();
    }
  }

  public double[] rootCurve(double a) {
    double[] nums = reverse((Double[]) stack.toArray());

    // Root Curve Function: F(X)=(100^(1-a))*(x^a)
    double ans = 0, val = 0;
    for (int i = 0; i < nums.length; i++) {
      val = nums[i];
      ans = Math.pow(100, (1 - a)) * Math.pow(val, a);
      nums[i] = ans;
    }

    return nums;
  }

  public double[] bellCurve() {
    double[] nums = reverse((Double[]) stack.toArray());

    /* TODO: curve nums using bell curve. **************************************/
    double val = 0, d2mean = 0, SD = 0;
    for (Double num : nums) {
      val += num;
    }
    double mean = val / nums.length;
    for (Double num : nums) {
      d2mean += Math.pow((mean - num), 2);
    }
    SD = Math.sqrt(d2mean / nums.length);
    return nums;
  }

  public double[] flatCurve(double a) {
    double[] nums = reverse((Double[]) stack.toArray());

    for (Double num : nums) {
      num = num + a;
    }

    return nums;
  }

  private double[] reverse(Double[] input) {
    for (int i = 0; i < input.length / 2; i++) {
      double temp = input[i];
      input[i] = input[input.length - 1 - i];
      input[input.length - 1 - i] = temp;
    }
  }

  public void storeList(String register) {
    // source: https://mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    stacks.put(register, stack);
    storeLists();
  }

  public void loadList(String register) {
    loadLists();
    stack = stacks.get(register);
    X = "";
  }

  public void storeLists() {
    try {
      FileOutputStream fos = new FileOutputStream(new File(lists_filename));
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(stacks);

      fos.close();
      oos.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    } catch (IOException e) {
      System.out.println("Error initializing stream");
    }
  }

  // source: https://mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
  public void loadLists() {
    try {
      FileInputStream fis = new FileInputStream(new File(lists_filename));
      ObjectInputStream ois = new ObjectInputStream(fis);
      stacks = (HashMap<String, Stack<Double>>) ois.readObject();

      ois.close();
      fis.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    } catch (IOException e) {
      System.out.println("Error initializing stream");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void pushStack(String num) {
    /* Use this function over stack.push() because you have to deal with user
     * sometimes entering without any number */
    if (num.equals(".") || num.isEmpty()) {
      stack.push(0.);
    } else {
      stack.push(Double.parseDouble(num)); // Enter entry into stack
    }
  }

  public double[] getRegisters() {
    /* Return values in the registers displayed */
    double[] registers = new double[NUM_REGISTERS_DISPLAYED];

    for (int i = stack.size() - 1, j = 0;
        j < NUM_REGISTERS_DISPLAYED && j < stack.size();
        --i, ++j) {
      registers[j] = stack.get(i);
    }

    return registers;
  }

  public void digitOrDecimal(Button button) {
    /* Ex: 3, . , 9,*/
    if (operator_last_pressed) {
      pushStack(X);
      X = "";
    } else if (enter_last_pressed) {
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
        X_double = Math.log(X_double);
        break;
      case "sin":
        X_double = Math.sin(X_double);
        break;
      case "cos":
        X_double = Math.cos(X_double);
        break;
      case "tan":
        X_double = Math.tan(X_double);
        break;
      case "x^2":
        X_double = Math.pow(X_double, 2);
        break;
      case "e^x":
        X_double = Math.exp(X_double);
        break;
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

    double result =
        Float.NEGATIVE_INFINITY; // Keep as -inf in case something bad happened and no cases matched
    double X_double = Double.parseDouble(X);
    double Y = stack.pop();

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
        // TODO: add rest of cases *******************************************
    }

    X = String.valueOf(result);
    operator_last_pressed = true;
  }

  public void pop() {
    operator_last_pressed = true;

    if (stack.empty()) {
      X = "0.0";
      return;
    }

    X = String.valueOf(stack.pop());
  }

  public HashMap<String, Integer> gradeHistogram(double[] grades) {
    HashMap<String, Integer> hist = new HashMap<>();
    hist.put("A+", 0);
    hist.put("A", 0);
    hist.put("A-", 0);
    hist.put("B+", 0);
    hist.put("B", 0);
    hist.put("B-", 0);
    hist.put("C+", 0);
    hist.put("C", 0);
    hist.put("C-", 0);
    hist.put("D+", 0);
    hist.put("D", 0);
    hist.put("D-", 0);
    hist.put("F", 0);

    for (double grade : grades) {
      if (grade >= 97) {
        hist.compute("A+", (k, v) -> v + 1);
      } else if (grade >= 93) {
        hist.compute("A", (k, v) -> v + 1);
      } else if (grade >= 90) {
        hist.compute("A-", (k, v) -> v + 1);
      } else if (grade >= 87) {
        hist.compute("B+", (k, v) -> v + 1);
      } else if (grade >= 83) {
        hist.compute("B", (k, v) -> v + 1);
      } else if (grade >= 80) {
        hist.compute("B-", (k, v) -> v + 1);
      } else if (grade >= 77) {
        hist.compute("C+", (k, v) -> v + 1);
      } else if (grade >= 73) {
        hist.compute("C", (k, v) -> v + 1);
      } else if (grade >= 70) {
        hist.compute("C-", (k, v) -> v + 1);
      } else if (grade >= 67) {
        hist.compute("D+", (k, v) -> v + 1);
      } else if (grade >= 63) {
        hist.compute("D", (k, v) -> v + 1);
      } else if (grade >= 60) {
        hist.compute("D-", (k, v) -> v + 1);
      } else {
        hist.compute("F", (k, v) -> v + 1);
      }
    }

    return hist;
  }
}
