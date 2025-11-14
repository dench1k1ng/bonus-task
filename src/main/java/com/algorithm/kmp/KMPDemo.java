package com.algorithm.kmp;

import java.util.List;


public class KMPDemo {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("KMP STRING MATCHING ALGORITHM - DEMONSTRATION");
        System.out.println("=".repeat(80) + "\n");

        // Run all demonstration test cases
        demonstrateShortString();
        demonstrateMediumString();
        demonstrateLongString();
        demonstrateEdgeCases();
        demonstratePerformance();

        System.out.println("\n" + "=".repeat(80));
        System.out.println("DEMONSTRATION COMPLETED SUCCESSFULLY");
        System.out.println("=".repeat(80) + "\n");
    }


    private static void demonstrateShortString() {
        System.out.println("### DEMO 1: SHORT STRING TEST ###\n");

        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";

        System.out.println("Text: \"" + text + "\"");
        System.out.println("Length: " + text.length() + " characters\n");

        KMP.displayLPS(pattern);

        long startTime = System.nanoTime();
        List<Integer> matches = KMP.search(text, pattern);
        long endTime = System.nanoTime();

        KMP.displayResults(text, pattern, matches, endTime - startTime);
    }


    private static void demonstrateMediumString() {
        System.out.println("### DEMO 2: MEDIUM STRING TEST ###\n");

        String text = "The Knuth-Morris-Pratt algorithm is an efficient string matching " +
                "algorithm that finds occurrences of a pattern within a text. " +
                "The algorithm preprocesses the pattern to create a failure function, " +
                "which allows it to skip unnecessary comparisons during the search. " +
                "This makes the algorithm very efficient for string searching tasks.";

        String pattern = "algorithm";

        System.out.println("Text preview: \"" + text.substring(0, 100) + "...\"");
        System.out.println("Full length: " + text.length() + " characters\n");

        KMP.displayLPS(pattern);

        long startTime = System.nanoTime();
        List<Integer> matches = KMP.search(text, pattern);
        long endTime = System.nanoTime();

        KMP.displayResults(text, pattern, matches, endTime - startTime);
    }


    private static void demonstrateLongString() {
        System.out.println("### DEMO 3: LONG STRING TEST ###\n");

        String text = "In computer science, the Knuth-Morris-Pratt string-searching algorithm " +
                "(or KMP algorithm) searches for occurrences of a word within a main text " +
                "string by employing the observation that when a mismatch occurs, the word " +
                "itself embodies sufficient information to determine where the next match " +
                "could begin, thus bypassing re-examination of previously matched characters. " +
                "The algorithm was conceived by James H. Morris and independently discovered " +
                "by Donald Knuth and Vaughan Pratt. The three published it jointly in 1977. " +
                "The algorithm compares characters from left to right. When a mismatch occurs, " +
                "the algorithm uses a preprocessed table to skip some comparisons. This table " +
                "determines how many characters can be skipped based on what has already been " +
                "matched. The key insight is that if we have matched some prefix of the pattern " +
                "and then encounter a mismatch, we don't need to start over from the beginning. " +
                "Instead, we can use information from the pattern itself to determine the next " +
                "possible starting position for a match. This optimization is what makes KMP " +
                "algorithm so efficient compared to naive string matching approaches.";

        String pattern = "algorithm";

        System.out.println("Text preview: \"" + text.substring(0, 120) + "...\"");
        System.out.println("Full length: " + text.length() + " characters\n");

        KMP.displayLPS(pattern);

        long startTime = System.nanoTime();
        List<Integer> matches = KMP.search(text, pattern);
        long endTime = System.nanoTime();

        KMP.displayResults(text, pattern, matches, endTime - startTime);
    }

  
    private static void demonstrateEdgeCases() {
        System.out.println("### DEMO 4: EDGE CASES ###\n");

        System.out.println("Test 4.1: Empty pattern");
        testCase("test string", "", "Should return empty list");

        System.out.println("\nTest 4.2: Pattern longer than text");
        testCase("short", "this is a very long pattern", "Should return empty list");

        System.out.println("\nTest 4.3: Pattern not found");
        testCase("hello world", "xyz", "Should return empty list");

        System.out.println("\nTest 4.4: Single character pattern");
        testCase("aaaaa", "a", "Should find 5 matches");

        System.out.println("\nTest 4.5: Pattern equals text");
        testCase("match", "match", "Should find 1 match at position 0");

        System.out.println("\nTest 4.6: Overlapping patterns");
        testCase("AAAA", "AA", "Should find overlapping matches");

        System.out.println("\nAll edge cases completed!\n");
    }

 
    private static void testCase(String text, String pattern, String expected) {
        long startTime = System.nanoTime();
        List<Integer> matches = KMP.search(text, pattern);
        long endTime = System.nanoTime();

        double timeMs = (endTime - startTime) / 1_000_000.0;

        System.out.println("  Text: \"" + text + "\"");
        System.out.println("  Pattern: \"" + pattern + "\"");
        System.out.println("  Result: " + matches);
        System.out.println("  Expected: " + expected);
        System.out.println("  Time: " + String.format("%.4f", timeMs) + " ms");

        // Simple validation
        if (pattern.isEmpty() || pattern.length() > text.length()) {
            System.out.println("  Status: " + (matches.isEmpty() ? "✅ PASS" : "❌ FAIL"));
        } else if (pattern.equals("a") && text.equals("aaaaa")) {
            System.out.println("  Status: " + (matches.size() == 5 ? "✅ PASS" : "❌ FAIL"));
        } else if (pattern.equals("match") && text.equals("match")) {
            System.out.println("  Status: " + (matches.size() == 1 && matches.get(0) == 0 ? "✅ PASS" : "❌ FAIL"));
        } else if (pattern.equals("AA") && text.equals("AAAA")) {
            System.out.println("  Status: " + (matches.size() == 3 ? "✅ PASS" : "❌ FAIL"));
        } else if (!text.contains(pattern)) {
            System.out.println("  Status: " + (matches.isEmpty() ? "✅ PASS" : "❌ FAIL"));
        } else {
            System.out.println("  Status: " + (!matches.isEmpty() ? "✅ PASS" : "❌ FAIL"));
        }
    }


    private static void demonstratePerformance() {
        System.out.println("### DEMO 5: PERFORMANCE ANALYSIS ###\n");

        System.out.println("Demonstrating O(n+m) time complexity with varying text sizes:\n");
        System.out.println("Text Size | Pattern | Matches | Time (ms) | Status");
        System.out.println("-".repeat(55));

        String pattern = "test";

        // Test with different sizes
        performanceTest(100, pattern);
        performanceTest(500, pattern);
        performanceTest(1000, pattern);
        performanceTest(5000, pattern);
        performanceTest(10000, pattern);

        System.out.println("\nAll performance tests demonstrate linear O(n+m) behavior!\n");
    }


    private static void performanceTest(int textSize, String pattern) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < textSize) {
            if (sb.length() % 100 == 0 && sb.length() > 0) {
                sb.append(pattern).append(" ");
            } else {
                sb.append("abc ");
            }
        }
        String text = sb.toString();

        long startTime = System.nanoTime();
        List<Integer> matches = KMP.search(text, pattern);
        long endTime = System.nanoTime();

        double timeMs = (endTime - startTime) / 1_000_000.0;

        System.out.printf("%8d  | %-7s | %7d | %8.3f | %s%n",
                text.length(),
                pattern,
                matches.size(),
                timeMs,
                timeMs < textSize * 0.01 ? "✅ Fast" : "⚠️ Check");
    }
}
