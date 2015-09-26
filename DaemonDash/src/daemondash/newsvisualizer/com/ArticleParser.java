package daemondash.newsvisualizer.com;

import java.util.ArrayList;
import java.util.Collections;

public class ArticleParser {
	public static final int NUM_POP_WORDS = 15;
	private static final String[] commonWords = new String[] { "FOR", "THE", "SAID", "WHILE", "FROM", "AND", "WAS",
			"WHERE", "WHAT", "WHEN", "WHERE", "WHY", "HOW", "THAT", "THEN", "THERE", "WHO", "ALL", "WERE", "INTO",
			"HAD" };
	private ArrayList<ArrayList<Tuple<String>>> listOfWordCountsByArticle = new ArrayList<ArrayList<Tuple<String>>>();
	private ArrayList<Tuple<String>> listOfWordCountsTotal = new ArrayList<Tuple<String>>();
	private ArrayList<String> mostPopWords = new ArrayList<String>();
	private ArrayList<Tuple<String>> mostPopTuples = new ArrayList<Tuple<String>>();

	public ArticleParser(ArrayList<String> articles) {
		for (String s : articles) {
			String[] strArr = s.split("\\s+");
			ArrayList<Tuple<String>> stringCounts = new ArrayList<Tuple<String>>();
			for (String str : strArr) {
				boolean found = false;
				for (Tuple<String> tuple : stringCounts) {
					if (tuple.getKey().equals(str)) {
						tuple.incValue();
						found = true;
					}
				}
				if (!found && notCommonWord(str)) {
					stringCounts.add(new Tuple<String>(str, 1));
				}
			}
			listOfWordCountsByArticle.add(stringCounts);
		}
		for (ArrayList<Tuple<String>> wordList : listOfWordCountsByArticle) {
			for (Tuple<String> word : wordList) {
				boolean found = false;
				for (Tuple<String> tuple : listOfWordCountsTotal) {
					if (tuple.getKey().equals(word.getKey())) {
						tuple.setValue(word.getValue());
						;
						found = true;
					}
				}
				if (!found) {
					listOfWordCountsTotal.add(new Tuple<String>(word.getKey(), word.getValue()));
				}
			}
		}
		Collections.sort(listOfWordCountsTotal);
		for (int index = 0; index < NUM_POP_WORDS && index < listOfWordCountsTotal.size(); index++) {
			mostPopWords.add(listOfWordCountsTotal.get(index).getKey());
			mostPopTuples.add(listOfWordCountsTotal.get(index));
		}
	}

	private boolean notCommonWord(String s) {
		s = s.toUpperCase();
		if (s.length() > 2) {
			for (String word : commonWords) {
				if (word.equals(s)) {
					return false;
				}
			}
		}else{
			return false;
		}
		return true;
	}

	public ArrayList<String> getMostPopWords() {
		return mostPopWords;
	}

	public ArrayList<Tuple<String>> getMostPopTuples() {
		return mostPopTuples;
	}
}
