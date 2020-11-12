package com.piean.idea.plugin.coding.tool;

import com.google.common.collect.Collections2;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Processor;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomService;
import com.intellij.util.xml.DomUtil;
import com.piean.idea.plugin.coding.dom.model.IdElement;
import com.piean.idea.plugin.coding.dom.model.MapperElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/11/11
 */
public class MybatisDomUtil {
    public static void process(@NotNull PsiMethod psiMethod, @NotNull Processor<DomElement> processor) {
        PsiClass psiClass = psiMethod.getContainingClass();
        if (null == psiClass) {
            return;
        }
        String id = psiClass.getQualifiedName() + "." + psiMethod.getName();
        Collection<MapperElement> mappers = findMappers(psiMethod.getProject());
        for (MapperElement mapper : mappers) {
            for (IdElement element : mapper.getMethodElements()) {
                if (getMethodId(element).equals(id)) {
                    processor.process(element);
                }
            }
        }
    }

    public static void process(@NotNull PsiClass clazz, @NotNull Processor<DomElement> processor) {
        String className = clazz.getQualifiedName();
        Collection<MapperElement> mappers = findMappers(clazz.getProject());
        for (MapperElement mapper : mappers) {
            if (Objects.equals(getNamespace(mapper), className)) {
                processor.process(mapper);
            }
        }
    }

    public static Collection<MapperElement> findMappers(@NotNull Project project) {
        return findDomElements(project, MapperElement.class);
    }

    public static MapperElement getMapper(@NotNull DomElement element) {
        Optional<MapperElement> optional = Optional.ofNullable(DomUtil.getParentOfType(element, MapperElement.class, true));
        return optional.orElseThrow(() -> new IllegalArgumentException("Unknown element"));
    }

    public static String getNamespace(@NotNull MapperElement mapper) {
        String ns = mapper.getNamespace().getStringValue();
        return ns != null ? ns : "";
    }

    public static String getNamespace(@NotNull DomElement element) {
        return getNamespace(getMapper(element));
    }

    public static <T extends IdElement> String getId(@NotNull T element) {
        return element.getId().getRawText();
    }

    public static <T extends IdElement> String getMethodId(@NotNull IdElement element) {
        return getNamespace(element) + "." + getId(element);
    }

    public static <T extends DomElement> Collection<T> findDomElements(@NotNull Project project, Class<T> clazz) {
        GlobalSearchScope scope = GlobalSearchScope.projectScope(project);
        List<DomFileElement<T>> elements = DomService.getInstance().getFileElements(clazz, project, scope);
        return Collections2.transform(elements, DomFileElement::getRootElement);
    }

    public static boolean isMapperXml(@NotNull PsiElement element) {
        return false;
    }
}
