package com.piean.idea.plugin.coding.tool;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;
import com.intellij.psi.util.PsiUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/9/15
 */
public class WizardPsiUtil {
    public static boolean isVariable(PsiElement psiElement) {
        return psiElement instanceof PsiVariable;
    }

    public static boolean isJDKClass(PsiClass psiClass) {
        String className = psiClass.getQualifiedName();
        return className == null || className.startsWith("java.");
    }

    public static List<PsiMethod> extractSetMethod(PsiClass psiClass) {
        List<PsiMethod> methods = new LinkedList<>();
        PsiClass clazz = psiClass;
        while (clazz != null && !isJDKClass(clazz)) {
            for (PsiMethod m : clazz.getMethods()) {
                if (!isSetter(m)) {
                    continue;
                }
                methods.add(m);
            }
            clazz = clazz.getSuperClass();
        }
        return methods;
    }

    public static List<PsiMethod> extractGetMethod(PsiClass psiClass) {
        List<PsiMethod> methods = new LinkedList<>();
        PsiClass clazz = psiClass;
        while (clazz != null && !isJDKClass(clazz)) {
            for (PsiMethod m : clazz.getMethods()) {
                if (!isGetter(m)) {
                    continue;
                }
                methods.add(m);
            }
            clazz = clazz.getSuperClass();
        }
        return methods;
    }

    public static boolean isSetter(@NotNull PsiMethod m) {
        return m.hasModifierProperty(PsiModifier.PUBLIC)
                && !m.hasModifierProperty(PsiModifier.STATIC)
                && m.getName().startsWith("set");
    }

    public static boolean isGetter(@NotNull PsiMethod m) {
        return m.hasModifierProperty(PsiModifier.PUBLIC)
                && !m.hasModifierProperty(PsiModifier.STATIC)
                && (m.getName().startsWith("get") || m.getName().startsWith("is"));
    }

    public static Map<String, PsiLocalVariable> getBlockVariables(PsiLocalVariable localVariable) {
        PsiCodeBlock psiCodeBlock = (PsiCodeBlock) PsiUtil.getVariableCodeBlock(localVariable, localVariable.getParent());
        if (psiCodeBlock == null) {
            return Collections.emptyMap();
        }
        PsiStatement[] statements = psiCodeBlock.getStatements();
        Map<String, PsiLocalVariable> options = new HashMap<>(1);
        for (PsiStatement statement : statements) {
            PsiLocalVariable ov = PsiTreeUtil.getChildOfType(statement, PsiLocalVariable.class);
            if (ov == null) {
                continue;
            }
            final PsiClass oc = PsiTypesUtil.getPsiClass(ov.getType());
            if (oc == null || WizardPsiUtil.isJDKClass(oc)) {
                continue;
            }
            options.put(oc.getQualifiedName() + " : " + ov.getName(), ov);
        }
        return options;
    }


    public static PsiMethod findLikelyGetter(String setMethodName, List<PsiMethod> getMethods) {
        String filedName = setMethodName.replaceFirst("set", "");
        double maxSimilarity = 0D;
        PsiMethod getter = null;
        Iterator<PsiMethod> iterator = getMethods.iterator();
        while (iterator.hasNext()) {
            PsiMethod method = iterator.next();
            String get = method.getName().replaceFirst("get", "");
            double similarity = Jaccard.similarity(filedName, get);
            if (similarity < 0.3) {
                continue;
            }
            if (similarity > maxSimilarity) {
                getter = method;
                maxSimilarity = similarity;
            }
            if (similarity == 1.0) {
                iterator.remove();
                break;
            }
        }
        return getter;
    }
}
