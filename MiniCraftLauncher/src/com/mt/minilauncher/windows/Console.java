package com.mt.minilauncher.windows;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.util.TextAreaOutputStream;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Console extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrintStream con;
	
	private JPanel contentPane;
	private JTextArea textArea;
	private JToolBar toolBar;
	private JButton btnNewButton;

	/**
	 * Create the frame.
	 */
	public Console() {
		setTitle("Console");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LauncherWindow.class.getResource("/minicraftplus.png")));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setForeground(Color.GREEN);
		textArea.setBackground(Color.BLACK);
		scrollPane.setViewportView(textArea);
		con = new PrintStream(new TextAreaOutputStream(textArea, 10000));
		
		toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.SOUTH);
		
		btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		toolBar.add(btnNewButton);
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public PrintStream getCon() {
		return con;
	}

	public void setCon(PrintStream con) {
		this.con = con;
	}
	
	
}
