package com.piean.idea.plugin.coding.inspection;

import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.highlighting.BasicDomElementsInspection;
import com.intellij.util.xml.highlighting.DomElementAnnotationHolder;
import com.intellij.util.xml.highlighting.DomHighlightingHelper;
import com.piean.idea.plugin.coding.dom.model.MapperElement;
import com.piean.idea.plugin.coding.tool.WizardPsiUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/11/10
 */
public class MybatisXmlInspection extends BasicDomElementsInspection<DomElement> {
    private final Project project;

    public MybatisXmlInspection() {
        super(DomElement.class);
    }

    @SafeVarargs
    public MybatisXmlInspection(@NotNull Class<? extends DomElement> domClass, Class<? extends DomElement>... additionalClasses) {
        super(domClass, additionalClasses);
    }

    @Override
    protected void checkDomElement(DomElement element, DomElementAnnotationHolder holder, DomHighlightingHelper helper) {
        super.checkDomElement(element, holder, helper);
        if (element instanceof MapperElement) {
            String namespace = ((MapperElement) element).getNamespace().toString();
            Optional<PsiClass> clazz = WizardPsiUtil.findClass(, namespace);
        }
        holder.createProblem(element, HighlightSeverity.ERROR, "Test");
    }
}
