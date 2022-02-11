package com.mt.minilauncher;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.JTree;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

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
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTree tree = new JTree();
		contentPane.add(tree, BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JEditorPane htmlPane = new JEditorPane();
		scrollPane.setViewportView(htmlPane);
		htmlPane.setEditable(false);
		htmlPane.setContentType("text/html");
		try {
			htmlPane.setPage(ReferenceApp.class.getResource("/docs/index.html").toURI().toURL());
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		htmlPane.addHyperlinkListener(l -> {
			if(l.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				try {
					htmlPane.setPage(l.getURL());
					System.out.println(l.getURL());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
