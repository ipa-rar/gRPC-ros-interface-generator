/*****************************************************************************
 * Copyright (c) 2021 CEA LIST.
 * 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 *****************************************************************************/

package org.acumos.gen.ros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class FileWriter {

	String baseFolder;

	/**
	 * Create a new file write starting at the passed base folder
	 * Constructor.
	 *
	 * @param baseFolder
	 */
	public FileWriter(String baseFolder) {
		this.baseFolder = baseFolder;
	}

	/**
	 * @param fileName
	 *            The filename
	 * @param content
	 *            The content that is written to a file
	 */
	public void generateFile(String fileName, CharSequence content) {
		File file = getFile(fileName);
		try {
			if (!file.exists()) {
				// the file does not exists
				file.createNewFile();
			}
			BufferedWriter writer = Files.newBufferedWriter(file.toPath(), Charset.defaultCharset());
			writer.write(content.toString());
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(String.format("FileWriter: cannot write to file: %s, error message: %s", //$NON-NLS-1$
					fileName, e.getMessage()));
		}
	}

	/**
	 * Return a container (folder) for a given filename.
	 * Folders will be created, if the do not exist (comparable to "mkdir -p" in Unix).
	 *
	 * @param filename
	 *            a filename with '/' (IPFileSystemAccess.SEP_CHAR) as separation character
	 * @return file for this element
	 */
	public File getFile(String filename) {
		String paths[] = filename.split("/"); //$NON-NLS-1$
		File folder = new File(baseFolder);
		if (!folder.exists()) {
			folder.mkdir();
		}
		for (int i = 0; i < paths.length - 1; i++) {
			String path = paths[i];
			folder = getFile(folder, path);
			if (!folder.exists()) {
				// if packageContainer is a Project, it necessarily exists
				folder.mkdir();
			}
		}
		String last = paths[paths.length - 1];
		return getFile(folder, last);
	}

	/**
	 * Get a file within a folder
	 * 
	 * @param folder
	 *            A folder (instance of class File)
	 * @param fileName
	 *            A file name
	 * @return the file within the folder (instance of class File)
	 */
	public File getFile(File folder, String fileName) {
		return new File(folder, fileName);
	}
}
