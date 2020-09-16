package com.piean.idea.plugin.coding.function;

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
public class BeanCopyMaker {
    private final PsiClass sourceClass;
    private final PsiLocalVariable sourceVariable;
    private final PsiClass targetClass;
    private final PsiLocalVariable targetVariable;
    private final String whitespace;

    public BeanCopyMaker(PsiClass sourceClass, PsiLocalVariable sourceVariable, PsiClass targetClass, PsiLocalVariable targetVariable, int blankLength) {
        this.sourceClass = sourceClass;
        this.sourceVariable = sourceVariable;
        this.targetClass = targetClass;
        this.targetVariable = targetVariable;
        this.whitespace = StringUtils.center("", blankLength);
    }

    public String output() {
        List<PsiMethod> setMethods = WizardPsiUtil.extractSetMethod(targetClass);
        String targetVariableName = targetVariable.getName();
        List<PsiMethod> getMethods = WizardPsiUtil.extractGetMethod(sourceClass);
        String sourceVariableName = sourceVariable.getName();
        StringBuilder sb = new StringBuilder();
        setMethods.forEach(m -> {
            sb.append("\n").append(whitespace);
            sb.append(targetVariableName).append(".").append(m.getName()).append("(");
            //Find the get method that corresponds to the most matching source object
            PsiMethod getter = WizardPsiUtil.findLikelyGetter(m.getName(), getMethods);
            if (getter != null) {
                sb.append(sourceVariableName).append(".").append(getter.getName()).append("()");
            }
            sb.append(");");
        });
        return sb.toString();
    }
}
