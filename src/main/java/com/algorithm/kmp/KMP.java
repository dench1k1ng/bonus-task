package com.algorithm.kmp;

import java.util.ArrayList;
import java.util.List;


public class KMP {

    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();

        // Handle edge cases
        if (text == null || pattern == null || pattern.isEmpty() ||
                pattern.length() > text.length()) {
            return matches;
        }

        int n = text.length();
        int m = pattern.length();

        // Build the LPS array
        int[] lps = computeLPSArray(pattern);

        // Search for pattern in text
        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            // If we've matched the entire pattern
            if (j == m) {
                matches.add(i - j); // Found match at index (i - j)
                j = lps[j - 1]; // Continue searching for next match
            }
            // Mismatch after j matches
            else if (i < n && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    // Use LPS array to skip unnecessary comparisons
                    j = lps[j - 1];
                } else {
                    // No matches yet, just move to next character in text
                    i++;
                }
            }
        }

        return matches;
    }

    private static int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];

        int len = 0; // length of the previous longest prefix suffix
        int i = 1;
        lps[0] = 0; 

        // Build the LPS array
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                // Characters match: extend the current prefix-suffix
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    // Try shorter prefix-suffix (backtrack using previous LPS value)
                    len = lps[len - 1];
                } else {
                    // No prefix-suffix possible
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }


    public static void displayLPS(String pattern) {
        int[] lps = computeLPSArray(pattern);
        System.out.println("Pattern: " + pattern);
        System.out.print("LPS Array: [");
        for (int i = 0; i < lps.length; i++) {
            System.out.print(lps[i]);
            if (i < lps.length - 1)
                System.out.print(", ");
        }
        System.out.println("]\n");
    }

    public static void displayResults(String text, String pattern,
            List<Integer> matches, long timeNano) {
        System.out.println("=".repeat(80));
        System.out.println("TEXT LENGTH: " + text.length() + " characters");
        System.out.println("PATTERN: \"" + pattern + "\" (length: " + pattern.length() + ")");
        System.out.println("-".repeat(80));

        if (matches.isEmpty()) {
            System.out.println("No matches found.");
        } else {
            System.out.println("Found " + matches.size() + " match(es) at position(s): " + matches);
            System.out.println("\nContext snippets:");
            for (int index : matches) {
                int start = Math.max(0, index - 20);
                int end = Math.min(text.length(), index + pattern.length() + 20);
                String snippet = text.substring(start, end);

                // Replace newlines for cleaner display
                snippet = snippet.replace("\n", " ");
                System.out.println("  [" + index + "]: ..." + snippet + "...");
            }
        }

        double timeMs = timeNano / 1_000_000.0;
        System.out.println("\nExecution time: " + String.format("%.4f", timeMs) + " ms");
        System.out.println("=".repeat(80) + "\n");
    }

    public static int[] getLPSArray(String pattern) {
        return computeLPSArray(pattern);
    }
}
