package com.mt.minilauncher;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class ReferenceApp extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReferenceApp frame = new ReferenceApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReferenceApp() {
		setTitle("Reference");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		try {
			
			JSplitPane splitPane = new JSplitPane();
			splitPane.setResizeWeight(0.5);
			contentPane.add(splitPane, BorderLayout.CENTER);
			
			JScrollPane scrollPane = new JScrollPane();
			splitPane.setRightComponent(scrollPane);
			
			JEditorPane htmlPane = new JEditorPane();
			scrollPane.setViewportView(htmlPane);
			htmlPane.setEditable(false);
			htmlPane.setContentType("text/html");
			htmlPane.setPage(ReferenceApp.class.getResource("/docs/index.html").toURI().toURL());
			
			JScrollPane scrollPane_1 = new JScrollPane();
			splitPane.setLeftComponent(scrollPane_1);
			
			JEditorPane tableOfContentsPane = new JEditorPane();
			tableOfContentsPane.setEditable(false);
			tableOfContentsPane.setContentType("text/html");
			scrollPane_1.setViewportView(tableOfContentsPane);
			htmlPane.addHyperlinkListener(l -> {
				if(l.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						htmlPane.setPage(l.getURL());
						System.out.println("Loading page: " + l.getURL());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			tableOfContentsPane.setPage(ReferenceApp.class.getResource("/docs/contents.html").toURI().toURL());
			tableOfContentsPane.addHyperlinkListener(l -> {
				if(l.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						htmlPane.setPage(l.getURL());//the table of contents should open a link in the main view
						System.out.println("Loading page: " + l.getURL());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
