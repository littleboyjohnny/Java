package ru.skillbench.tasks.javaapi.io;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.stream.Stream;

public class WordFinderImpl implements WordFinder {
	
	private String text = null;
	private String[] targetWords = null;

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		if (text == null)
			throw new IllegalArgumentException();
		this.text = text;
	}

	@Override
	public void setInputStream(InputStream is) throws IOException {
		if (is == null)
			throw new IllegalArgumentException();
		ByteArrayOutputStream result = new ByteArrayOutputStream(); 
		byte[] buffer = new byte[1024];
		int length;
		while((length = is.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		this.text = result.toString("UTF-8");
	}

	@Override
	public void setFilePath(String filePath) throws IOException {
		if (filePath == null)
			throw new IllegalArgumentException();
		FileInputStream fis = new FileInputStream(filePath);
		setInputStream(fis);
	}

	@Override
	public void setResource(String resourceName) throws IOException {
		if (resourceName == null)
			throw new IllegalArgumentException();
		setInputStream(WordFinderImpl.class.getResourceAsStream(resourceName));
	}

	@Override
	public Stream<String> findWordsStartWith(String begin) {
		if (text == null)
			throw new IllegalStateException();
		String[] words = text.split("[\\s]+");
		for (int i = 0; i < words.length; i++)
			words[i] = words[i].toLowerCase();
		if (begin != null && begin != "") {
			String beginLower = begin.toLowerCase();
			words = Arrays.asList(words).stream().filter(w->w.startsWith(beginLower)).toArray(String[]::new);
		}
		targetWords = words;
		return Arrays.asList(targetWords).stream();
	}

	@Override
	public void writeWords(OutputStream os) throws IOException {
		if (targetWords == null)
			throw new IllegalStateException();
		Arrays.asList(targetWords).stream().sorted().forEach(w-> 
		{try {
			os.write((w+" ").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}});
	}
}