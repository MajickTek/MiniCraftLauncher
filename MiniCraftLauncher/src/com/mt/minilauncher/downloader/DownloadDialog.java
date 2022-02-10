package com.mt.minilauncher.downloader;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

import com.littlebigberry.httpfiledownloader.FileDownloader;
import com.littlebigberry.httpfiledownloader.FileDownloaderDelegate;
import com.mt.minilauncher.Debug;
import com.mt.minilauncher.Initializer;

import java.awt.BorderLayout;
import javax.swing.JLabel;

public class DownloadDialog extends JDialog implements FileDownloaderDelegate{
	private JLabel progress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
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

		FileDownloader fileDownloader = new FileDownloader(this);
		
		progress = new JLabel("0");
		getContentPane().add(progress, BorderLayout.CENTER);
		
		fileDownloader.setUrl("https://github.com/MinicraftPlus/minicraft-plus-revived/releases/download/v2.0.7/minicraft_plus-2.0.7.jar");
		fileDownloader.setLocalLocation(Initializer.jarPath.toString() + "/2.0.7.jar");
		fileDownloader.beginDownload();
	}

	@Override
	public void didStartDownload(FileDownloader fileDownloader) {
		System.out.println("Begin");
	}

	@Override
	public void didProgressDownload(FileDownloader fileDownloader) {
		progress.setText(fileDownloader.getPercentComplete());
	}

	@Override
	public void didFinishDownload(FileDownloader fileDownloader) {
		progress.setText("Done");
	}

	@Override
	public void didFailDownload(FileDownloader fileDownloader) {
		progress.setText("Oops!");
		Debug.callCrashDialog("ERROR", "An error ocurred downloading the file. Please check the logs.", Debug.ERR);
	}

	public JLabel getProgress() {
		return progress;
	}
}
