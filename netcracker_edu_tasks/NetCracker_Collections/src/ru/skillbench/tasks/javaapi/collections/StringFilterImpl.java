package ru.skillbench.tasks.javaapi.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class StringFilterImpl implements StringFilter {
	
	private HashSet<String> buffer = new HashSet<>();

	@Override
	public void add(String s) {
		if (s == null)
			buffer.add(null);
		else
			buffer.add(s.toLowerCase());
	}

	@Override
	public boolean remove(String s) {
		if (s == null)
			return buffer.remove(null);
		return buffer.remove(s.toLowerCase());
	}

	@Override
	public void removeAll() {
		buffer.clear();
	}

	@Override
	public Collection<String> getCollection() {
		return buffer;
	}
	
	public interface Filter {
		boolean search(String str, String chars);
	}

	private Iterator<String> getStrings(Filter filter, String chars) {
		if (chars == null || chars.length() == 0)
			return buffer.iterator();
		HashSet<String> result = new HashSet<>();
		for (Iterator<String> i = buffer.iterator(); i.hasNext();) {
			String obj = i.next();
			if (obj == null)
				continue;
			if (filter.search(obj, chars))
				result.add(obj);
		}
		return result.iterator();
	}
	
	@Override
	public Iterator<String> getStringsContaining(String chars) {
		return getStrings(new Filter() {
			
			@Override
			public boolean search(String str, String chars) {
				return str.contains(chars);
			}
		}, chars.toLowerCase());
	}

	@Override
	public Iterator<String> getStringsStartingWith(String begin) {
		if (begin == null)
			return buffer.iterator();
		
		return getStrings(new Filter() {
			@Override
			public boolean search(String str, String chars) {
				return str.startsWith(chars);
				
			}
		}, begin.toLowerCase());
	}

	@Override
	public Iterator<String> getStringsByNumberFormat(String format) {
		return getStrings(new Filter() {
			@Override
			public boolean search(String str, String chars) {
				if (str.length() != chars.length())
					return false;
				for (int i = 0; i < chars.length(); i++) {
					if (chars.charAt(i) != '#' && chars.charAt(i) != str.charAt(i)) {
						return false; 
					} else {
						if (chars.charAt(i) == '#' && !Character.isDigit(str.charAt(i))) {
							return false;
						}
					}
				}
				return true;
				
			}
		}, format.toLowerCase());
	}

	@Override
	public Iterator<String> getStringsByPattern(String pattern) {
		return getStrings(new Filter() {
			@Override
			public boolean search(String str, String chars) {
				Integer begin = 0;
				Integer end = -1;
				Integer lastIndex = 0;
				Integer newIndex = 0;
				//ArrayList<String> parts = new ArrayList<>();
				String part = null;
				do {
					begin = end + 1;
					end = chars.indexOf("*", begin);
					if (end == -1 && begin == 0) {
						return str.compareTo(chars) == 0;
					}
					if (end != -1 || end == -1 && begin < chars.length()) {
						if (end == -1) 
							part = chars.substring(begin);
						else 
							part = chars.substring(begin, end);
						if (part.length() == 0) {
							part = null;
							continue;
						}
					}
					if (part != null && (begin == 0 && end != 0 && !str.startsWith(part) || 
							end == -1 && begin != (str.length() - 1) && !str.endsWith(part) ||
							(newIndex = str.indexOf(part, lastIndex)) == -1)) {
						return false;
					}
					if (part != null && newIndex != -1) {
						lastIndex = newIndex + part.length();
					}
						
					part = null;
				} while(end != -1);
				return true;
			}
		}, pattern.toLowerCase());
	}

}
