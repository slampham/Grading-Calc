package org.ecs160.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.plaf.RoundBorder;

public class ScienceButton extends Button {
    public ScienceButton(String text) {
        super(text);

        this.getUnselectedStyle().setBorder(
                RoundBorder.create().rectangle(true).color(0x334E58)
        );

        this.getUnselectedStyle().setBgTransparency(255);
        this.getUnselectedStyle().setBgColor(0x334E58);
        this.getUnselectedStyle().setFgColor(ColorUtil.WHITE);

        this.getSelectedStyle().setFgColor(0x334E58);

        this.getAllStyles().setMargin(15, 15, 15, 15); // Margin parameters: TOP, BOTTOM, LEFT, RIGHT
    }
}
