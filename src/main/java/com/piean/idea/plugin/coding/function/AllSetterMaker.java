package com.piean.idea.plugin.coding.function;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.PsiMethod;
import com.piean.idea.plugin.coding.tool.WizardPsiUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/9/15
 */
public class AllSetterMaker {
    private final Project project;
    private final PsiClass psiClass;
    private final PsiLocalVariable variable;
    private final String whitespace;

    public AllSetterMaker(Project project, PsiClass psiClass, PsiLocalVariable variable, int blankLength) {
        this.project = project;
        this.psiClass = psiClass;
        this.variable = variable;
        this.whitespace = StringUtils.center("", blankLength);
    }

    public String output() {
        List<PsiMethod> methods = WizardPsiUtil.extractSetMethod(project, psiClass);
        String variableName = variable.getName();
        StringBuilder sb = new StringBuilder();
        methods.forEach(m -> {
            sb.append("\n").append(whitespace).append(variableName).append(".");
            sb.append(m.getName()).append("();");
        });
        return sb.toString();
    }
}
