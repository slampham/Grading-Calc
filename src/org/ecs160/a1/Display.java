package org.ecs160.a1;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;

import java.util.*;
import java.util.List;

public class Display extends Container {
    String mode = "viewRegisters";

    Label tRegister = new Label("T: ");
    Label zRegister = new Label("Z: ");
    Label yRegister = new Label("Y: ");
    Label xRegister = new Label("X: ");

    Calc calc;

    public Display(Layout layout, Calc calc) {
        super(layout);
        this.calc = calc;

        // four containers to populate display
        Container tRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container zRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container yRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container xRegisterRow = new Container(new BoxLayout(BoxLayout.X_AXIS));

        tRegisterRow.add(tRegister);
        zRegisterRow.add(zRegister);
        yRegisterRow.add(yRegister);
        xRegisterRow.add(xRegister);

        this.addAll(tRegisterRow, zRegisterRow, yRegisterRow, xRegisterRow); // Add registers to Display Container
        this.updateRegisters();
        this.revalidate();
    }

    public void updateRegisters() {
        /* Get registers after doing button press, and update display */
        double[] registers = calc.getRegisters();

        tRegister.setText("T: " + registers[2]);
        zRegister.setText("Z: " + registers[1]);
        yRegister.setText("Y: " + registers[0]);
        xRegister.setText("X: " + calc.X);
    }
    
    public void viewLists() {
        List<Stack<Double>> stacks = new ArrayList<>(calc.stacks.values()); // WARNING: stacks iterated in reverse order (ie bottom up)

        tRegister.setText("RCL 0: " + reverse(stacks.get(0)).toString());
        zRegister.setText("RCL 1: " + reverse(stacks.get(1)).toString());
        yRegister.setText("RCL 2: " + reverse(stacks.get(2)).toString());
        xRegister.setText("RCL 3: " + reverse(stacks.get(3)).toString());
    }

    public void viewSummary() {
        tRegister.setText("Grades: 8 Z's | 5 F's | 20 C's | 32 B's | 20 A's");
        zRegister.setText("Standard Deviation: 17");
        yRegister.setText("Min: 0 | Max: 97");
        xRegister.setText("Mean: 77 | Median: 81 | Mode: 0");
    }

    public static List<Double> reverse(List<Double> list) {
        List<Double> result = new ArrayList<>(list);
        Collections.reverse(result);
        return result;
    }
}
