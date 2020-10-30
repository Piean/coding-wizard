package com.piean.idea.plugin.coding.config;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.piean.idea.plugin.coding.dialog.SettingsComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/9/28
 */
public class ProjectSettingsConfigurable implements SearchableConfigurable {
    private final Project project;
    private SettingsComponent settingsComponent;

    public ProjectSettingsConfigurable(Project project) {
        this.project = project;
        this.settingsComponent = new SettingsComponent();
//        this.initSettings(state);
    }

    private void initSettings(ProjectSettingsState.ConfigState state) {
        settingsComponent.setUserLevelPackage(state.getUserPackages());
//        settingsComponent.setEnableFunction(EnableFunctionBit.ALL_SETTER.isEnable(state.getEnableFlagBit()));
    }

    @Override
    public @NotNull String getId() {
        return "Coding Wizard";
    }

    @Override
    @Nls(capitalization = Nls.Capitalization.Title)
    public String getDisplayName() {
        return "Coding Wizard";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return settingsComponent.getMainPanel();
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return settingsComponent.getPreferredFocusedComponent();
    }

    @Override
    public boolean isModified() {
        ProjectSettingsState.ConfigState state = ProjectSettingsState.getInstance(project).getState();
        boolean isPackagesChanged = !Objects.equals(state.getUserPackages(), settingsComponent.getUserLevelPackage());
//        boolean isEnableChange = !Objects.equals(EnableFunctionBit.ALL_SETTER.isEnable(state.getEnableFlagBit()), settingsComponent.isEnableAllSetter());
        return isPackagesChanged;
    }

    @Override
    public void apply() {
        ProjectSettingsState.ConfigState state = ProjectSettingsState.getInstance(project).getState();
        state.setUserPackages(settingsComponent.getUserLevelPackage());
//        int flagBit = state.getEnableFlagBit();
//        boolean enable = settingsComponent.isEnableAllSetter();
//        if (enable) {
//            flagBit = flagBit | EnableFunctionBit.ALL_SETTER.getFlag();
//        } else {
//            flagBit = flagBit & ~EnableFunctionBit.ALL_SETTER.getFlag();
//        }
//        state.setEnableFlagBit(flagBit);
    }

    @Override
    public void reset() {
        ProjectSettingsState.ConfigState state = ProjectSettingsState.getInstance(project).getState();

        settingsComponent.setUserLevelPackage(state.getUserPackages());
//        settingsComponent.setEnableFunction(false);
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
