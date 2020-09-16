package com.piean.idea.plugin.coding.view;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;
import com.piean.idea.plugin.coding.error.HintMsg;
import com.piean.idea.plugin.coding.error.WizardException;
import com.piean.idea.plugin.coding.function.AllSetterMaker;
import com.piean.idea.plugin.coding.function.DocumentWriter;
import com.piean.idea.plugin.coding.tool.Asserts;
import com.piean.idea.plugin.coding.tool.Notifier;
import com.piean.idea.plugin.coding.tool.WizardPsiUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/9/8
 */
@SuppressWarnings("DuplicatedCode")
public class BeanSetterAction extends AnAction {
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
            if (psiClass == null || WizardPsiUtil.isJDKClass(psiClass)) {
                Notifier.warn(project, "Not user-level class");
                return;
            }
            final Document document = editor.getDocument();
            PsiElement statement = variable.getParent();
            int length = statement.getTextOffset() - caretModel.getVisualLineStart();
            AllSetterMaker allSetterMaker = new AllSetterMaker(psiClass, variable, length);
            String output = allSetterMaker.output();
            DocumentWriter writer = new DocumentWriter(project, document);
            int offset = statement.getTextOffset() + statement.getTextLength();
            writer.insert(offset, output);
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
