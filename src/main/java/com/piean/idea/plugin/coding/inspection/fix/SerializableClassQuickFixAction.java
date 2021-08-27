package com.piean.idea.plugin.coding.inspection.fix;

import com.intellij.codeInsight.daemon.impl.quickfix.CreateFieldFromUsageHelper;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.util.VisibilityUtil;
import com.siyeh.ig.fixes.SerialVersionUIDBuilder;
import org.jetbrains.annotations.NotNull;

public class SerializableClassQuickFixAction implements LocalQuickFix {

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return "Add serialVersionUID field";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
        PsiElement psiElement = problemDescriptor.getPsiElement();
        if (!(psiElement.getParent() instanceof PsiClass)) {
            return;
        }
        PsiClass targetClass = (PsiClass) psiElement.getParent();
        final PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
        PsiField psiField = factory.createField("serialVersionUID", PsiType.LONG);
        final PsiModifierList modifierList = psiField.getModifierList();
        if (modifierList != null) {
            VisibilityUtil.setVisibility(modifierList, PsiModifier.PRIVATE);
            VisibilityUtil.setVisibility(modifierList, PsiModifier.STATIC);
            VisibilityUtil.setVisibility(modifierList, PsiModifier.FINAL);
        }
        final long serialVersionUID = SerialVersionUIDBuilder.computeDefaultSUID(targetClass);
        PsiExpression expression = factory.createExpressionFromText(serialVersionUID + "L", psiField);
        psiField.setInitializer(expression);
        CreateFieldFromUsageHelper.insertField(targetClass, psiField, psiElement);
    }
}
