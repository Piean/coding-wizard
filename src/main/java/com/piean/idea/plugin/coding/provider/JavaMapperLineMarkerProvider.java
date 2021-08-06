package com.piean.idea.plugin.coding.provider;

import com.google.common.collect.Collections2;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.util.CommonProcessors;
import com.intellij.util.xml.DomElement;
import com.piean.idea.plugin.coding.constants.Icons;
import com.piean.idea.plugin.coding.tool.MybatisDomUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/11/10
 */
public class JavaMapperLineMarkerProvider extends RelatedItemLineMarkerProvider {
    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        CommonProcessors.CollectProcessor<DomElement> processor = new CommonProcessors.CollectProcessor<>();
        if (element instanceof PsiMethod) {
            MybatisDomUtil.process((PsiMethod) element, processor);
        } else if (element instanceof PsiClass) {
            MybatisDomUtil.process((PsiClass) element, processor);
        } else {
            return;
        }
        Collection<DomElement> results = processor.getResults();
        if (results.isEmpty()) {
            return;
        }
        NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(Icons.ARROW_RIGHT)
                .setAlignment(GutterIconRenderer.Alignment.CENTER)
                .setTargets(Collections2.transform(results, DomElement::getXmlTag))
                .setTooltipTitle("Navigate to the method of the Mapper XML file");
        RelatedItemLineMarkerInfo<PsiElement> lineMarkerInfo = builder.createLineMarkerInfo(Objects.requireNonNull(((PsiNameIdentifierOwner) element).getNameIdentifier()));
        result.add(lineMarkerInfo);
    }
}