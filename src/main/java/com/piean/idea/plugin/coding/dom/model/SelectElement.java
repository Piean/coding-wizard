package com.piean.idea.plugin.coding.dom.model;

import com.intellij.psi.PsiClass;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/11/10
 */
public interface SelectElement extends IdElement {
    @NotNull
    @Attribute("parameterType")
//    @Convert(AliasConverter.class)
    GenericAttributeValue<PsiClass> getParameterType();

    @NotNull
    @Attribute("resultType")
//    @Convert(AliasConverter.class)
    GenericAttributeValue<PsiClass> getResultType();

    @NotNull
    @Attribute("resultMap")
//    @Convert(ResultMapConverter.class)
    GenericAttributeValue<ResultMapElement> getResultMap();
}
