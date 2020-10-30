package com.piean.idea.plugin.coding.config;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/9/28
 */
@State(
        name = "com.piean.idea.plugin.coding.config.SettingsConfigState",
        storages = {@Storage(StoragePathMacros.PRODUCT_WORKSPACE_FILE)}
)
public class ProjectSettingsState implements PersistentStateComponent<ProjectSettingsState.ConfigState> {

    public ProjectSettingsState() {
        this.configState = new ConfigState();
    }

    public static ProjectSettingsState getInstance(Project project) {
        return ServiceManager.getService(project, ProjectSettingsState.class);
    }

    private final ConfigState configState;


    @Override
    public @NotNull ConfigState getState() {
        return configState;
    }

    @Override
    public void loadState(@NotNull ConfigState state) {
        XmlSerializerUtil.copyBean(state, this.configState);
    }

    public String getUserPackages() {
        String packages = configState.userPackages;
        if (packages == null) {
            packages = "";
        }
        return packages;
    }

    static class ConfigState {
        private String userPackages;

        private int enableFlagBit;

        public String getUserPackages() {
            return userPackages;
        }

        public void setUserPackages(String userPackages) {
            this.userPackages = userPackages;
        }

        public int getEnableFlagBit() {
            return enableFlagBit;
        }

        public void setEnableFlagBit(int enableFlagBit) {
            this.enableFlagBit = enableFlagBit;
        }
    }
}
