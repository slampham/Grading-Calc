package org.ecs160.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.plaf.RoundBorder;

public class SysButton extends Button {
    public SysButton(String text) {
        super(text);

        this.getUnselectedStyle().setBorder(
                RoundBorder.create().rectangle(true).color(0x489FB5)
        );

        this.getUnselectedStyle().setBgTransparency(255);
        this.getUnselectedStyle().setBgColor(0x489FB5);
        this.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        this.getSelectedStyle().setFgColor(0x489FB5);

        this.getAllStyles().setMargin(10, 10, 10, 10); // Margin parameters: TOP, BOTTOM, LEFT, RIGHT
    }
}
