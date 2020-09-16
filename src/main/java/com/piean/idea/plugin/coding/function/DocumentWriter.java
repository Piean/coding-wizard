package com.piean.idea.plugin.coding.function;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/9/16
 */
public class DocumentWriter {
    private final Project project;
    private final Document document;

    public DocumentWriter(Project project, Document document) {
        this.project = project;
        this.document = document;
    }

    public void insert(int offset, String content) {
        WriteCommandAction.runWriteCommandAction(project, () -> document.insertString(offset, content));
    }
}
