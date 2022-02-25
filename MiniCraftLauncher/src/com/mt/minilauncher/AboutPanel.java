package com.mt.minilauncher;

import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public AboutPanel() {
		
		setLayout(new BorderLayout(0, 0));
		
		JEditorPane htmlPane = new JEditorPane();
		htmlPane.setEditable(false);
		htmlPane.setContentType("text/html");
		htmlPane.setText("<p><b>MiniCraftLauncher</b> by <b>MajickTek</b></p>\n"
				+ "<p>Make sure you have the latest version! automatic updates aren't available yet.</p>\n"
				+ "<p>Older versions might also break because of format changes to the index repo.</p>\n\n"
				+ "<a href=\"https://github.com/MajickTek/MiniCraftLauncher\">Launcher source code/releases</a>\n"
				+ "<br />\n<a href=\"https://github.com/MajickTek/MiniCraftLauncherIndex\">Version index repo and archive</a>\n\n");
		
		
		htmlPane.addHyperlinkListener(l -> {
			if(l.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				if(Desktop.isDesktopSupported()) {
				    try {
						Desktop.getDesktop().browse(l.getURL().toURI());
					} catch (IOException | URISyntaxException e) {
						Debug.callCrashDialog("ERROR", "An error occurred opening a link. Check the console.", Debug.ERR);
						e.printStackTrace();
					}
				} else {
					Debug.callCrashDialog("ERROR", "Sorry, using java.awt.Desktop is not allowed/supported in this environment!", Debug.ERR);
				}
			}
		});
		
		
		add(htmlPane, BorderLayout.CENTER);
		

	}

}
