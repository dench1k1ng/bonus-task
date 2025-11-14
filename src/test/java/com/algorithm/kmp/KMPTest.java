package com.algorithm.kmp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("KMP Algorithm Test Suite")
class KMPTest {

    @Nested
    @DisplayName("Basic Functionality Tests")
    class BasicFunctionalityTests {

        @Test
        @DisplayName("Should find pattern in short string")
        void testShortStringMatch() {
            String text = "ABABDABACDABABCABAB";
            String pattern = "ABABCABAB";

            List<Integer> matches = KMP.search(text, pattern);

            assertFalse(matches.isEmpty(), "Should find at least one match");
            assertEquals(1, matches.size(), "Should find exactly one match");
            assertEquals(10, matches.get(0), "Should find match at position 10");
        }

        @Test
        @DisplayName("Should find multiple occurrences")
        void testMultipleMatches() {
            String text = "AAABAAABAAAB";
            String pattern = "AAAB";

            List<Integer> matches = KMP.search(text, pattern);

            assertEquals(3, matches.size(), "Should find three matches");
            assertIterableEquals(List.of(0, 4, 8), matches,
                    "Should find matches at positions 0, 4, and 8");
        }

        @Test
        @DisplayName("Should find overlapping patterns")
        void testOverlappingPatterns() {
            String text = "AAAA";
            String pattern = "AA";

            List<Integer> matches = KMP.search(text, pattern);

            assertEquals(3, matches.size(), "Should find three overlapping matches");
            assertIterableEquals(List.of(0, 1, 2), matches,
                    "Should find matches at positions 0, 1, and 2");
        }

        @Test
        @DisplayName("Should handle medium length text")
        void testMediumString() {
            String text = "The algorithm is efficient. This algorithm works well. " +
                    "Algorithm design is important for algorithm performance.";
            String pattern = "algorithm";

            List<Integer> matches = KMP.search(text, pattern);

            assertFalse(matches.isEmpty(), "Should find matches");
            assertTrue(matches.size() >= 3, "Should find multiple algorithm occurrences");
        }

        @Test
        @DisplayName("Should handle long text efficiently")
        void testLongString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                sb.append("This is a test string with test patterns. ");
            }
            String text = sb.toString();
            String pattern = "test";

            long startTime = System.nanoTime();
            List<Integer> matches = KMP.search(text, pattern);
            long endTime = System.nanoTime();

            assertFalse(matches.isEmpty(), "Should find matches in long text");
            assertTrue(matches.size() >= 1000, "Should find many test occurrences");

            // Performance check - should complete quickly (under 50ms for this size)
            double timeMs = (endTime - startTime) / 1_000_000.0;
            assertTrue(timeMs < 50, "Should complete within 50ms for text of this size");
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should return empty list for null text")
        void testNullText() {
            List<Integer> matches = KMP.search(null, "pattern");
            assertTrue(matches.isEmpty(), "Should return empty list for null text");
        }

        @Test
        @DisplayName("Should return empty list for null pattern")
        void testNullPattern() {
            List<Integer> matches = KMP.search("text", null);
            assertTrue(matches.isEmpty(), "Should return empty list for null pattern");
        }

        @Test
        @DisplayName("Should return empty list for empty pattern")
        void testEmptyPattern() {
            List<Integer> matches = KMP.search("text", "");
            assertTrue(matches.isEmpty(), "Should return empty list for empty pattern");
        }

        @Test
        @DisplayName("Should return empty list when pattern longer than text")
        void testPatternLongerThanText() {
            List<Integer> matches = KMP.search("short", "very long pattern");
            assertTrue(matches.isEmpty(), "Should return empty list when pattern is longer");
        }

        @Test
        @DisplayName("Should return empty list when pattern not found")
        void testPatternNotFound() {
            List<Integer> matches = KMP.search("hello world", "xyz");
            assertTrue(matches.isEmpty(), "Should return empty list when pattern not found");
        }

        @Test
        @DisplayName("Should find single character pattern")
        void testSingleCharacterPattern() {
            String text = "abcabc";
            String pattern = "a";

            List<Integer> matches = KMP.search(text, pattern);

            assertEquals(2, matches.size(), "Should find two occurrences of 'a'");
            assertIterableEquals(List.of(0, 3), matches, "Should find 'a' at positions 0 and 3");
        }

        @Test
        @DisplayName("Should handle pattern equal to text")
        void testPatternEqualsText() {
            String text = "exact match";
            String pattern = "exact match";

            List<Integer> matches = KMP.search(text, pattern);

            assertEquals(1, matches.size(), "Should find exactly one match");
            assertEquals(0, matches.get(0), "Should find match at position 0");
        }

        @Test
        @DisplayName("Should handle repeated character patterns")
        void testRepeatedCharacters() {
            String text = "aaaaaaaaaa";
            String pattern = "aaa";

            List<Integer> matches = KMP.search(text, pattern);

            assertEquals(8, matches.size(), "Should find 8 overlapping matches");
        }
    }

    @Nested
    @DisplayName("LPS Array Tests")
    class LPSArrayTests {

        @Test
        @DisplayName("Should compute correct LPS array for ABABCABAB")
        void testLPSArrayABABCABAB() {
            String pattern = "ABABCABAB";
            int[] expectedLPS = { 0, 0, 1, 2, 0, 1, 2, 3, 4 };

            int[] actualLPS = KMP.getLPSArray(pattern);

            assertArrayEquals(expectedLPS, actualLPS,
                    "LPS array should match expected values for ABABCABAB");
        }

        @Test
        @DisplayName("Should compute correct LPS array for AAAA")
        void testLPSArrayAAAA() {
            String pattern = "AAAA";
            int[] expectedLPS = { 0, 1, 2, 3 };

            int[] actualLPS = KMP.getLPSArray(pattern);

            assertArrayEquals(expectedLPS, actualLPS,
                    "LPS array should match expected values for AAAA");
        }

        @Test
        @DisplayName("Should compute correct LPS array for ABCDE")
        void testLPSArrayABCDE() {
            String pattern = "ABCDE";
            int[] expectedLPS = { 0, 0, 0, 0, 0 };

            int[] actualLPS = KMP.getLPSArray(pattern);

            assertArrayEquals(expectedLPS, actualLPS,
                    "LPS array should be all zeros for pattern with no repeated prefix-suffix");
        }

        @Test
        @DisplayName("Should handle single character pattern for LPS")
        void testLPSArraySingleChar() {
            String pattern = "A";
            int[] expectedLPS = { 0 };

            int[] actualLPS = KMP.getLPSArray(pattern);

            assertArrayEquals(expectedLPS, actualLPS,
                    "LPS array should be [0] for single character");
        }
    }

    @Nested
    @DisplayName("Performance Tests")
    class PerformanceTests {

        @Test
        @DisplayName("Should demonstrate linear time complexity")
        void testLinearTimeComplexity() {
            String pattern = "test";

            // Test with different text sizes
            int[] textSizes = { 100, 500, 1000, 5000 };
            long[] times = new long[textSizes.length];

            for (int i = 0; i < textSizes.length; i++) {
                String text = generateText(textSizes[i], pattern);

                long startTime = System.nanoTime();
                KMP.search(text, pattern);
                long endTime = System.nanoTime();

                times[i] = endTime - startTime;
            }

            // Verify that time doesn't grow quadratically
            // For linear algorithm, time ratio should be roughly proportional to size ratio
            for (int i = 1; i < times.length; i++) {
                double timeRatio = (double) times[i] / times[i - 1];
                double sizeRatio = (double) textSizes[i] / textSizes[i - 1];

                // Time ratio should not be much larger than size ratio (allowing some variance)
                assertTrue(timeRatio < sizeRatio * 3,
                        "Time complexity should be roughly linear, not quadratic");
            }
        }

        @Test
        @DisplayName("Should handle large text efficiently")
        void testLargeTextPerformance() {
            String text = generateText(50000, "pattern");
            String pattern = "pattern";

            long startTime = System.nanoTime();
            List<Integer> matches = KMP.search(text, pattern);
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;

            assertFalse(matches.isEmpty(), "Should find matches in large text");
            assertTrue(timeMs < 100, "Should complete within 100ms for large text");
        }

        /**
         * Helper method to generate text of specified size with pattern occurrences
         */
        private String generateText(int size, String pattern) {
            StringBuilder sb = new StringBuilder();
            while (sb.length() < size) {
                if (sb.length() % 100 == 0 && sb.length() > 0) {
                    sb.append(pattern).append(" ");
                } else {
                    sb.append("abcdef ");
                }
            }
            return sb.toString();
        }
    }

    @Nested
    @DisplayName("Parametrized Tests")
    class ParametrizedTests {

        @ParameterizedTest(name = "Text: ''{0}'', Pattern: ''{1}'', Expected matches: {2}")
        @DisplayName("Should find correct number of matches for various inputs")
        @CsvSource({
                "'ABABCABABA', 'ABA', '3'",
                "'AAAAAA', 'AA', '5'",
                "'ABCDEFG', 'XYZ', '0'",
                "'hello hello hello', 'hello', '3'",
                "'test', 'test', '1'",
                "'abcabc', 'abc', '2'"
        })
        void testVariousPatterns(String text, String pattern, int expectedMatches) {
            List<Integer> matches = KMP.search(text, pattern);
            assertEquals(expectedMatches, matches.size(),
                    "Should find expected number of matches");
        }
    }

    @Nested
    @DisplayName("Specific Algorithm Tests")
    class AlgorithmSpecificTests {

        @Test
        @DisplayName("Should correctly handle patterns with self-overlap")
        void testSelfOverlappingPattern() {
            String text = "ABABABAB";
            String pattern = "ABAB";

            List<Integer> matches = KMP.search(text, pattern);

            assertEquals(3, matches.size(), "Should find overlapping matches");
            assertIterableEquals(List.of(0, 2, 4), matches,
                    "Should find matches at correct positions");
        }

        @Test
        @DisplayName("Should handle complex repeating patterns")
        void testComplexRepeatingPattern() {
            String text = "AABAACAABAABAACAABA";
            String pattern = "AABAACAABA";

            List<Integer> matches = KMP.search(text, pattern);

            assertEquals(2, matches.size(), "Should find two overlapping matches");
            assertIterableEquals(List.of(0, 9), matches, "Should find matches at positions 0 and 9");
        }

        @Test
        @DisplayName("Should demonstrate KMP efficiency vs naive approach")
        void testKMPEfficiency() {
            // Create a text that would cause many false starts in naive approach
            String text = "AAAAAAAAAB" + "AAAAAAAAAB".repeat(100);
            String pattern = "AAAAAAAAAB";

            long startTime = System.nanoTime();
            List<Integer> matches = KMP.search(text, pattern);
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;

            assertEquals(101, matches.size(), "Should find all pattern occurrences");
            assertTrue(timeMs < 10, "Should complete quickly even with many false starts");
        }
    }

    @Nested
    @DisplayName("Utility Method Tests")
    class UtilityMethodTests {

        @Test
        @DisplayName("Should display LPS array without throwing exceptions")
        void testDisplayLPSMethod() {
            assertDoesNotThrow(() -> {
                KMP.displayLPS("ABABCABAB");
            }, "displayLPS should not throw any exceptions");
        }

        @Test
        @DisplayName("Should display results without throwing exceptions")
        void testDisplayResultsMethod() {
            String text = "test text";
            String pattern = "test";
            List<Integer> matches = List.of(0);
            long timeNano = 1000000L; // 1ms

            assertDoesNotThrow(() -> {
                KMP.displayResults(text, pattern, matches, timeNano);
            }, "displayResults should not throw any exceptions");
        }
    }
}
