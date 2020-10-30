package org.ecs160.a1;

import com.codename1.ui.*;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.Layout;

public class Keypad extends Container {
    Button prev_key_pressed = null;

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
    Button max = new ScienceButton("max");
    Button exit = new ScienceButton("exit");
    Button plusminus = new ScienceButton("+/-");
    Button stat = new ScienceButton("stat");
    Button overX = new ScienceButton("1/x");
    Button yX = new ScienceButton("y^x");

    Button grades = new ScienceButton("grd");
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

    Button bPop = new SysButton("pop");
    Button bClear = new SysButton("CLR");
    Button bBack = new SysButton("<-");
    Button bEnter = new SysButton("ENT");

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

        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setBgColor(0x202020); // dark gray

        Container[] key_rows = {sci0, sci1, num2, num3, num4, num5};
        this.addAll(key_rows); // Keypad Container adds all key rows

        for (Container row : key_rows) {
            for (Component comp : row) {
                if (comp instanceof Button) {
                    ((Button) comp).addActionListener(evt -> {
                        prev_key_pressed = (Button) comp;
                        display.revalidate(); // FIXME: maybe synch issues? Hence, some button presses wont work
                    });
                }
            }
        }
    }

    public void keypadRow0() {
        // Create the 1st row of the keypad (2 rows of 5 buttons)
        sci0.addAll(bSin, bCos, bTan, bLog, bLn, bX2, bX3, yX, overX, bSQRT);
    }

    public void keypadRow1() {
        // Create the 2nd row of the keypad (2 rows of 5 buttons)
        sci1.addAll(stat, grades, store, bPi, bE, recall, root, bell, max, plusminus);
    }

    public void keypadRow2() {
        // Create the 3rd row of the keypad (5 buttons)
        num2.addAll(bEnter, b7, b8, b9, bSlash);
    }

    public void keypadRow3() {
        // Create the 4th row of the keypad (5 buttons)
        num3.addAll(bClear, b4, b5, b6, bStar);
    }

    public void keypadRow4() {
        // Create the 5th row of the keypad (5 buttons)
        num4.addAll(bBack, b1, b2, b3, bMinus);
    }

    public void keypadRow5() {
        // Create the 6th row of the keypad (5 buttons)
        num5.addAll(exit, b0, bDecimal, bPop, bPlus);
    }

    public void buttonPressListeners() {
        digitPressed(new Button[] {b0, b1, b2, b3, b4, b5, b6, b7, b8, b9});
        unaryOpOrConstPressed(new Button[] {bSin, bCos, bTan, bLog, bLn, bX2, bX3, bSQRT, bPi, bE, plusminus, overX});
        binaryOpPressed(new Button[] {bMinus, bSlash, bStar, bYX, bPlus});

        root.addActionListener(evt -> {
            calc.rootCurve(2 / 3.);
            display.updateRegisters();
        });

        bell.addActionListener(evt -> {
            calc.bellCurve(80);
            display.updateRegisters();
        });

        max.addActionListener(evt -> {
            calc.maxCurve();
            display.updateRegisters();
        });

        stat.addActionListener(evt -> {
            display.viewStats();
        });

        grades.addActionListener(evt -> {
            display.viewGrades();
        });

        bDecimal.addActionListener(evt -> {
            calc.digitOrDecimal(bDecimal);
            display.updateRegisters();
        });

        recall.addActionListener(evt -> {
            display.viewLists();
        });

        store.addActionListener(evt -> {
            display.viewLists();
        });

        exit.addActionListener(evt -> {
            display.updateRegisters();
        });

        bPop.addActionListener(evt -> {
            calc.pop();
            display.updateRegisters();
        });

        bClear.addActionListener(evt -> {
            calc.clear();
            display.updateRegisters();
        });

        bBack.addActionListener(evt -> {
            calc.backspace();
            display.updateRegisters();
        });

        bEnter.addActionListener(evt -> {
            calc.enter();
            display.updateRegisters();
        });
    }

    public void digitPressed(Button[] buttons) {
        for (Button button : buttons) {
            button.addActionListener(evt -> {
                if (prev_key_pressed == recall) {
                    calc.loadList(button.getText());
                }
                else if (prev_key_pressed == store) {
                    calc.storeList(button.getText());
                }
                else {
                    calc.digitOrDecimal(button);
                }

                display.updateRegisters();
            });
        }
    }

    public void unaryOpOrConstPressed(Button[] buttons) {
        for (Button button : buttons) {
            button.addActionListener(evt -> {
                calc.unaryOpOrConst(button);
                display.updateRegisters();
            });
        }
    }

    public void binaryOpPressed(Button[] buttons) {
        for (Button button : buttons) {
            button.addActionListener(evt -> {
                calc.binaryOp(button);
                display.updateRegisters();
            });
        }
    }
}
