package com.piean.idea.plugin.coding.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;
import com.piean.idea.plugin.coding.dialog.VariableSelectionPopupStep;
import com.piean.idea.plugin.coding.error.HintMsg;
import com.piean.idea.plugin.coding.error.WizardException;
import com.piean.idea.plugin.coding.tool.Asserts;
import com.piean.idea.plugin.coding.tool.Notifier;
import com.piean.idea.plugin.coding.tool.WizardPsiUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/9/15
 */
@SuppressWarnings("DuplicatedCode")
public class BeanCopyAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        try {
            Asserts.notNull(project, HintMsg.NEED_PROJECT);
            final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
            final PsiFile file = e.getRequiredData(CommonDataKeys.PSI_FILE);
            final CaretModel caretModel = editor.getCaretModel();
            final PsiElement psiElement = file.findElementAt(caretModel.getOffset());
            final PsiLocalVariable variable = PsiTreeUtil.getParentOfType(psiElement, PsiLocalVariable.class);
            Asserts.notNull(variable, HintMsg.NEED_LOCAL_VARIABLE);
            final PsiClass psiClass = PsiTypesUtil.getPsiClass(variable.getType());
            if (!WizardPsiUtil.isUserClass(project, psiClass)) {
                Notifier.warn(project, "[" + variable.getName() + "] Not a user-level class instance, place check user-level packages config");
                return;
            }

            final Document document = editor.getDocument();
            final PsiElement parent = variable.getParent();
            Map<String, PsiVariable> blockVariables = WizardPsiUtil.getBlockVariables(project, variable);
            blockVariables.remove(psiClass.getQualifiedName() + " : " + variable.getName());
            VariableSelectionPopupStep step = new VariableSelectionPopupStep(project, document, parent, psiClass, variable, caretModel, blockVariables);
            ListPopup popup = JBPopupFactory.getInstance().createListPopup(step);
            popup.showInBestPositionFor(editor);
        } catch (WizardException we) {
            Notifier.error(project, we.getMessage());
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        PsiElement psiElement = e.getData(LangDataKeys.PSI_ELEMENT);
        e.getPresentation().setEnabledAndVisible(available(psiElement));
    }

    private boolean available(PsiElement psiElement) {
        return WizardPsiUtil.isVariable(psiElement);
    }
}
