package com;

import com.atlassian.jira.rest.client.JiraRestClient;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zn
 */
public class ShowPicture{
	private JPanel showPicture;
	private JButton up;
	private JButton down;
	private JLabel PicNum;
	private JPanel PicBtn;
	private JScrollPane Pic;
	private JLabel PicLabel;
	private Map<String, String> pictures;
	private int picIndex = 1;
	private JiraRestClient client;
	private List<String> pics;

	public void showPictureFrame() {
		JFrame frame = new JFrame("ShowPicture");
		frame.setContentPane(new ShowPicture(pictures, client).showPicture);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setBounds(200,100, 1500, 900);
		frame.setVisible(true);
	}

	public ShowPicture(Map<String, String> pictures, JiraRestClient client) {
		this.pictures = pictures;
		this.client = client;
		pics = new ArrayList<>();
		pictures.forEach((k,v) -> pics.add(v));
		initPicture();
		initListener();
		PicNum.setText("<html>" + (picIndex) + "<br>/<br>" + pictures.size() + "</html>");
	}

	private void initListener() {
		ActionListener upListener = e -> {
			picIndex--;
			initPicture();
		};

		ActionListener downListener = e -> {
			picIndex++;
			initPicture();
		};

		up.addActionListener(upListener);
		down.addActionListener(downListener);
	}

	private void initPicture() {
		if (picIndex < 1) {
			picIndex = 1;
		} else if (picIndex > pics.size()) {
			picIndex = pics.size();
		} else {
			BufferedImage image = null;
			try {
				InputStream stream = client.getIssueClient().getAttachment(new URI(getPicUrl(picIndex - 1))).get();
				image = ImageIO.read(stream);
				PicLabel.setIcon(new ImageIcon(image));
			} catch (Exception e) {
				PicLabel.setIcon(new ImageIcon());
				PicLabel.setText("<html><h1>这个附件不是图片</h1></html>");
			}
		}
		PicNum.setText("<html>" + (picIndex) + "<br>/<br>" + pictures.size() + "</html>");
	}

	private String getPicUrl(int index) {
		return pics.get(index);
	}

}
