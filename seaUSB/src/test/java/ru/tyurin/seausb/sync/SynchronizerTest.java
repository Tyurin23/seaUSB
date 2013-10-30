package ru.tyurin.seausb.sync;


import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.tyurin.filecreator.FilesTreeCreator;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.testng.Assert.assertTrue;

public class SynchronizerTest {

	final Path src = Paths.get("/home/tyurin/tmp/drive");
	final Path dst = Paths.get("/home/tyurin/tmp/sea");

	@BeforeMethod
	public void setUp() throws Exception {
		FileUtils.cleanDirectory(src.toFile());
		FileUtils.cleanDirectory(dst.toFile());


	}

	@AfterMethod
	public void tearDown() throws Exception {


	}

	@Test
	public void testSync() throws Exception {
		FilesTreeCreator creator = new FilesTreeCreator.Builder(src.toFile()).maxFiles(5).maxFileSize(100000000).build();
		creator.createTree();
		Task task = new Task(1L);


		Synchronizer synchronizer = new Synchronizer();
		boolean result = synchronizer.synchronize(task);
		assertTrue(result);

	}
}
