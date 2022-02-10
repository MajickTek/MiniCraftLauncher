package com.mt.minilauncher.downloader;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;

public class DownloadDialog extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DownloadDialog dialog = new DownloadDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public DownloadDialog() {
		setTitle("Downloading");
		setBounds(100, 100, 450, 100);
		
		JProgressBar progressBar = new JProgressBar();
		getContentPane().add(progressBar, BorderLayout.CENTER);

	}

}
