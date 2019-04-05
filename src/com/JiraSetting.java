package com;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author zn
 */
@State(
		name = "JiraSettings",
		storages = @Storage("jira-plugin.xml")
)
public class JiraSetting implements PersistentStateComponent<JiraSettingState> {
	public static JiraSetting getInstance() {
		return ServiceManager.getService(JiraSetting.class);
	}
	private JiraSettingState myState = new JiraSettingState();

	@Nullable
	@Override
	public JiraSettingState getState() {
		return myState;
	}

	@Override
	public void loadState(@NotNull JiraSettingState state) {
		myState = state;
	}


}
