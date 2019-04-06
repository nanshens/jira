package com;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.domain.Issue;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.apache.xmlgraphics.image.loader.util.ImageUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ShowIssue implements ToolWindowFactory {
	private JTextField issueNumber;
	private JComboBox projectNumber;
	private JPanel selectIssue;
	private JScrollPane issueContent;
	private JPanel showIssue;
	private JButton ok;
	private JTextPane issue;
	private JButton showPicture;
	private static JiraRestClient client;
	private Map<String, String> pictures = new HashMap<>();

	public static void reLogin() {
		JiraSettingState setting = JiraSetting.getInstance().getState();
		client = JiraUtils.getJiraClient(setting.getHost(), setting.getUser(), setting.getPassword());
	}
	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		Content content = contentFactory.createContent(showIssue,"", false);
		reLogin();
		initProjectNumber();
		initListener();
		addStyle();
		toolWindow.getContentManager().addContent(content);
	}

	private void initProjectNumber() {
		projectNumber.addItem("GLD");
		projectNumber.addItem("TAM");
		projectNumber.addItem("FE");
		projectNumber.addItem("MIM");
		projectNumber.addItem("AER");
		projectNumber.addItem("TNT");
	}

	private void initListener() {
		ActionListener loginListener = e -> {
			issue.setText("");
			String issueNumberText = issueNumber.getText();
			String userText = projectNumber.getSelectedItem().toString();
			Issue issueAll = JiraUtils.getIssueByKey(client, userText + "-" + issueNumberText);
			if (issueAll == null) {
				issueAddString("没有此Issue", true);
			}else {
				issueAddString("Title:\n", true);
				issueAddString(issueAll.getSummary(), false);
				issueAddString("\nDescription:\n", true);
				issueAddString(issueAll.getDescription(), false);
				issueAddString("\nComments:\n", true);
				issueAddString(JiraUtils.issueCommentsString(issueAll), false);
				pictures = JiraUtils.getIssuePhotos(issueAll);
			}
		};

		ActionListener pictureListener = e -> {
			ShowPicture showPicture = new ShowPicture(pictures, client);
			showPicture.showPictureFrame();
		};

		showPicture.addActionListener(pictureListener);
		ok.addActionListener(loginListener);
	}

	private void addStyle() {
		Style def = issue.getStyledDocument().addStyle(null, null);
		StyleConstants.setFontFamily(def, "family");
		StyleConstants.setFontSize(def, 14);
		StyleConstants.setForeground(def, Color.lightGray);
		Style normal = issue.addStyle("normal", def);
		Style s = issue.addStyle("bold", normal);
		StyleConstants.setForeground(s, Color.ORANGE);
		StyleConstants.setBold(s, true);
		StyleConstants.setFontSize(s, 16);
		issue.setParagraphAttributes(normal, true);

	}

	private void issueAddString(String content, boolean bold) {
		String b = bold ? "bold" : "normal";
		try {
			issue.getDocument().insertString(issue.getDocument().getLength(), content, issue.getStyle(b));
		}catch (Exception e) {
			System.out.println("issuecontentError");
		}
	}
}
