package com;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.domain.Attachment;
import com.atlassian.jira.rest.client.domain.BasicProject;
import com.atlassian.jira.rest.client.domain.Comment;
import com.atlassian.jira.rest.client.domain.Component;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import org.apache.commons.collections.IteratorUtils;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zn
 * jira issue and comment all pictures in attachment
 */
public class JiraUtils {

//	public static void main(String[] args) {
//		try {
//			JiraRestClient client = getJiraClient("", "", "");
////			getAllProjects(client);
//			InputStream stream = client.getIssueClient().getAttachment(new URI("")).get();
//
//			Issue issue = getIssueByKey(client, "GLD-973");
//
////			System.out.println(issue);
////			Promise<SearchResult> searchResultPromise = client.getSearchClient().searchJql("screenshot-1.png");
////			System.out.println(getIssueComments(issue));
//			System.out.println(getIssuePhotos(issue));
//
//		}catch (Exception e) {
//			System.out.println("jira error");
//		}
//	}


	public static List<BasicProject> getAllProjects(JiraRestClient client) throws Exception {
		Promise<Iterable<BasicProject>> list = client.getProjectClient().getAllProjects();
		return IteratorUtils.toList(list.get().iterator());
	}

	public static Issue getIssueByKey(JiraRestClient client, String issueKey) {
		Promise<Issue> issuePromise = null;
		try {
			issuePromise = client.getIssueClient().getIssue(issueKey);
			return issuePromise.get();
		}catch (Exception e) {
			System.out.println("getIssueByKeyError");
		}
		return null;
	}

	public static Map<String, String> getIssuePhotos (Issue issue) {
		Iterator<Attachment> issueAttachments = issue.getAttachments().iterator();
		Map<String, String> photos = new HashMap<>();
		while (issueAttachments.hasNext()) {
			Attachment attachment= issueAttachments.next();
//			System.out.println(attachment.getFilename());
			photos.put(attachment.getFilename(), attachment.getContentUri().toString());
		}
		return photos;
	}

	public static List<Comment> getIssueComments (Issue issue) {
		//comment.getbody.getauthor
		return IteratorUtils.toList(issue.getComments().iterator());
	}

	public static JiraRestClient getJiraClient(String host, String user, String password) {
		JiraRestClient client = null;
		try {
			AsynchronousJiraRestClientFactory jiraRestClientFactory = new AsynchronousJiraRestClientFactory();
			client = jiraRestClientFactory.createWithBasicHttpAuthentication(new URI(host), user,password);
		} catch (URISyntaxException e) {
			System.out.println("hostError");
		}
		return client;
	}

	public static List<String> getAllProjectsKey(JiraRestClient client) {
		try {
			return getAllProjects(client).stream().map(project -> project.getKey()).collect(Collectors.toList());
		}catch (Exception e) {
			System.out.println("getAllProjectsError");
		}

		return null;
	}

	public static String issueCommentsString(Issue issue) {
		StringBuffer sb = new StringBuffer();
		List<Comment> comments = getIssueComments(issue);
		for (Comment comment : comments) {
			sb.append("----" + comment.getAuthor().getDisplayName() + ":\n")
					.append(comment.getBody() + "\n");
		}
		return sb.toString();
	}
}
