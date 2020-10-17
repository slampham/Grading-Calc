package org.ecs160.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;

public class FormRPNCalc extends Form {
    // four containers to populate display
    Container tRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));
    Container zRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));
    Container yRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));
    Container xRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));

    Container display = new Container(new BoxLayout(BoxLayout.Y_AXIS));

    // five containers to populate keypad
    Container sci1 = new Container(new GridLayout(2,5));
    Container num2 = new Container(new GridLayout(1, 5));
    Container num3 = new Container(new GridLayout(1, 5));
    Container num4 = new Container(new GridLayout(1, 5));
    Container num5 = new Container(new GridLayout(1, 5));

    Container keypad = new Container(new GridLayout(5,1));

    public FormRPNCalc() {
        setLayout(new BorderLayout());
        setTitle("RPN Calculator v0.1");

        // create buttons for all five rows and add them to the keypad container
        keypadRow1();
        keypadRow2();
        keypadRow3();
        keypadRow4();
        keypadRow5();

        // generate the display
        createTempDisplay();

        add(BorderLayout.NORTH, display);
        add(BorderLayout.CENTER, keypad);
    }

    public void updateDisplay() {
        // functions copied from the lecture video
//        setRow(tRegisterRow, "T:", calc.getT());
//        setRow(zRegisterRow, "Z:", calc.getZ());
//        setRow(yRegisterRow, "Y:", calc.getY());
//        setRow(xRegisterRow, "X:", calc.getX());
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
        sci1.add(bSin);
        sci1.add(bCos);
        sci1.add(bTan);
        sci1.add(bLog);
        sci1.add(bLn);
        sci1.add(bX2);
        sci1.add(bX3);
        sci1.add(bSQRT);
        sci1.add(bPi);
        sci1.add(bE);

        // Set border around top 10 buttons
        sci1.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.YELLOW));

        keypad.add(sci1);
    }

    public void keypadRow2() {
        // Create the 2nd row of the keypad (5 buttons)
        Button bPop = otherButton("pop");
        Button b7 = numberButton("7");
        Button b8 = numberButton("8");
        Button b9 = numberButton("9");
        Button bSlash = operationButton("/");
        num2.add(bPop);
        num2.add(b7);
        num2.add(b8);
        num2.add(b9);
        num2.add(bSlash);

        // Set border around second row
        num2.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.MAGENTA));

        keypad.add(num2);
    }

    public void keypadRow3() {
        // Create the 3rd row of the keypad (5 buttons)
        Button bClear = otherButton("CLR");
        Button b4 = numberButton("4");
        Button b5 = numberButton("5");
        Button b6 = numberButton("6");
        Button bStar = operationButton("*");
        num3.add(bClear);
        num3.add(b4);
        num3.add(b5);
        num3.add(b6);
        num3.add(bStar);

        // Set border around third row
        num3.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLUE));

        keypad.add(num3);
    }

    public void keypadRow4() {
        // Create the 4th row of the keypad (5 buttons)
        Button bBack = otherButton("<-");
        Button b1 = numberButton("1");
        Button b2 = numberButton("2");
        Button b3 = numberButton("3");
        Button bMinus = operationButton("-");
        num4.add(bBack);
        num4.add(b1);
        num4.add(b2);
        num4.add(b3);
        num4.add(bMinus);

        // Set border around third row
        num4.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.CYAN));

        keypad.add(num4);
    }

    public void keypadRow5() {
        // Create the 5th row of the keypad (5 buttons)
        Button bEnter = otherButton("ENT");
        Button b0 = numberButton("0");
        Button bDecimal = numberButton(".");
        Button bYX = operationButton("Y^X");
        Button bPlus = operationButton("+");
        num5.add(bEnter);
        num5.add(b0);
        num5.add(bDecimal);
        num5.add(bYX);
        num5.add(bPlus);

        // Set border around third row
        num5.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.GREEN));

        keypad.add(num5);
    }

    public void createTempDisplay() {
        // just display text for the registers until logic can update calc
        Label tRegister = new Label("T: ");
        tRegisterRow.add(tRegister);
        Label zRegister = new Label("Z: ");
        zRegisterRow.add(zRegister);
        Label yRegister = new Label("Y: ");
        yRegisterRow.add(yRegister);
        Label xRegister = new Label("X: ");
        xRegisterRow.add(xRegister);

        // add RegisterRows to the display container
        display.add(tRegisterRow);
        display.add(zRegisterRow);
        display.add(yRegisterRow);
        display.add(xRegisterRow);
    }

    public Button numberButton(String text) {
        // give style to the number buttons
        Button bNum = new Button(text);

        bNum.getUnselectedStyle().setBgTransparency(255);
        bNum.getUnselectedStyle().setBgColor(ColorUtil.LTGRAY);
        bNum.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        // can add selectedStyle for when button is pressed
        // otherwise it defaults to white BG with light blue text

        bNum.getAllStyles().setMargin(Component.TOP, 50);
        bNum.getAllStyles().setMargin(Component.RIGHT, 20);
        bNum.getAllStyles().setMargin(Component.LEFT, 20);
        bNum.getAllStyles().setMargin(Component.BOTTOM, 50);

        return bNum;
    }

    public Button scienceButton(String text) {
        // give style to the number buttons
        Button bSci = new Button(text);

        bSci.getUnselectedStyle().setBgTransparency(255);
        bSci.getUnselectedStyle().setBgColor(ColorUtil.GREEN);
        bSci.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        // can add selectedStyle for when button is pressed
        // otherwise it defaults to white BG with light blue text

        bSci.getAllStyles().setMargin(Component.TOP, 50);
        bSci.getAllStyles().setMargin(Component.RIGHT, 20);
        bSci.getAllStyles().setMargin(Component.LEFT, 20);
        bSci.getAllStyles().setMargin(Component.BOTTOM, 50);

        return bSci;
    }

    public Button otherButton(String text) {
        // give style to the other buttons (not numbers)
        Button bOther = new Button(text);

        bOther.getUnselectedStyle().setBgTransparency(255);
        bOther.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
        bOther.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        // can add selectedStyle for when button is pressed
        // otherwise it defaults to white BG with light blue text

        bOther.getAllStyles().setMargin(Component.TOP, 50);
        bOther.getAllStyles().setMargin(Component.RIGHT, 20);
        bOther.getAllStyles().setMargin(Component.LEFT, 20);
        bOther.getAllStyles().setMargin(Component.BOTTOM, 50);

        return bOther;
    }

    public Button operationButton(String text) {
        // give style to the other buttons (not numbers)
        Button bOperation = new Button(text);

        bOperation.getUnselectedStyle().setBgTransparency(255);
        bOperation.getUnselectedStyle().setBgColor(ColorUtil.GRAY);
        bOperation.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        // can add selectedStyle for when button is pressed
        // otherwise it defaults to white BG with light blue text

        bOperation.getAllStyles().setMargin(Component.TOP, 50);
        bOperation.getAllStyles().setMargin(Component.RIGHT, 20);
        bOperation.getAllStyles().setMargin(Component.LEFT, 20);
        bOperation.getAllStyles().setMargin(Component.BOTTOM, 50);

        return bOperation;
    }
}
