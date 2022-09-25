package com.mt.minilauncher.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.downloader.Downloader;
import com.mt.minilauncher.objects.ChannelObject;
import com.mt.minilauncher.objects.VersionObject;

import io.quicktype.Asset;
import io.quicktype.GithubAPI;

public class Util {

	/**
	 * Downloads a file from a URL using a java.nio stream
	 * 
	 * @param urlStr the url from which to download
	 * @param file   the local filename and path after downloading
	 * @throws IOException if the file cannot be written
	 */
	public static void downloadUsingNIO(String urlStr, String file) throws IOException {
		URL url = new URL(urlStr);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
	}

	/**
	 * Method to download a jar file using com.mt.minilauncher.Downloader using
	 * information from a VersionObject
	 * 
	 * @param vo     the object from which the url and filename are gathered
	 * @param window the window instance used as a context (so the progressbar and
	 *               tree can be refreshed)
	 */
	public static void downloadJar(VersionObject vo, LauncherWindow window) {
		String path = Paths.get(Initializer.jarPath.toString(), vo.version + ".jar").toString();
		System.out.println(String.format("Downloading: [URL:%s, path: %s]", vo.getURL(), path));

		Downloader downloader = new Downloader();
		downloader.setUrl(vo.url);
		downloader.setLocalLocation(path);

		downloader.setDownloadProgressCallback((f) -> {
			// TODO
//			window.getProgressBarLabel().setText("Downloading: " + f.getPercentComplete());

			Display.getDefault().asyncExec(() -> {
				window.getProgressBar()
				.setSelection((int) Double.parseDouble(f.getPercentComplete().replace('%', ' ').strip()));
			});
		});
		
		downloader.setDownloadFinishedCallback((f) -> {

			Display.getDefault().asyncExec(() -> {
				MessageDialog.openInformation(window.getShell(), "Launcher",
						"Ready to Play! [" + vo.version + "]");
				
				vo.isDownloaded = true;
				window.getProgressBar().setSelection(0);
			});
		});
		downloader.download();
	}

	/**
	 * Recursively deletes (empties) a given directory
	 * 
	 * @param dir a file or directory to delete
	 */
	public static void purgeDirectory(File dir) {
		for (File file : dir.listFiles()) {
			if (file.isDirectory())
				purgeDirectory(file);
			file.delete();
		}
	}

	/**
	 * Same as purgeDirectory() but preserves the subdirectories
	 * 
	 * @param dir
	 */
	public static void purgeDirectoryButKeepSubDirectories(File dir) {
		for (File file : dir.listFiles()) {
			if (!file.isDirectory())
				file.delete();
		}
	}

	/**
	 * Lists all of the class resources starting from the root relative to this Util
	 * class.
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static void listResources() throws URISyntaxException, IOException {
		URL url = Util.class.getResource("/");
		Path path = Paths.get(url.toURI());
		Files.walk(path, 5).forEach(p -> System.out.printf("- %s%n", p.toString()));
	}

	/**
	 * Opens a file through the operating system. Depending on implementation, this
	 * may also open a URL in the default browser. <br />
	 * One usage example is to open a folder using Windows Explorer
	 * 
	 * @param f the file/directory/url to open
	 * @throws IOException file not found
	 */
	public static void openNative(File f) throws IOException {
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(f);
		}
	}

	/**
	 * like openNative(), but is specifically meant to open the system's web
	 * browser.
	 * 
	 * @param url A web URL such as "https://www.google.com". A filepath can not be
	 *            entered here.
	 * @throws MalformedURLException Incorrect url format
	 * @throws IOException           Java cannot find/launch the system's default
	 *                               browser
	 * @throws URISyntaxException    String cannot be parsed as a URL
	 */
	public static void browseNative(String url) throws MalformedURLException, IOException, URISyntaxException {
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new URL(url).toURI());
		}
	}

	/**
	 * Builds an ArrayList of available channels from the internet
	 * 
	 * @return ArrayList of ChannelObjects populated with XML data
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static ArrayList<ChannelObject> buildChannelList()
			throws ParserConfigurationException, SAXException, IOException {
		ArrayList<ChannelObject> tempList = new ArrayList<>();

		ChannelObject liveObject = new ChannelObject();
		liveObject.setLive(true);
		liveObject.setChannelName("MinicraftPlus");
		liveObject.setLiveUsername("MinicraftPlus");
		liveObject.setLiveRepoName("minicraft-plus-revived");
		tempList.add(liveObject);

		Path indexPath = Paths.get(Initializer.indexPath.toString(), "index.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(indexPath.toFile());

		doc.getDocumentElement().normalize();

		NodeList indexList = doc.getElementsByTagName("index");
		for (int i = 0; i < indexList.getLength(); i++) {
			Node indexNode = indexList.item(i);
			if (indexNode.getNodeType() == Node.ELEMENT_NODE) {
				Element indexElement = (Element) indexNode;
				String indexName = indexElement.getAttribute("name");
				NodeList urlList = indexElement.getElementsByTagName("url");
				Node urlNode = urlList.item(0);
				if (urlNode.getNodeType() == Node.ELEMENT_NODE) {
					Element urlElement = (Element) urlNode;
					String target = urlElement.getAttribute("target");
					String url = (urlElement.getTextContent().startsWith("http://")
							|| urlElement.getTextContent().startsWith("https://")) ? urlElement.getTextContent() : "";
					ChannelObject co = new ChannelObject();
					co.channelFile = url;
					co.channelName = indexName;
					co.target = target;
					tempList.add(co);
				}
			}
		}
		return tempList;
	}

	/**
	 * Using the url from a ChannelObject, a Tree is populated with either GitHub or XML data
	 * @param channelObject
	 * @throws IOException
	 */
	public static void populateTree(ChannelObject channelObject) throws IOException {
		Image base = SWTResourceManager.getImage(LauncherWindow.instance.getClass(), "/minicraftplus.png");
		Image resized = resize(base, 16, 16);
		
		if (channelObject.isLive()) {
			TreeItem releasesRoot = new TreeItem(LauncherWindow.instance.getTree(), SWT.NONE);
			releasesRoot.setText("Releases");
			releasesRoot.setImage(resized);
			
			TreeItem preReleasesRoot = new TreeItem(LauncherWindow.instance.getTree(), SWT.NONE);
			preReleasesRoot.setText("Pre-Releases");
			preReleasesRoot.setImage(resized);
			
			GithubAPI[] releaseTree = GithubReleaseParser.parseReleases(channelObject.liveUsername,
					channelObject.liveRepoName, 1);
			ArrayList<GithubAPI> releases = new ArrayList<>(Arrays.asList(releaseTree));

			
			releases.stream().filter(r -> (r.getPrerelease() == false)).forEach(release -> {
				VersionObject vo = new VersionObject();
				vo.canEdit = false;
				vo.description = release.getBody();
				vo.version = release.getTagName();

				ArrayList<Asset> assets = new ArrayList<>(Arrays.asList(release.getAssets()));
				assets.stream().filter(a -> (a.getName().contains(".jar"))).forEach(asset -> {
					vo.url = asset.getBrowserDownloadURL();
				});
				assets.stream().filter(a -> (a.getName().equalsIgnoreCase("changelog.txt"))).forEach(asset -> {
					vo.changelogURL = asset.getBrowserDownloadURL();
				});

				TreeItem tempItem = new TreeItem(releasesRoot, SWT.NONE);
				tempItem.setText(vo.toString());

				tempItem.setData("VersionObject", vo);
				tempItem.setImage(resized);
			});

			releases.stream().filter(r -> (r.getPrerelease() == true)).forEach(release -> {
				VersionObject vo = new VersionObject();
				vo.canEdit = false;
				vo.description = release.getBody();
				vo.version = release.getTagName();

				ArrayList<Asset> assets = new ArrayList<>(Arrays.asList(release.getAssets()));
				assets.stream().filter(a -> (a.getName().contains(".jar"))).forEach(asset -> {
					vo.url = asset.getBrowserDownloadURL();
				});
				assets.stream().filter(a -> (a.getName().equalsIgnoreCase("changelog.txt"))).forEach(asset -> {
					vo.changelogURL = asset.getBrowserDownloadURL();
				});

				TreeItem tempItem = new TreeItem(preReleasesRoot, SWT.NONE);
				tempItem.setText(vo.toString());

				tempItem.setData("VersionObject", vo);
				tempItem.setImage(resized);
			});

		} else {
			Path filePath = Paths.get(Initializer.indexPath.toString(), channelObject.target);
			Util.downloadUsingNIO(channelObject.channelFile, filePath.toString());
			try {
				XMLConverter.fromXML(filePath.toString(), LauncherWindow.instance.getTree());
			} catch (ParserConfigurationException | SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		LauncherWindow.instance.getTree().update();
		LauncherWindow.instance.getTree().redraw();
	}

	/**
	 * Resizes an Image using the SWT graphics processing class
	 * @param image the image to resize
	 * @param width the new width
	 * @param height the new height
	 * @return a resized version of the inputed image
	 */
	public static Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
	}
	
	/**
	 * Creates a crash/error dialog showing the full stack trace in a text area
	 * @param parent the parent window to which the dialog is attached
	 * @param msg the human-readable message error
	 * @param t the error from which the stack trace is thrown (traditionally, a variable called `e`)
	 * @return the status code of the error dialog
	 */
	public static int openErrorDialog(Shell parent, String msg, Throwable t) {
		MultiStatus ms = createMultiStatus(msg, t);
		return ErrorDialog.openError(parent, t.getClass().getName(), msg, ms);
	}
	
	private static MultiStatus createMultiStatus(String msg, Throwable t) {
		List<Status> childStatuses = new ArrayList<>();
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

        for (StackTraceElement stackTrace: stackTraces) {
            Status status = new Status(IStatus.ERROR,
                    "com.vogella.tasks.ui", stackTrace.toString());
            childStatuses.add(status);
        }

        MultiStatus ms = new MultiStatus("com.mt.minilauncher.util",
                IStatus.ERROR, childStatuses.toArray(new Status[] {}),
                t.toString(), t);
        return ms;
		
	}
	
	public static void throwError(String msg) throws Exception {
		throw new Exception(msg);
	}
}