package com.mt.minilauncher.windows;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChangelogViewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea description;
	private JTextArea changelog;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;

	

	/**
	 * Create the frame.
	 */
	public ChangelogViewer() {
		setTitle("Changelog");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel descriptionPanel = new JPanel();
		tabbedPane.addTab("Description", null, descriptionPanel, null);
		descriptionPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPane1 = new JScrollPane();
		descriptionPanel.add(scrollPane1, BorderLayout.CENTER);
		
		description = new JTextArea();
		description.setEditable(false);
		scrollPane1.setViewportView(description);
		
		JPanel changelogPanel = new JPanel();
		tabbedPane.addTab("Changelog", null, changelogPanel, null);
		changelogPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPane2 = new JScrollPane();
		changelogPanel.add(scrollPane2, BorderLayout.CENTER);
		
		changelog = new JTextArea();
		changelog.setEditable(false);
		scrollPane2.setViewportView(changelog);
	}

	public JTextArea getDescription() {
		return description;
	}
	public JTextArea getChangelog() {
		return changelog;
	}
	
	public void scrollTop() {
		description.setCaretPosition(0);
		changelog.setCaretPosition(0);
	}
	public JScrollPane getScrollPane1() {
		return scrollPane1;
	}
	public JScrollPane getScrollPane2() {
		return scrollPane2;
	}
}
