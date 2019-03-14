package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import logic.CalculateFrequencyActivity;

@WebServlet("/homepageactivity")
public class HomePageActivity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomePageActivity() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doOperations(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	private void doOperations(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String keyword = request.getParameter("sname");
		String buttonClickEvent = request.getParameter("buttonType");
                
		if (buttonClickEvent.contains("frequency")) {
                        
			CalculateFrequencyActivity frequencyMatcher = new CalculateFrequencyActivity();
			String input = keyword.toString();
                        
                        /*
                        *   The core part of the search engine where
                        *   the function below will fetch the list of files
                        *   contianing the word and push on the JSP
                        **/
                        long startTime = System.nanoTime();
                        request.setAttribute("counter", CalculateFrequencyActivity.ResultCounter);
			request.setAttribute("listOfResult", frequencyMatcher.frequencysearch(input));
                        //System.out.println(frequencyMatcher.frequencysearch(input));  //Uncomment this to view the results if not displaying on thh webpage
                        long endTime = System.nanoTime();
                        double time = (endTime - startTime) / 10e+9;
                        
                        // Formatting the output time to have 2 digits after decimal point
                        java.text.DecimalFormat df = new DecimalFormat("#.##");
                        request.setAttribute("time", df.format(time));
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/frequencypage.jsp");
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException servletException) {
			}
		}
		else if (buttonClickEvent.contains("editdistance")) {
			HashMap<String, Integer> hashMap = new HashMap<>();
			CalculateFrequencyActivity frequencyMatcher = new CalculateFrequencyActivity();
			String input = keyword.toLowerCase();
                        String text = new String(Files.readAllBytes(Paths.get("D:/MAC/Advanced COmputing/Project/code/computing/src/dictionary.txt")));
                        StringTokenizer strk = new StringTokenizer(text, " -,.>/<", false);
                        int i = 0;

                        // Initialising TST
                        Trie<Integer> trie = new Trie<>();
                        while (strk.hasMoreTokens()){
                            trie.put(strk.nextToken(), i);
                            i++;
                        }
                        if (!input.isEmpty()){
                            String str = trie.prefixMatch(input).toString();
                            String []textFound = str.split("\n"); // splitting the words suggestion to store in json

                            for (int j = 0; j < textFound.length;j++){ 
                                int a = frequencyMatcher.editDistance(input, textFound[j]);
                                hashMap.put(textFound[j], a);
                            }
                            Map<Integer, String> map = sortByEditDistance(hashMap); // Sorting the word suggetions based on its edit distance
                            Set set2 = map.entrySet();
                            Iterator iterator2 = set2.iterator();
                            int wordLimit = 0;
                            List<String> sugArr = new ArrayList<>();
                            wordLimit = 0;
                            while (iterator2.hasNext() && wordLimit < 10) { // Displaying only first 10 word suggestions and cresating hash table
                                    Map.Entry me2 = (Map.Entry) iterator2.next(); 
                                    sugArr.add(me2.getKey().toString().replaceAll("[^a-zA-Z0-9\\r]", "")); // formatting the words to only have alphabets
                                    wordLimit++;
                            }

                            Set<String> hs = new HashSet<>(); // Creating sets to remove dulicate entries
                            hs.addAll(sugArr);
                            sugArr.clear();
                            sugArr.addAll(hs);

                            // Initiating JSON file to store the final words list
                            String json = new Gson().toJson(sugArr);
                            // System.out.println(json); // Uncomment this to view the results if not displaying on thh webpage
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write(json);
                        }
		}
	}

	private static HashMap sortByEditDistance(HashMap map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
			}
		});
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}
}
