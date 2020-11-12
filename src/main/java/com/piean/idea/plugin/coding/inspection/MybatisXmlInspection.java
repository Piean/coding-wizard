package com.piean.idea.plugin.coding.inspection;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.highlighting.BasicDomElementsInspection;
import com.intellij.util.xml.highlighting.DomElementAnnotationHolder;
import com.intellij.util.xml.highlighting.DomHighlightingHelper;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/11/10
 */
public class MybatisXmlInspection extends BasicDomElementsInspection<DomElement> {
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
    }
}
