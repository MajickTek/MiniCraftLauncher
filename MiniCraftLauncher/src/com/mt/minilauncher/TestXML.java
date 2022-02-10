package com.mt.minilauncher;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestXML {
	public static void main(String[] args) throws URISyntaxException, IOException {
		URL url = TestXML.class.getResource("/");
	    Path path = Paths.get(url.toURI());
	    Files.walk(path, 5).forEach(p -> System.out.printf("- %s%n", p.toString()));
	}
	
}
