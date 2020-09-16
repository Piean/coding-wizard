package com.piean.idea.plugin.coding.dialog;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.util.PsiTypesUtil;
import com.piean.idea.plugin.coding.function.BeanCopyMaker;
import com.piean.idea.plugin.coding.function.DocumentWriter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/9/15
 */
public class VariableSelectionPopupStep extends BaseListPopupStep<String> {
    private final Project project;
    private final Document document;
    private final PsiElement psiElement;
    private final PsiClass psiClass;
    private final PsiLocalVariable variable;
    private final CaretModel caretModel;
    private final Map<String, PsiLocalVariable> options;

    public VariableSelectionPopupStep(Project project, Document document, PsiElement psiElement, PsiClass psiClass, PsiLocalVariable variable, CaretModel caretModel, Map<String, PsiLocalVariable> options) {
        super("Select Other Variable", new ArrayList<>(options.keySet()));
        this.project = project;
        this.document = document;
        this.psiElement = psiElement;
        this.psiClass = psiClass;
        this.variable = variable;
        this.caretModel = caretModel;
        this.options = options;
    }


    @SuppressWarnings("rawtypes")
    @Override
    public @Nullable PopupStep onChosen(String selectedValue, boolean finalChoice) {
        if (finalChoice) {
            PsiLocalVariable localVariable = options.get(selectedValue);
            PsiClass sourceClass = PsiTypesUtil.getPsiClass(localVariable.getType());
            int length = psiElement.getTextOffset() - caretModel.getVisualLineStart();
            BeanCopyMaker maker = new BeanCopyMaker(sourceClass, localVariable, this.psiClass, variable, length);
            String output = maker.output();
            DocumentWriter writer = new DocumentWriter(project, document);
            int offset = psiElement.getTextOffset() + psiElement.getTextLength();
            writer.insert(offset, output);
            return null;
        } else {
            return super.onChosen(selectedValue, false);
        }
    }
}
