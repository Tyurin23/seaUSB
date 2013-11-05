package ru.tyurin.seausb;


import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.tyurin.filecreator.FilesTreeCreator;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SeaTest {

	final Path src = Paths.get("/home/tyurin/aasrc");
	final Path dst = Paths.get("/home/tyurin/aadst");

	@BeforeMethod
	public void setUp() throws Exception {
		FileUtils.cleanDirectory(src.toFile());
		FileUtils.cleanDirectory(dst.toFile());
		FilesTreeCreator creator = new FilesTreeCreator.Builder(src.toFile()).maxFiles(5).maxFileSize(10000000).build();
		creator.createTree();
	}

	@AfterMethod
	public void tearDown() throws Exception {
	}

	@Test
	public void testSEA() throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Sea.createAndShowGUI();
			}
		});
		synchronized (this) {
			this.wait();
		}
	}
}
