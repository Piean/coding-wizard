package com.piean.idea.plugin.coding.tool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/9/16
 */
public class Jaccard {
    private static final int K = 3;

    public static double similarity(final String s1, final String s2) {
        if (s1 == null || s2 == null) {
            return 0;
        }
        if (s1.equals(s2)) {
            return 1;
        }

        Map<String, Integer> profile1 = getProfile(s1);
        Map<String, Integer> profile2 = getProfile(s2);

        Set<String> union = new HashSet<>();
        union.addAll(profile1.keySet());
        union.addAll(profile2.keySet());

        int inter = profile1.keySet().size() + profile2.keySet().size() - union.size();

        return 1.0 * inter / union.size();
    }

    private static Map<String, Integer> getProfile(final String string) {
        int length = string.length() - K + 1;
        Map<String, Integer> shingles = new HashMap<>();
        for (int i = 0; i < length; i++) {
            String shingle = string.substring(i, i + K);
            shingles.merge(shingle, 1, Integer::sum);
        }
        return shingles;
    }
}
