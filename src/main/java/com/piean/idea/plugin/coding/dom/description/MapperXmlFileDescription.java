package com.piean.idea.plugin.coding.dom.description;

import com.intellij.util.xml.DomFileDescription;
import com.piean.idea.plugin.coding.dom.model.MapperElement;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/11/11
 */
public class MapperXmlFileDescription extends DomFileDescription<MapperElement> {
    public MapperXmlFileDescription() {
        super(MapperElement.class, "mapper");
    }
}
