package org.ecs160.a1;

import com.codename1.ui.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Calc {
  static final int NUM_REGISTERS_DISPLAYED = 4;
  static final int NUM_LISTS = 4;
  static final String lists_filename = "lists.txt";

  static Stack<Double> stack = new Stack<>();
  HashMap<String, Stack<Double>> stacks = new HashMap<>();

  boolean operator_last_pressed = false;
  boolean enter_last_pressed = false;
  String X = ""; // Keep as string to add on digit / period.

  public Calc() {
    File list_file = new File(lists_filename);

    if (list_file.exists()) {
      loadLists();
    } else {
      for (int i = 0; i < NUM_LISTS; ++i) {
        stacks.put(String.valueOf(i), new Stack<>());
      }
      storeLists();
    }
  }

  public Double[] rootCurve(double a) {
    Double[] nums = reverse((Double[]) stack.toArray());

    // Root Curve Function: F(X)=(100^(1-a))*(x^a)
    for (int i = 0; i < nums.length; i++) {
      nums[i] = Math.pow(100, (1 - a)) * Math.pow(nums[i], a);
    }

    return nums;
  }

  public Double[] flatCurve(double a) {
    Double[] nums = reverse((Double[]) (stack.toArray()));

    for (int i = 0; i < nums.length; i++) {
      nums[i] += a;
    }

    return nums;
  }

  // consider the highest score to be 100% and regrade the rest of the scores as if their
  // denominator was the highest
  public Double[] maxCurve() {
    Double[] nums = reverse((Double[]) stack.toArray());

    double max = max();
    for (int i = 0; i < nums.length; i++) {
      nums[i] = nums[i] / max * 100;
    }

    return nums;
  }

  // curves grades using a normal distribution. The target_average parameter specifies the score
  // that the average grade will take. For example, if a student scored 70 / 100 on an exam but we
  // want to curve them up so that the class average is an 80 / 100, the parameter would be 80.
  public Double[] bellCurve(double target_average) {
    Double[] nums = reverse((Double[]) stack.toArray());
    double mean = mean(nums);
    double stddev = stddev(nums);

    for (int i = 0; i < nums.length; i++) {
      nums[i] = target_average + 10 * (nums[i] - mean) / stddev;
    }

    return nums;
  }

  private double mean(Double[] input) {
    double sum = 0;

    for (double v : input) {
      sum += v;
    }

    return sum / input.length;
  }

  private double stddev(Double[] input) {
    double mean = mean(input);

    double sum = 0;
    for (double value : input) {
      sum += Math.pow(value - mean, 2);
    }

    return Math.sqrt(sum / input.length);
  }

  private double max() {
    Double[] nums = reverse((Double[]) stack.toArray());

    double max = 0;
    for (Double num : nums) {
      if (num > max) {
        max = num;
      }
    }

    return max;
  }

  private double min() {
    Double[] nums = reverse((Double[]) stack.toArray());

    double min = 0;
    for (Double num : nums) {
      if (num < min) {
        min = num;
      }
    }

    return min;
  }

  public static Double[] reverse(Double[] input) {
    Double[] result = input.clone();

    for (int i = 0; i < result.length / 2; ++i) {
      double temp = result[i];
      result[i] = result[result.length - i - 1];
      result[result.length - i - 1] = temp;
    }

    return result;
  }

  public void storeList(String register) {
    stacks.put(register, (Stack<Double>) stack.clone());
    storeLists();
  }

  public void loadList(String register) {
    loadLists();
    stack = (Stack<Double>) stacks.get(register).clone();
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

    if (button.getText().equals(".") && X.contains(".")) {
      // do not allow another decimal
      // do nothing
    } else {
      X += button.getText(); // Concatenate digits pressed
    }
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
      X = "";
      return;
    }

    X = String.valueOf(stack.pop());
  }

  public HashMap<String, Integer> gradeHistogram() {
    List<Double> grades = new ArrayList<>(stack);

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
    hist.put("Z", 0);

    for (Double grade : grades) {
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
      } else if (grade > 0) {
        hist.compute("F", (k, v) -> v + 1);
      } else {
        hist.compute("Z", (k, v) -> v + 1);
      }
    }

    return hist;
  }

  public List<List<Double>> lists() {
    List<List<Double>> lists = new ArrayList<>(stacks.values());

    // SOURCE: https://stackoverflow.com/questions/47879142/copy-two-dimensional-arraylist-as-new
    List<List<Double>> result = lists.stream().map(ArrayList::new).collect(Collectors.toList());

    for (List<Double> list : result) {
      Collections.reverse(list);
    }

    return result;
  }
}
