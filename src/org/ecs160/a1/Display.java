package org.ecs160.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Border;

import java.util.*;
import java.util.List;

public class Display extends Container {
    Label tRegister = new Label("T: ");
    Label zRegister = new Label("Z: ");
    Label yRegister = new Label("Y: ");
    Label xRegister = new Label("X: ");

    Calc calc;

    public Display(Layout layout, Calc calc) {
        super(layout);
        this.calc = calc;

        // change style of registers
        setRegisterStyles();

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

        getAllStyles().setBgTransparency(255);
        getAllStyles().setBgColor(0x202020);

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
        tRegister.setText("RCL 0: " + calc.lists().get(0).toString());
        zRegister.setText("RCL 1: " + calc.lists().get(1).toString());
        yRegister.setText("RCL 2: " + calc.lists().get(2).toString());
        xRegister.setText("RCL 3: " + calc.lists().get(3).toString());
    }

    public void viewStats() {
        Double[] list = calc.stack.toArray(new Double[calc.stack.size()]);

        double mean = calc.mean(list);
        double median = calc.median(list);
        double mode = calc.mode(list);
        double max = calc.max(list);
        double min = calc.min(list);
        double std_dev = calc.stddev(list);

        tRegister.setText("SUMMARY:");
        zRegister.setText(String.format("Mean: %.1f | Median: %.1f", mean, median));
        yRegister.setText(String.format("Mode: %.1f | Std Dev: %.1f", mode, std_dev));
        xRegister.setText(String.format("Min: %.1f | Max: %.1f", min, max));
    }

    public void viewGrades() {
        HashMap<String, Integer> grade_hist = calc.gradeHistogram();

        tRegister.setText("GRADES:");
        zRegister.setText(printGrade(new String[]{"A", "A-", "B+", "B", "B-"}, grade_hist));
        yRegister.setText(printGrade(new String[]{"C+", "C", "C-", "D+", "D"}, grade_hist));
        xRegister.setText(printGrade(new String[]{"D-", "F", "Z"}, grade_hist));
    }

    public void setRegisterStyles() {
        Font largePlainProportionalFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE);

        tRegister.getAllStyles().setFont(largePlainProportionalFont);
        zRegister.getAllStyles().setFont(largePlainProportionalFont);
        yRegister.getAllStyles().setFont(largePlainProportionalFont);
        xRegister.getAllStyles().setFont(largePlainProportionalFont);

        tRegister.getAllStyles().setFgColor(ColorUtil.WHITE);
        zRegister.getAllStyles().setFgColor(ColorUtil.WHITE);
        yRegister.getAllStyles().setFgColor(ColorUtil.WHITE);
        xRegister.getAllStyles().setFgColor(ColorUtil.WHITE);
    }

    private String printGrade(String[] grades, HashMap<String, Integer> grade_hist) {
        StringBuilder out = new StringBuilder();

        for (String grade : grades) {
            out.append(grade_hist.get(grade).toString()).append(grade).append(" | ");
        }

        return out.toString();
    }
}
