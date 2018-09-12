package ru.skillbench.tasks.text.regex;

public class Checker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Patterns p = new PatternsImpl();
		String str = "7alary_-";
		String mail = "lexkoz.yan@mail.ag-a.s7.ru";
		String tag = "ijo3igoiro igo <A HREF = httphuemoe \\>";
		//System.out.println(p.getSQLIdentifierPattern().matches(str));
		//System.out.println(mail.matches(p.getEmailPattern().pattern()));
		System.out.println(p.findAll(tag, p.getHrefTagPattern()));

	}

}
