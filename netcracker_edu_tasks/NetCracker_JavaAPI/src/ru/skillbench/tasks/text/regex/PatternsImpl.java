package ru.skillbench.tasks.text.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternsImpl implements Patterns {

	@Override
	public Pattern getSQLIdentifierPattern() {
		return Pattern.compile("^[a-zA-Z]{1}\\w{0,29}$");
	}

	@Override
	public Pattern getEmailPattern() {
		return Pattern.compile("^([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9_\\.\\-]{0,20}[a-zA-Z0-9])" + "@" + "(([a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)+" + "((ru)|(com)|(net)|(org))$");
	}

	@Override
	public Pattern getHrefTagPattern() {
		return Pattern.compile("<[\\s]*[aA][\\s](?i)(href)[\\s]*=[\\s]*" + "(\".*\"|[\\S]*)" + "[\\s]*[\\\\]?>");
	}

	@Override
	public List<String> findAll(String input, Pattern pattern) {
		ArrayList<String> result = new ArrayList<>();
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			result.add(matcher.group(0));
		}
		return result;
	}

	@Override
	public int countMatches(String input, String regex) {
		ArrayList<String> result = new ArrayList<>();
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			result.add(matcher.group(0));
		}
		return result.size();
	}

}
