package ru.tyurin.seausb.util;


import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class USBTool {

	private static final String LINUX_MOUNT_PATH = "/media";

	public static Collection<USBDrive> usbList() {
		if (SystemUtils.IS_OS_LINUX) {
			return usbListLinux();
		} else if (SystemUtils.IS_OS_WINDOWS) {
			throw new UnsupportedOperationException("Not supported yet!");
		}
		return null;
	}

	public static USBDrive find(String name) {
		for (USBDrive drive : usbList()) {
			if (drive.getName().equals(name)) {
				return drive;
			}
		}
		return null;
	}

	private static Collection<USBDrive> usbListLinux() {
		List<USBDrive> drives = new ArrayList<>();
		Path mountPath = Paths.get(LINUX_MOUNT_PATH);
		if (Files.exists(mountPath) && Files.isDirectory(mountPath)) {
			for (File drive : mountPath.toFile().listFiles()) {
				USBDrive usb = new USBDrive(drive.toPath());
				usb.setName(drive.getName());
				drives.add(usb);
			}
		}
		return drives;
	}
}
