package com.piean.idea.plugin.coding.dom.model;

import com.intellij.util.xml.*;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/11/10
 */
public interface IdElement extends DomElement {

    @Required
    @NameValue
    @Attribute("id")
    GenericAttributeValue<String> getId();

    @TagValue
    void setValue(String content);

    @TagValue
    String getValue();
}
