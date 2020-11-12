package com.piean.idea.plugin.coding.dom.model;

import com.intellij.psi.PsiClass;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/11/10
 */
public interface InsertElement extends IdElement {
    @NotNull
    @Attribute("parameterType")
//    @Convert(AliasConverter.class)
    GenericAttributeValue<PsiClass> getParameterType();

    @SubTagList("selectKey")
    List<SelectKeyElement> getSelectKey();
}
