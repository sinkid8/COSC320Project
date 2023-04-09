package P1;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class LCSScompare {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// Prompt user for filename
		// System.out.print("Enter filename: ");
		// String fileName = scanner.nextLine();
		String fileName = "test.txt";
		String curDir = System.getProperty("user.dir") + "/";
		fileName = curDir + fileName;

		// Call sentence separator method
		String[] sentenceArray = separateSentences(fileName);
		while (sentenceArray == null) {
			System.out.print("Enter filename: ");
			fileName = scanner.nextLine();
			sentenceArray = separateSentences(fileName);
		}

		// Print out resulting array
		System.out.println("Sentences in file: ");
		for (String sentence : sentenceArray) {
			System.out.println(sentence);
		}

		scanner.close();

		ArrayList<String> fileNames = new ArrayList<String>();
		fileNames.add(curDir + "file1.txt");
		fileNames.add(curDir + "file2.txt");
		fileNames.add(curDir + "file3.txt");

		ArrayList<String[]> sentencesList = separateSentencesInFiles(fileNames);
		for (int i = 0; i < sentencesList.size(); i++) {
			String[] currentSentences = sentencesList.get(i);
			for (String firstSentence : sentenceArray) {
				for (String currentSentence : currentSentences) {
					System.out.println("Comparing '" + firstSentence + "' and '" + currentSentence + "'");
					if (findSubstringLCSS(currentSentence, firstSentence)) {
						System.out.println(firstSentence + " found in " + currentSentence);
					}
				}
			}
		}
	}

	public static ArrayList<String[]> separateSentencesInFiles(ArrayList<String> fileNames) {
		ArrayList<String[]> sentencesList = new ArrayList<String[]>();
		for (String fileName : fileNames) {
			try {
				Scanner fileScanner = new Scanner(new File(fileName));
				fileScanner.useDelimiter("\\.\\|\\.\\s+");
				ArrayList<String> sentences = new ArrayList<String>();

				// Read sentences from file
				while (fileScanner.hasNext()) {
					String sentence = fileScanner.next();
					sentences.add(sentence);
				}

				fileScanner.close();

				// Convert ArrayList to array and add to sentencesList
				String[] sentenceArray = sentences.toArray(new String[sentences.size()]);
				sentencesList.add(sentenceArray);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				sentencesList.add(null);
			}
		}
		return sentencesList;
	}

	public static String[] separateSentences(String fileName) {
		try {
			Scanner fileScanner = new Scanner(new File(fileName));
			fileScanner.useDelimiter("\\.\\s+");
			ArrayList<String> sentences = new ArrayList<String>();

			// Read sentences from file
			while (fileScanner.hasNext()) {
				String sentence = fileScanner.next();
				sentences.add(sentence);
			}

			fileScanner.close();

			// Convert ArrayList to array and return
			String[] sentenceArray = sentences.toArray(new String[sentences.size()]);
			return sentenceArray;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	public static boolean findSubstringLCSS(String text, String pattern) {
        int m = text.length();
        int n = pattern.length();
    
        int[][] dp = new int[m+1][n+1];
    
        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text.charAt(i-1) == pattern.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                }
            }
        }
    
        // Check if LCS length is equal to pattern length
        return dp[m][n] == n;
    }
    

	public static int[] computeLSPTable(String pattern) {
		int[] lsp = new int[pattern.length()];
		lsp[0] = 0; // Base case

		int j = 0; // Length of the previous longest prefix suffix
		for (int i = 1; i < pattern.length(); i++) {
			while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
				// Fall back in the pattern
				j = lsp[j - 1];
			}

			if (pattern.charAt(i) == pattern.charAt(j)) {
				// Next character matched, increment length of longest prefix suffix
				j++;
			}

			lsp[i] = j;
		}

		return lsp;
	}
}

    

