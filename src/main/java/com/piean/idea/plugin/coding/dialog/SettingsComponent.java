package com.piean.idea.plugin.coding.dialog;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/9/28
 */
public class SettingsComponent {
    private final JPanel myMainPanel;
    private final JBTextField userLevelPackage = new JBTextField();
//    public final JBCheckBox enableAllSetter = new JBCheckBox("Enable 'AllSetter'?");

    public SettingsComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Enter user level packages:"), userLevelPackage, 1, false)
//                .addComponent(enableAllSetter, 1)
//                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getMainPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return userLevelPackage;
    }

    public String getUserLevelPackage() {
        return userLevelPackage.getText();
    }

    public void setUserLevelPackage(String text) {
        userLevelPackage.setText(text);
    }

//    public boolean isEnableAllSetter() {
//        return enableAllSetter.isSelected();
//    }
//
//    public void setEnableFunction(boolean selected) {
//        enableAllSetter.setSelected(selected);
//    }
}
