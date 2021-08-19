package com.piean.idea.plugin.coding.action;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiIdentifier;
import com.piean.idea.plugin.coding.error.HintMsg;
import com.piean.idea.plugin.coding.inspection.SerializableClassQuickFixAction;
import com.piean.idea.plugin.coding.tool.Asserts;
import com.piean.idea.plugin.coding.tool.WizardPsiUtil;
import org.jetbrains.annotations.NotNull;

public class ClassQuickFixAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder holder) {
        if (!(psiElement instanceof PsiClass)) {
            return;
        }
        final PsiClass psiClass = (PsiClass) psiElement;
        PsiIdentifier nameIdentifier = psiClass.getNameIdentifier();
        Asserts.notNull(nameIdentifier, HintMsg.NEED_NAME_IDENTIFIER);
        if (!WizardPsiUtil.isUserClass(psiElement.getProject(), psiClass)) {
            return;
        }
        PsiField field = psiClass.findFieldByName("serialVersionUID", false);
        if (field == null) {
            holder.newAnnotation(HighlightSeverity.WARNING, "The serialization class does not declare field 'serialVersionUID' correctly")
                    .range(nameIdentifier)
                    .withFix(new SerializableClassQuickFixAction(psiClass.getName())).create();
        } else {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(field).textAttributes(TextAttributesKey.createTextAttributesKey("text attr")).create();
        }
    }
}
