package me.jcomo.stilltasty.core;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Static methods to humanize text. Shamelessly ripped (and slightly modified) from:
 * https://github.com/mfornos/humanize/blob/master/humanize-slim/src/main/java/humanize/Humanize.java
 *
 * The library is large and not worth having the entire dependency for a couple straightforward utility functions.
 */
public class Humanize {

    private static final String SPACE = " ";

    private static final Pattern TITLE_WORD_SEPARATOR = Pattern.compile(".+(\\||-|/).+");
    private static final List<String> TITLE_IGNORED_WORDS = Arrays.asList(
            "a", "an", "and", "but", "nor", "it", "the", "to", "with", "in", "on", "of",
            "up", "or", "at", "into", "onto", "by", "from", "then", "for", "via", "versus");

    public static String titleize(String text) {
        String str = text.toLowerCase(Locale.ENGLISH).replaceAll("[\\s_]+", SPACE).trim();
        return titleize(str, SPACE);
    }

    private static String titleize(String str, String separator) {
        StringBuilder sb = new StringBuilder(str.length());
        String[] parts = str.split(separator);
        Matcher m;

        for (int i = 0; i < parts.length; i++) {
            String word = parts[i];
            boolean notLastWord = i < parts.length - 1;

            if (i > 0 && notLastWord && TITLE_IGNORED_WORDS.contains(word)) {
                sb.append(word);
            } else if ((m = TITLE_WORD_SEPARATOR.matcher(word)).find()) {
                String newSeparator = m.group(1);
                sb.append(titleize(word, newSeparator));

                while (m.find()) {
                    sb.append(titleize(word, newSeparator));
                }
            } else {
                sb.append(capitalize(word));
            }

            if (notLastWord) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    public static String capitalize(String text) {
        String str = text.trim();
        int len = str.length();

        if (len == 0) {
            return text;
        }

        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            if (Character.isLetter(str.charAt(i))) {
                Locale locale = Locale.ENGLISH;
                int lc = i + 1;

                if (i > 0) {
                    sb.append(str.substring(0, i));
                }

                sb.append(str.substring(i, lc).toUpperCase(locale));
                sb.append(str.substring(lc).toLowerCase(locale));

                break;
            }
        }

        return sb.length() == 0 ? str : sb.toString();
    }
}
