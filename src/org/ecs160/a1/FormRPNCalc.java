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

        sci1.addAll(bSin, bCos, bTan, bLog, bLn, bX2, bX3, bSQRT, bPi, bE);

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
        Button bSlash = operatorButton("/");

        num2.addAll(bPop, b7, b8, b9, bSlash);

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
        Button bStar = operatorButton("*");

        num3.addAll(bClear, b4, b5, b6, bStar);

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
        Button bMinus = operatorButton("-");

        num4.addAll(bBack, b1, b2, b3, bMinus);

        // Set border around third row
        num4.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.CYAN));

        keypad.add(num4);
    }

    public void keypadRow5() {
        // Create the 5th row of the keypad (5 buttons)
        Button bEnter = otherButton("ENT");
        Button b0 = numberButton("0");
        Button bDecimal = numberButton(".");
        Button bYX = operatorButton("Y^X");
        Button bPlus = operatorButton("+");

        num5.addAll(bEnter, b0, bDecimal, bYX, bPlus);

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
        display.addAll(tRegisterRow, zRegisterRow, yRegisterRow, xRegisterRow);
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
        bSci.getAllStyles().setMargin(50, 50, 20, 20); // Margin parameters: TOP, BOTTOM, LEFT, RIGHT

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
}
