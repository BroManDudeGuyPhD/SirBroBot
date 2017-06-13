package bromandudeguyphd.sirbrobot;

import java.util.Map;

/**
 * Aligns a map
 * <br>
 * Created by Arsen on 26.8.2016.
 */
public class Aligner {

    /**
     * Aligns some text
     *
     * @param separator The separator
     * @param toAlign   The map to align
     * @return Aligned string
     */
    public static String align(Map<String, String> toAlign, String separator) {
        StringBuilder aligned = new StringBuilder();

        int longestLength = 0;
        for (String key : toAlign.keySet()) {
            if (key.length() > longestLength)
                longestLength = key.length();
        }
        longestLength += 1;
        for (Map.Entry<String, String> e : toAlign.entrySet()) {
            String key = e.getKey();
            aligned.append(key);
            int spaces = longestLength - key.length();
            for (int i = 0; i < spaces; i++)
                aligned.append(' ');
            aligned.append(separator).append(' ');
            aligned.append(e.getValue());
            aligned.append('\n');
        }

        return aligned.toString().trim();
    }
}
