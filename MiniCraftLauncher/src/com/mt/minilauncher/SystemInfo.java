package com.mt.minilauncher;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;

public class SystemInfo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextArea textArea_1;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SystemInfo dialog = new SystemInfo();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SystemInfo() {
		setTitle("System Info");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JSplitPane splitPane = new JSplitPane();
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane.setResizeWeight(0.5);
			contentPanel.add(splitPane, BorderLayout.CENTER);
			{
				JScrollPane scrollPane = new JScrollPane();
				splitPane.setLeftComponent(scrollPane);
				{
					textArea_1 = new JTextArea();
					textArea_1.setForeground(Color.GREEN);
					textArea_1.setBackground(Color.BLACK);
					textArea_1.setEditable(false);
					scrollPane.setViewportView(textArea_1);
				}
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				splitPane.setRightComponent(scrollPane);
				{
					textArea = new JTextArea();
					textArea.setForeground(Color.GREEN);
					textArea.setBackground(Color.BLACK);
					textArea.setEditable(false);
					scrollPane.setViewportView(textArea);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
		}
		initTexts();
	}
	private void initTexts() {
		StringBuilder propertiesBuilder = new StringBuilder();
		propertiesBuilder.append("====System Properties====\n");
		System.getProperties().forEach((k, v) -> {
			propertiesBuilder.append(String.format("%s = %s", k.toString(), v.toString()) + "\n");
		});
		textArea_1.setText(propertiesBuilder.toString());
		
		StringBuilder envBuilder = new StringBuilder();
		envBuilder.append("====System Environment====\n");
		System.getenv().forEach((k, v) -> {
			envBuilder.append(String.format("%s = %s", k.toString(), v.toString()) + "\n");
		});
		textArea.setText(envBuilder.toString());
	}
	public JTextArea getTextArea1() {
		return textArea_1;
	}
	public JTextArea getTextArea() {
		return textArea;
	}
}
