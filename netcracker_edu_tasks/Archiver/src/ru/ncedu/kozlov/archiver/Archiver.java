package ru.ncedu.kozlov.archiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.*;


/**
 *  This class is used for packing files to zip file.
 *  All methods throw IOException.
 * 
 * @author Alexey Kozlov
 */
public class Archiver {
	
	private static void addFile(ZipOutputStream zos, File file) throws IOException {
		ZipEntry ze = new ZipEntry(file.getPath());
		zos.putNextEntry(ze);
		
		Path fileLocation = file.toPath();
		byte[] data = Files.readAllBytes(fileLocation);
		zos.write(data, 0, data.length);
		
		zos.closeEntry();
	}
	
	private static void recursiveZip(ZipOutputStream zos, File directoryName) throws IOException {
		File[] files = directoryName.listFiles();
		for (File file: files) {
			if (file.isFile()) {
				addFile(zos, file);
			}
			else if (file.isDirectory()) {
				recursiveZip(zos, file);
			}
		}
	}
	
	/**
	 * Packs files or directories to zip file.
	 * @param archiveName - name of archive
	 * @param fileNames - files or directories to be packed
	 * @param comment - comment to be added to zip file
	 * @throws IOException
	 */
	public static void zipFiles(String archiveName, String[] fileNames, String comment) throws IOException {
		File zipFile = new File(archiveName);
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
		
		for (int i = 0; i < fileNames.length; i++) {
			File file = new File(fileNames[i]);
			if (file.isFile()) {
				addFile(zos, file);
			} else if (file.isDirectory()) {
				recursiveZip(zos, file);
			}
		}
		zos.setComment(comment);
		zos.close();
	}
	
	/**
	 * Unpacks an archive to specified directory. If the directory doesn't exist, it will be created.
	 * @param archiveName - name of archive
	 * @param path - path to directory for unpacking 
	 * @throws IOException
	 */
	public static void unzipFiles(String archiveName, String path) throws IOException {
		ZipFile zipFile = new ZipFile(archiveName);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            if (!entry.isDirectory()) {
            	InputStream stream = zipFile.getInputStream(entry);
            	File file = new File(path + entry.getName());
            	
            	File dir = new File(file.getParent());
            	dir.mkdirs();
            	
            	Files.copy(stream, file.toPath());
            	stream.close();
            } 
        }
        zipFile.close();
	}
	
	/**
	 * Adds files or directories to existing archive
	 * @param archiveName - name of archive
	 * @param fileNames - files or directories to be added to archive
	 * @throws IOException
	 */
	public static void addFileToArchive(String archiveName, String[] fileNames) throws IOException {
		ZipFile oldZip = new ZipFile(archiveName);
		Enumeration<? extends ZipEntry> entries = oldZip.entries();
		
		File newZip = new File("tempzip.zip");
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(newZip));
		
		while(entries.hasMoreElements()){
			ZipEntry entry = entries.nextElement();
			zos.putNextEntry(entry);
			
			InputStream stream = oldZip.getInputStream(entry);
			byte[] buffer = new byte[1024];
			int nread;
			
			while((nread = stream.read(buffer, 0, buffer.length)) != -1) {
				zos.write(buffer, 0, nread);
			}
			
		}
		
		oldZip.close();
		
		for (int i = 0; i < fileNames.length; i++) {
			File file = new File(fileNames[i]);
			if (file.isFile()) {
				addFile(zos, file);
			} else if (file.isDirectory()) {
				recursiveZip(zos, file);
			}
		}
		zos.close();
		
		Files.delete(new File(archiveName).toPath());
		newZip.renameTo(new File(archiveName));
	}
	
	/**
	 * Reads archive's comment
	 * @param archiveName - name of archive
	 * @return - archive's comment
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static String readComment(String archiveName) throws IOException {
		return new ZipFile(archiveName).getComment();
	}
}
