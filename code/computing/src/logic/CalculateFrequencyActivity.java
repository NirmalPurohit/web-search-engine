package logic;

/**
 *  This package reads the keyword form the input panel and
 *  tries to find it in the text files.
 *  once it finds the word in files it sorts them and displays the
 *  result.
 */


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

public class CalculateFrequencyActivity {

    
        public static int ResultCounter = 0;
	// TODO code
	public Map frequencysearch(String cname) throws IOException {
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		int counter = 0;
		ArrayList<String> arrayList = new ArrayList<String>();
                String filePath = "D:/MAC/Advanced COmputing/Project/code/computing/src/textfile/";
		File directory = new File(filePath);
		File[] files = directory.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				arrayList.add(filePath + files[i].getName());
			}
		}
                ResultCounter = 0;
		for (int i = 0; i < arrayList.size(); i++) {
			org.jsoup.nodes.Document document = Jsoup.parse(new File(arrayList.get(i)), "UTF-8");
			String text = document.text();
			String[] wordsToFind = text.split(" ");
                        
                        //Sorting the words using QuickSelect
			Arrays.sort(wordsToFind);
                        
			for (int j = 0; j < wordsToFind.length; j++) {
				if (wordsToFind[j].equals(cname)) {
					counter++;
				}
			}
                        if (counter > 0)
                            ResultCounter++;
                        // Pushing to a hashMap which works as dynamic inverted Index
			hashMap.put(files[i].getName(), counter);
			wordsToFind = null;
			counter = 0;
		}
		Map<Integer, String> map = sortForINsertion(hashMap);
		return map;
	}
	
	private static HashMap sortForINsertion(HashMap map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
			}
		});
		Collections.reverse(list);
		HashMap sortedHashMap = new LinkedHashMap(); // Preserving the insertion order
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

	public int editDistance(String str1, String str2) {
		int str1len = str1.length();
		int str2len = str2.length();
		int[][] distance = new int[str1len + 1][str2len + 1];

		for (int i = 0; i <= str1len; i++) {
			distance[i][0] = i;
		}

		for (int j = 0; j <= str2len; j++) {
			distance[0][j] = j;
		}
		for (int i = 0; i < str1len; i++) {
			char c1 = str1.charAt(i);
			for (int j = 0; j < str2len; j++) {
				char c2 = str2.charAt(j);
				if (c1 == c2) {
					distance[i + 1][j + 1] = distance[i][j];
				} else {
					int replace = distance[i][j] + 1;
					int insert = distance[i][j + 1] + 1;
					int delete = distance[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					distance[i + 1][j + 1] = min;
				}
			}
		}
		return distance[str1len][str2len];
	}

}
