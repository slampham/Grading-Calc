package org.ecs160.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Border;

import java.util.ArrayList;

public class Keypad extends Container {
    // five containers to populate keypad
    Container sci1 = new Container(new GridLayout(3,5));
    Container num2 = new Container(new GridLayout(1, 5));
    Container num3 = new Container(new GridLayout(1, 5));
    Container num4 = new Container(new GridLayout(1, 5));
    Container num5 = new Container(new GridLayout(1, 5));

    Calc calc;
    Display display;

    public Keypad(Layout layout, Calc calc, Display display) {
        super(layout);
        this.calc = calc;
        this.display = display;

        keypadRow1();
        keypadRow2();
        keypadRow3();
        keypadRow4();
        keypadRow5();

        Container[] key_rows = {sci1, num2, num3, num4, num5};
        this.addAll(key_rows); // Keypad Container adds all key rows

        /* FIXME: Would have been easy to add action listeners to every button. However, I realized
           that the ORDER of action listeners matter, meaning that calc.function() would
           occur AFTER the display.update(). That means the display updates BEFORE the actual
           calculation. Does anyone know how to fix this? */

//        for (Container row : key_rows) {
//            for (Component comp : row) {
//                if (comp instanceof Button) {
//                    ((Button) comp).addActionListener(evt -> display.update());
//                }
//            }
//        }
    }

    public void keypadRow1() {
        // Create the 1st row of the keypad (2 rows of 5 buttons)
        Button bSin = scienceButton("sin");
        Button bCos = scienceButton("cos");
        Button bTan = scienceButton("tan");
        Button bLog = scienceButton("log");
        Button bLn = scienceButton("ln");
        Button bX2 = scienceButton("x^2");
        Button bX3 = scienceButton("x^3");
        Button bSQRT = scienceButton("sqrt");
        Button bPi = scienceButton("pi");
        Button bE = scienceButton("E");

        Button store = scienceButton("sto");
        Button recall = scienceButton("rcl");
        Button root = scienceButton("root");
        Button bell = scienceButton("bell");
        Button undo = scienceButton("undo");

        sci1.addAll(bSin, bCos, bTan, bLog, bLn, bX2, bX3, bSQRT, bPi, bE, store, recall, root, bell, undo);

        // Set border around top 10 buttons
        sci1.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.YELLOW));

        recall.addActionListener(
                evt -> {
                    display.viewLists();
                });
    }

    public void keypadRow2() {
        // Create the 2nd row of the keypad (5 buttons)
        Button bPop = otherButton("pop");
        Button b7 = numberButton("7");
        Button b8 = numberButton("8");
        Button b9 = numberButton("9");
        Button bSlash = operatorButton("/");

        num2.addAll(bPop, b7, b8, b9, bSlash);

        // Set border around second row
        num2.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.MAGENTA));

        Button[] digits = new Button[] {b7, b8, b9};
        digitOrDecimalPressed(digits);
    }

    public void keypadRow3() {
        // Create the 3rd row of the keypad (5 buttons)
        Button bClear = otherButton("CLR");
        Button b4 = numberButton("4");
        Button b5 = numberButton("5");
        Button b6 = numberButton("6");
        Button bStar = operatorButton("*");

        num3.addAll(bClear, b4, b5, b6, bStar);

        // Set border around third row
        num3.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLUE));

        Button[] digits = new Button[] {b4, b5, b6};
        digitOrDecimalPressed(digits);
    }

    public void keypadRow4() {
        // Create the 4th row of the keypad (5 buttons)
        Button bBack = otherButton("<-");
        Button b1 = numberButton("1");
        Button b2 = numberButton("2");
        Button b3 = numberButton("3");
        Button bMinus = operatorButton("-");

        num4.addAll(bBack, b1, b2, b3, bMinus);

        // Set border around 4th row
        num4.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.CYAN));

        Button[] digits = new Button[] {b1, b2, b3};
        Button[] binary_ops = new Button[] {bMinus};
        bBack.addActionListener(evt -> calc.backspace());
        digitOrDecimalPressed(digits);
        binaryOpPressed(binary_ops);
    }

    public void keypadRow5() {
        // Create the 5th row of the keypad (5 buttons)
        Button bEnter = otherButton("ENT");
        Button b0 = numberButton("0");
        Button bDecimal = numberButton(".");
        Button bYX = operatorButton("Y^X");
        Button bPlus = operatorButton("+");

        num5.addAll(bEnter, b0, bDecimal, bYX, bPlus);
        num5.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.GREEN)); // Set border around 5th row

        Button[] digits = new Button[] {b0, bDecimal};
        Button[] unary_ops = new Button[] {bYX};
        Button[] binary_ops = new Button[] {bPlus};

        digitOrDecimalPressed(digits);
        unaryOpOrConstPressed(unary_ops);
        binaryOpPressed(binary_ops);
        enterPressed(bEnter);
    }

    public Button numberButton(String text) {
        // give style to the number buttons
        Button bNum = new Button(text);

        bNum.getUnselectedStyle().setBgTransparency(255);
        bNum.getUnselectedStyle().setBgColor(ColorUtil.LTGRAY);
        bNum.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        // Can add selectedStyle for when button is pressed. Otherwise it defaults to white BG with light blue text
        bNum.getAllStyles().setMargin(50, 50, 20, 20); // Margin parameters: TOP, BOTTOM, LEFT, RIGHT

        return bNum;
    }

    public Button scienceButton(String text) {
        // give style to the number buttons
        Button bSci = new Button(text);

        bSci.getUnselectedStyle().setBgTransparency(255);
        bSci.getUnselectedStyle().setBgColor(ColorUtil.GREEN);
        bSci.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        // Can add selectedStyle for when button is pressed. Otherwise it defaults to white BG with light blue text
        bSci.getAllStyles().setMargin(25, 25, 15, 15); // Margin parameters: TOP, BOTTOM, LEFT, RIGHT

        return bSci;
    }

    public Button otherButton(String text) {
        // give style to the other buttons (not numbers)
        Button bOther = new Button(text);

        bOther.getUnselectedStyle().setBgTransparency(255);
        bOther.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
        bOther.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        // Can add selectedStyle for when button is pressed. Otherwise it defaults to white BG with light blue text
        bOther.getAllStyles().setMargin(50, 50, 20, 20); // Margin parameters: TOP, BOTTOM, LEFT, RIGHT

        return bOther;
    }

    public Button operatorButton(String text) {
        // give style to operator buttons
        Button bOperation = new Button(text);

        bOperation.getUnselectedStyle().setBgTransparency(255);
        bOperation.getUnselectedStyle().setBgColor(ColorUtil.GRAY);
        bOperation.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        // Can add selectedStyle for when button is pressed. Otherwise it defaults to white BG with light blue text
        bOperation.getAllStyles().setMargin(50, 50, 20, 20); // Margin parameters: TOP, BOTTOM, LEFT, RIGHT

        return bOperation;
    }

    /*  TODO: IF ANY BUTTON PRESSED, IT SHOULD UPDATE DISPLAY. CURRENTLY ONLY CHANGES CALC STACK ATM */
    public void digitOrDecimalPressed(Button[] buttons) {
        for (Button button : buttons) {
            button.addActionListener(
                    evt -> {
                        calc.digitOrDecimal(button);
                        display.update();
                    }
            );
        }
    }

    public void unaryOpOrConstPressed(Button[] buttons) {
        for (Button button : buttons) {
            button.addActionListener(
                    evt -> {
                        calc.unaryOpOrConst(button);
                        display.update();
                    });
        }
    }

    public void binaryOpPressed(Button[] buttons) {
        for (Button button : buttons) {
            button.addActionListener(
                    evt -> {
                        calc.binaryOp(button);
                        display.update();
                    });
        }
    }

    public void enterPressed(Button enter) {
        enter.addActionListener(
                    evt -> {
                        calc.enter();
                        display.update();
                    }
        );
    }
}
