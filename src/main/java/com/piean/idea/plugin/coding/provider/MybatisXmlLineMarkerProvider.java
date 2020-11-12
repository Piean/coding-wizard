package com.piean.idea.plugin.coding.provider;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomUtil;
import com.piean.idea.plugin.coding.constants.Icons;
import com.piean.idea.plugin.coding.dom.model.IdElement;
import com.piean.idea.plugin.coding.dom.model.MapperElement;
import com.piean.idea.plugin.coding.tool.MybatisDomUtil;
import com.piean.idea.plugin.coding.tool.WizardPsiUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/11/11
 */
public class MybatisXmlLineMarkerProvider implements LineMarkerProvider {
    private static final List<String> TARGET_TOKEN = Arrays.asList("mapper", "select", "insert", "update", "delete");

    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
        if (!isStatementStartToken(element)) {
            return null;
        }
        Optional<PsiNameIdentifierOwner> optional = this.findTargetPsiElement((XmlToken) element);
        return optional.map(psi ->
                new LineMarkerInfo<>(element, element.getTextRange(), Icons.ARROW_LEFT,
                        e -> "Navigate to the method: " + psi.getText() + " in the Mapper-Interface file",
                        (f, t) -> {
                            Navigatable navigatable = (Navigatable) psi.getNavigationElement();
                            navigatable.navigate(true);
                        }, GutterIconRenderer.Alignment.CENTER)
        ).orElse(null);
    }

    @Override
    public void collectSlowLineMarkers(@NotNull List<? extends PsiElement> elements, @NotNull Collection<? super LineMarkerInfo<?>> result) {

    }


    private boolean isStatementStartToken(PsiElement element) {
        if (!(element instanceof XmlToken)) {
            return false;
        }
        DomElement domElement = DomUtil.getDomElement(element);
        return TARGET_TOKEN.contains(element.getText()) && Objects.equals(element.getPrevSibling().getText(), "<");
    }

    private Optional<PsiNameIdentifierOwner> findTargetPsiElement(XmlToken xmlToken) {
        PsiElement parent = xmlToken.getParent();
        if (parent instanceof XmlTag) {
            DomElement domElement = DomUtil.getDomElement(parent);
            if (domElement instanceof MapperElement) {
                String namespace = ((MapperElement) domElement).getNamespace().toString();
                Optional<PsiClass> clazz = WizardPsiUtil.findClass(parent.getProject(), namespace);
                if (clazz.isPresent()) {
                    return Optional.of(clazz.get());
                }
            } else {
                if (domElement instanceof IdElement) {
                    String namespace = MybatisDomUtil.getNamespace(domElement);
                    String methodName = MybatisDomUtil.getId((IdElement) domElement);
                    Optional<PsiMethod> method = WizardPsiUtil.findMethod(parent.getProject(), namespace, methodName);
                    if (method.isPresent()) {
                        return Optional.of(method.get());
                    }
                }
            }
        }
        return Optional.empty();
    }
}
