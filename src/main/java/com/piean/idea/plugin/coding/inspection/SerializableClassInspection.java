package com.piean.idea.plugin.coding.inspection;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiIdentifier;
import com.piean.idea.plugin.coding.error.HintMsg;
import com.piean.idea.plugin.coding.inspection.fix.SerializableClassQuickFixAction;
import com.piean.idea.plugin.coding.tool.Asserts;
import com.piean.idea.plugin.coding.tool.WizardPsiUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SerializableClassInspection extends AbstractBaseJavaLocalInspectionTool {

    @Override
    public ProblemDescriptor @Nullable [] checkClass(@NotNull PsiClass aClass, @NotNull InspectionManager manager, boolean isOnTheFly) {
        PsiIdentifier nameIdentifier = aClass.getNameIdentifier();
        Asserts.notNull(nameIdentifier, HintMsg.NEED_NAME_IDENTIFIER);
        if (!WizardPsiUtil.isUserClass(aClass.getProject(), aClass)) {
            return null;
        }
        PsiField field = aClass.findFieldByName("serialVersionUID", false);
        if (field == null) {
            SerializableClassQuickFixAction fixAction = new SerializableClassQuickFixAction();
            ProblemDescriptor problemDescriptor = manager.createProblemDescriptor(nameIdentifier, "No serialVersionUID field", fixAction, ProblemHighlightType.WARNING, false);
            return new ProblemDescriptor[]{problemDescriptor};
        }
        return null;
    }
}
