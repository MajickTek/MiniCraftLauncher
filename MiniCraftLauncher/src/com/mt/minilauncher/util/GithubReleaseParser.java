package com.mt.minilauncher.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import io.quicktype.*;

public class GithubReleaseParser {

	
	public static GithubAPI[] parseReleases(String user, String repo, int page) throws IOException {
		String json = getJSON(user, repo, page);
		GithubAPI[] data = Converter.fromJsonString(json);
		return data;
	}
	
	private static String getJSON(String user, String repo, int pageNumber) {
		String url = String.format("https://api.github.com/repos/%s/%s/releases?per_page=100&page=%d", user, repo, pageNumber);
		StringBuilder jsonBuilder = new StringBuilder();

		try {
			InputStream response = new URL(url).openStream();
			byte[] bytes = response.readAllBytes();
			for (int i = 0; i < bytes.length; i++) {
				jsonBuilder.append((char) bytes[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonBuilder.toString();
	}
	
}
