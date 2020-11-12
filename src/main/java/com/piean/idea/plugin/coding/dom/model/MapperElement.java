package com.piean.idea.plugin.coding.dom.model;

import com.intellij.util.xml.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/11/10
 */
public interface MapperElement extends DomElement {
    @Required
    @NameValue
    @NotNull
    @Attribute("namespace")
    GenericAttributeValue<String> getNamespace();

    @NotNull
    @SubTagList("resultMap")
    List<ResultMapElement> getResultMaps();

    @NotNull
    @SubTagsList({"insert", "update", "delete", "select"})
    List<IdElement> getMethodElements();

    @NotNull
    @SubTagList("sql")
    List<SQLElement> getSqls();

    @NotNull
    @SubTagList("insert")
    List<InsertElement> getInserts();

    @NotNull
    @SubTagList("update")
    List<UpdateElement> getUpdates();

    @NotNull
    @SubTagList("delete")
    List<DeleteElement> getDeletes();

    @NotNull
    @SubTagList("select")
    List<SelectElement> getSelects();

    @SubTagsList(value = {"insert", "update", "delete", "select"}, tagName = "select")
    SelectElement addSelect(int index);

    @SubTagsList(value = {"insert", "update", "delete", "select"}, tagName = "update")
    UpdateElement addUpdate(int index);

    @SubTagsList(value = {"insert", "update", "delete", "select"}, tagName = "insert")
    InsertElement addInsert(int index);

    @SubTagsList(value = {"insert", "update", "delete", "select"}, tagName = "delete")
    DeleteElement addDelete(int index);
}
