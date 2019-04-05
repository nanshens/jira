package com;
import com.atlassian.httpclient.apache.httpcomponents.DefaultHttpClient;
import com.atlassian.httpclient.apache.httpcomponents.DefaultRequest;
import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.httpclient.api.Request;
import com.atlassian.httpclient.api.ResponsePromises;
import com.atlassian.jira.rest.client.AuthenticationHandler;
import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import org.apache.http.HttpResponse;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import static com.JiraUtils.getJiraClient;


public class SwingTestImg{

	public static void main(String[] args){
		JFrame frame = new JFrame("Test Image Panel");
		JLabel lbl = new JLabel();
		BufferedImage image = null;
		try {
			JiraRestClient client = getJiraClient("", "", "");
//			getAllProjects(client);
			InputStream stream = client.getIssueClient().getAttachment(new URI("")).get();

			image = ImageIO.read(stream);
			System.out.println("image is:"+image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Container c= frame.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(lbl);lbl.setIcon(new ImageIcon(image));
		frame.setBounds(200,100,300,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}