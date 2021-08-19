package com.piean.idea.plugin.coding.inspection;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public class SerializableClassQuickFixAction extends BaseIntentionAction {
    private final String className;

    public SerializableClassQuickFixAction(String className) {
        this.className = className;
    }

    @Override
    public @IntentionName @NotNull String getText() {
        return "Add serialVersionUID field for class " + className;
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return "Add serialVersionUID field";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile psiFile) {
        return true;
        // PsiClass psiClass = (PsiClass) psiFile;
        // return WizardPsiUtil.isUserClass(project, psiClass) && WizardPsiUtil.isSerializableClass(psiClass);
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {
        int lineEnd = editor.getCaretModel().getVisualLineEnd();
        editor.getCaretModel().moveToOffset(lineEnd);
        long value = psiFile.hashCode();
        EditorModificationUtil.insertStringAtCaret(editor, "private static final long serialVersionUID = " + value + "L;\n");
    }
}
