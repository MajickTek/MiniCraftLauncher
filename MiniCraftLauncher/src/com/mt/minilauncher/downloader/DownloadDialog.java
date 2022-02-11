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
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

public class DownloadDialog extends JDialog implements FileDownloaderDelegate{
	private JLabel progress;
	private JLabel kbs;
	private JLabel eta;
	private JLabel bytesWritten;

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
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		eta = new JLabel("Estimated Time: 0");
		getContentPane().add(eta);
		
		progress = new JLabel("Progress: 0");
		getContentPane().add(progress);
		
		kbs = new JLabel("KB/s: 0");
		getContentPane().add(kbs);
		
		bytesWritten = new JLabel("Bytes Written: 0");
		getContentPane().add(bytesWritten);
		
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
		eta.setText("Estimated Time: " + fileDownloader.getTotalTimeToDownload());
		progress.setText("Progress:" + fileDownloader.getPercentComplete());
		kbs.setText("KB/s: " + fileDownloader.getKbPerSecond());
	}

	@Override
	public void didFinishDownload(FileDownloader fileDownloader) {
		eta.setText("Estimated Time: 0");
		progress.setText("Done");
		kbs.setText("KB/s: 0");
		bytesWritten.setText("Bytes Written: " + fileDownloader.getBytesWritten());
	}

	@Override
	public void didFailDownload(FileDownloader fileDownloader) {
		progress.setText("Oops!");
		Debug.callCrashDialog("ERROR", "An error ocurred downloading the file. Please check the logs.", Debug.ERR);
	}

	public JLabel getProgress() {
		return progress;
	}
	public JLabel getKbs() {
		return kbs;
	}
	public JLabel getEta() {
		return eta;
	}
	public JLabel getBytesWritten() {
		return bytesWritten;
	}
}
