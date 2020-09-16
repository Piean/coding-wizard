package com.piean.idea.plugin.coding.tool;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/9/10
 */
public final class Notifier {
    private final static NotificationGroup ERROR_NOTIFICATION = new NotificationGroup("Coding Wizard Error", NotificationDisplayType.BALLOON, true);

    public static void error(Project project, String content) {
        Notification notification = ERROR_NOTIFICATION.createNotification(content, MessageType.ERROR);
        notification.notify(project);
    }

    public static void warn(Project project, String content) {
        Notification notification = ERROR_NOTIFICATION.createNotification(content, MessageType.WARNING);
        notification.notify(project);
    }
}
