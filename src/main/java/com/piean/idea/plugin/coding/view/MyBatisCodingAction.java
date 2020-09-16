package com.piean.idea.plugin.coding.view;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/9/9
 */
public class MyBatisCodingAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Messages.showDialog("Test", "Tt", new String[]{"aa", "bb"}, 0, null);
    }
}
