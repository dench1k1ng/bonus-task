package com.algorithm.kmp;

import java.util.List;

public class TestChecker {
    public static void main(String[] args) {
        // Test the failing cases
        String text1 = "AABAACAABAABAACAABA";
        String pattern1 = "AABAACAABA";
        List<Integer> matches1 = KMP.search(text1, pattern1);
        System.out.println("Test 1 (Expected 1, Got " + matches1.size() + "): " + matches1);
        
        String text2 = "ABABCABABA";
        String pattern2 = "ABA";
        List<Integer> matches2 = KMP.search(text2, pattern2);
        System.out.println("Test 2 (Expected 2, Got " + matches2.size() + "): " + matches2);
    }
}
