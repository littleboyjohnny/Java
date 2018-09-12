package ru.skillbench.tasks.text;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = " Other notable Gordons from this time include Bertram de Gordon who wounded King Richard of England with an arrow at  <t> t t  t   t  from   ghost include    include";
		//String str1 = "hello";
		//System.out.println(str1.split("<.+>")[0]);
		
		WordCounterImpl wc = new WordCounterImpl();
		wc.setText(str);
		wc.print(wc.getWordCountsSorted(), System.out);
		
	}

}
