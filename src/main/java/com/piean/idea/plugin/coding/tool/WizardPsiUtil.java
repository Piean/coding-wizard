package com.piean.idea.plugin.coding.tool;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTypesUtil;
import com.piean.idea.plugin.coding.config.ProjectSettingsState;
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

    public static boolean isUserClass(Project project, PsiClass psiClass) {
        if (psiClass == null) {
            return false;
        }
        boolean isNotJDK = !isJDKClass(psiClass);
        ProjectSettingsState state = ProjectSettingsState.getInstance(project);
        String packages = state.getUserPackages();
        boolean isInPackage = Objects.requireNonNull(psiClass.getQualifiedName()).startsWith(packages);
        return isNotJDK && isInPackage;
    }

    public static List<PsiMethod> extractSetMethod(Project project, PsiClass psiClass) {
        List<PsiMethod> methods = new LinkedList<>();
        PsiClass clazz = psiClass;
        while (isUserClass(project, clazz)) {
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

    public static List<PsiMethod> extractGetMethod(Project project, PsiClass psiClass) {
        List<PsiMethod> methods = new LinkedList<>();
        PsiClass clazz = psiClass;
        while (isUserClass(project, clazz)) {
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
                && m.getName().startsWith("set")
                && m.hasParameters();
    }

    public static boolean isGetter(@NotNull PsiMethod m) {
        return m.hasModifierProperty(PsiModifier.PUBLIC)
                && !m.hasModifierProperty(PsiModifier.STATIC)
                && (m.getName().startsWith("get") || m.getName().startsWith("is"))
                && !m.hasParameters();
    }

    public static Map<String, PsiVariable> getBlockVariables(Project project, PsiLocalVariable localVariable) {
        Map<String, PsiVariable> options = new HashMap<>(2);
        final PsiFile file = localVariable.getContainingFile();
        file.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitVariable(PsiVariable variable) {
                super.visitVariable(variable);
                final PsiClass vc = PsiTypesUtil.getPsiClass(variable.getType());
                if (isUserClass(project, vc)) {
                    options.put(variable.getName() + " : " + vc.getQualifiedName(), variable);
                }
            }

            @Override
            public void visitField(PsiField field) {
                super.visitField(field);
                final PsiClass fc = PsiTypesUtil.getPsiClass(field.getType());
                if (isUserClass(project, fc)) {
                    options.put(field.getName() + " : " + fc.getQualifiedName(), field);
                }
            }
        });
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
