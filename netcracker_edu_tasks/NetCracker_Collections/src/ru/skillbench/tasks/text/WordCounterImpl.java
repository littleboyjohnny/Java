package ru.skillbench.tasks.text;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WordCounterImpl implements WordCounter {
	
	private String text = null;
	private Map<String, Long> tree = null;
	
	private void buildTree() {
		tree = new HashMap<String, Long>();
		String[] words = text.split("[\\s\t\n]+");
		for (int i = 0; i < words.length; i++) {
			if (words[i].length() > 0) {
				String word = words[i].toLowerCase();
				if (word.split("<.+>").length == 1) {
					if (tree.get(word) != null) {
						tree.put(word, tree.get(word) + 1);
					} else {
						tree.put(word, (long)1);
					}
				}
			}
		}
	}

	@Override
	public void setText(String text) {
		this.text = text;

	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public Map<String, Long> getWordCounts() {
		if (text == null) {
			throw new IllegalStateException();
		}
		if (tree == null) {
			buildTree();
		}
		return tree;
	}

	@Override
	public List<Entry<String, Long>> getWordCountsSorted() {
		if (text == null) {
			throw new IllegalStateException();
		}
		if (tree == null) {
			buildTree();
		}
		return this.sort(tree, new Comparator<Entry<String, Long>>() {
			
			@Override
			public int compare(Entry<String, Long> a, Entry<String, Long> b) {
				if (a.getValue().equals(b.getValue())) {
					return a.getKey().compareTo(b.getKey());
				} else {
					return -a.getValue().compareTo(b.getValue());
				}
			}
		});
	}

	@Override
	public <K extends Comparable<K>, V extends Comparable<V>> List<Entry<K, V>> sort(
			Map<K, V> map, Comparator<Entry<K, V>> comparator) {
		List<Entry<K, V>> list = new ArrayList(tree.entrySet());
		Collections.sort(list, comparator);
		return list;
	}

	@Override
	public <K, V> void print(List<Entry<K, V>> entryList, PrintStream ps) {
		for (Iterator<Entry<K, V>> i = entryList.iterator(); i.hasNext();) {
			Entry<K, V> obj = i.next();
			ps.println(obj.getKey() + " " + obj.getValue());
		}
	}

}
