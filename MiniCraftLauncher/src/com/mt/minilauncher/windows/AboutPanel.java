package com.mt.minilauncher.windows;

import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;

import org.eclipse.jface.dialogs.MessageDialog;

import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.util.Util;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;

public class AboutPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
				+ String.format("<p><b>Current Version:</b> %s</p>%n", LauncherWindow.version.toString())
				+ "<p>do not bother downgrading, older versions may not work properly. Open an issue on GitHub instead.</p>\n\n"
				+ "<a href=\"https://github.com/MajickTek/MiniCraftLauncher\">Launcher source code/releases</a>\n"
				+ "<br />\n<a href=\"https://github.com/MajickTek/MiniCraftLauncherIndex\">Version index repo and archive</a>\n\n");
		
		
		htmlPane.addHyperlinkListener(l -> {
			if(l.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				if(Desktop.isDesktopSupported()) {
				    try {
						Desktop.getDesktop().browse(l.getURL().toURI());
					} catch (IOException | URISyntaxException e) {
						e.printStackTrace();
						Util.openErrorDialog(LauncherWindow.instance.getShell(), "An error occurred opening a link.", e);
					}
				} else {
					try {
						Util.throwError("Unsupported action");
					} catch (Exception e) {
						e.printStackTrace();
						Util.openErrorDialog(LauncherWindow.instance.getShell(), "Sorry, using java.awt.Desktop is not allowed/supported in this environment!", null);
					}
					
				}
			}
		});
		
		
		add(htmlPane, BorderLayout.CENTER);
		

	}

}
