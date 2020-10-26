package org.ecs160.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Border;

import java.util.ArrayList;

public class Keypad extends Container {
    ArrayList<Button> key_combo = new ArrayList<>();

    Calc calc;
    Display display;

    // five containers to populate keypad
    Container sci0 = new Container(new GridLayout(2,5));
    Container sci1 = new Container(new GridLayout(2,5));
    Container num2 = new Container(new GridLayout(1, 5));
    Container num3 = new Container(new GridLayout(1, 5));
    Container num4 = new Container(new GridLayout(1, 5));
    Container num5 = new Container(new GridLayout(1, 5));

    Button store = new ScienceButton("sto");
    Button recall = new ScienceButton("rcl");
    Button root = new ScienceButton("root");
    Button bell = new ScienceButton("bell");
    Button undo = new ScienceButton("undo");
    Button exit = new ScienceButton("exit");
    Button plusminus = new ScienceButton("+/-");
    Button info = new ScienceButton("info");
    Button overX = new ScienceButton("1/x");
    Button yX = new ScienceButton("y^x");
    Button bSin = new ScienceButton("sin");
    Button bCos = new ScienceButton("cos");
    Button bTan = new ScienceButton("tan");
    Button bLog = new ScienceButton("log");
    Button bLn = new ScienceButton("ln");
    Button bX2 = new ScienceButton("x^2");
    Button bX3 = new ScienceButton("x^3");
    Button bSQRT = new ScienceButton("sqrt");
    Button bPi = new ScienceButton("pi");
    Button bE = new ScienceButton("E");

    Button bPop = new OtherButton("pop");
    Button bClear = new OtherButton("CLR");
    Button bBack = new OtherButton("<-");
    Button bEnter = new OtherButton("ENT");

    Button b0 = new NumberButton("0");
    Button b1 = new NumberButton("1");
    Button b2 = new NumberButton("2");
    Button b3 = new NumberButton("3");
    Button b4 = new NumberButton("4");
    Button b5 = new NumberButton("5");
    Button b6 = new NumberButton("6");
    Button b7 = new NumberButton("7");
    Button b8 = new NumberButton("8");
    Button b9 = new NumberButton("9");
    Button bDecimal = new NumberButton(".");

    Button bYX = new OperatorButton("Y^X");
    Button bPlus = new OperatorButton("+");
    Button bStar = new OperatorButton("*");
    Button bSlash = new OperatorButton("/");
    Button bMinus = new OperatorButton("-");

    public Keypad(Layout layout, Calc calc, Display display) {
        super(layout);
        this.calc = calc;
        this.display = display;

        keypadRow0();
        keypadRow1();
        keypadRow2();
        keypadRow3();
        keypadRow4();
        keypadRow5();

        buttonPressListeners();

        Container[] key_rows = {sci0, sci1, num2, num3, num4, num5};
        this.addAll(key_rows); // Keypad Container adds all key rows

        for (Container row : key_rows) {
            for (Component comp : row) { // FIXME: code pretty ugly. Maybe easier way to add action listeners to all buttons? Or make different classes?
                if (comp instanceof Button && !((Button) comp).getText().equals("rcl")) {
                    ((Button) comp).addActionListener(evt -> display.viewRegisters());
                }
            }
        }
    }

    public void keypadRow1() {
        sci1.addAll(exit, plusminus, info, overX, yX, store, recall, root, bell, undo);
    }

    public void keypadRow0() {
        // Create the 1st row of the keypad (2 rows of 5 buttons)
        sci0.addAll(bSin, bCos, bTan, bLog, bLn, bX2, bX3, bSQRT, bPi, bE);

        // Set border around top 10 buttons
        sci0.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.YELLOW));
    }

    public void keypadRow2() {
        // Create the 2nd row of the keypad (5 buttons)
        num2.addAll(bPop, b7, b8, b9, bSlash);

        // Set border around second row
        num2.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.MAGENTA));
    }

    public void keypadRow3() {
        // Create the 3rd row of the keypad (5 buttons)
        num3.addAll(bClear, b4, b5, b6, bStar);

        // Set border around third row
        num3.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLUE));
    }

    public void keypadRow4() {
        // Create the 4th row of the keypad (5 buttons)
        num4.addAll(bBack, b1, b2, b3, bMinus);

        // Set border around 4th row
        num4.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.CYAN));
    }

    public void keypadRow5() {
        // Create the 5th row of the keypad (5 buttons)
        num5.addAll(bEnter, b0, bDecimal, bYX, bPlus);
        num5.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.GREEN)); // Set border around 5th row
    }

    public void buttonPressListeners() {
        digitOrDecimalPressed(new Button[] {b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bDecimal});
        unaryOpOrConstPressed(new Button[] {bClear});
        binaryOpPressed(new Button[] {bMinus, bSlash, bStar, bYX, bPlus});

        recall.addActionListener(evt -> {
            display.viewLists();
        });
        exit.addActionListener(evt -> {
            display.viewRegisters();
        });
        bPop.addActionListener(evt -> {
            calc.pop();
        });
        bBack.addActionListener(evt -> {
            calc.backspace();
        });
        bEnter.addActionListener(evt -> {
            calc.enter();
        });
    }

    public void digitOrDecimalPressed(Button[] buttons) {
        for (Button button : buttons) {
            button.addActionListener(evt -> {
                calc.digitOrDecimal(button);
            });
        }
    }

    public void unaryOpOrConstPressed(Button[] buttons) {
        for (Button button : buttons) {
            button.addActionListener(evt -> {
                calc.unaryOpOrConst(button);
            });
        }
    }

    public void binaryOpPressed(Button[] buttons) {
        for (Button button : buttons) {
            button.addActionListener(evt -> {
                calc.binaryOp(button);
            });
        }
    }
}
