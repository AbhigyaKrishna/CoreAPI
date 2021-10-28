package me.Abhigya.core.util;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/** Implements {@link org.apache.commons.lang.StringUtils} */
public class StringUtils extends org.apache.commons.lang.StringUtils {

    public static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * Converts integer to an HTML RGB value
     *
     * <p>
     *
     * @param rgb RGB (0 ~ 0xFFFFFF)
     * @return Hex (#FFFFFF)
     */
    public static String toRgbText(int rgb) {
        if (rgb > 16777215) rgb = 16777215;

        if (rgb < 0) rgb = 0;

        String str = "000000" + Integer.toHexString(rgb);

        return "#" + str.substring(str.length() - 6);
    }

    /**
     * Limits a string to provided number of maximum characters
     *
     * <p>
     *
     * @param string String to limit
     * @param max_length Max length to limit
     * @return Formatted string
     */
    public static String limit(String string, int max_length) {
        return (string.length() > max_length) ? string.substring(0, max_length) : string;
    }

    /**
     * Concatenates the provided {@link ChatColor} array, making it useful to be concatenated to any
     * string.
     *
     * <p>
     *
     * @param colors ColorUtils
     * @return Single color line
     */
    public static String concatenate(ChatColor... colors) {
        StringBuilder builder = new StringBuilder();

        for (int x = 0; x < 2; x++) {
            boolean apply_colors = x == 0;

            for (ChatColor color : colors) {
                if (apply_colors ? color.isColor() : color.isFormat()) {
                    builder.append(color.toString());
                }
            }
        }
        return builder.toString();
    }

    /**
     * Colorizes the provided {@link String}.
     *
     * <p>
     *
     * @param colors {@link ChatColor} to apply
     * @param string String to colorize
     * @return Colorized string
     */
    public static String colorize(ChatColor[] colors, String string) {
        return concatenate(colors) + string;
    }

    /**
     * Colorizes the provided {@link String}
     *
     * <p>
     *
     * @param string String to colorize
     * @param colors {@link ChatColor} to apply
     * @return Colorized string
     */
    public static String colorize(String string, ChatColor... colors) {
        return colorize(colors, string);
    }

    /**
     * Translates a string using an alternate color code character into a string that uses the
     * internal {@link ChatColor#COLOR_CHAR} color code character. The alternate color code
     * character will only be replaced if it is immediately followed by 0-9, A-F, a-f, K-O, k-o, R
     * or r.
     *
     * <p>
     *
     * @param alt_char The alternate color code character to replace. Ex: {@literal &}
     * @param string Text containing the alternate color code character
     * @return Text containing the ChatColor.COLOR_CODE color code character
     * @see ChatColor#translateAlternateColorCodes(char, String)
     */
    public static String translateAlternateColorCodes(char alt_char, String string) {
        return ChatColor.translateAlternateColorCodes(alt_char, string);
    }

    /**
     * Translates a string using the alternate color code character '{@literal &}' into a string
     * that uses the internal {@link ChatColor#COLOR_CHAR} color code character. The alternate color
     * code character will only be replaced if it is immediately followed by 0-9, A-F, a-f, K-O,
     * k-o, R or r.
     *
     * <p>
     *
     * @param string Text containing the alternate color code character
     * @return Text containing the ChatColor.COLOR_CODE color code character
     * @see #translateAlternateColorCodes(char, String)
     */
    public static String translateAlternateColorCodes(String string) {
        return translateAlternateColorCodes('&', string);
    }

    /**
     * Translates the strings of the provided array using an alternate color code character into a
     * string that uses the internal {@link ChatColor#COLOR_CHAR} color code character. The
     * alternate color code character will only be replaced if it is immediately followed by 0-9,
     * A-F, a-f, K-O, k-o, R or r.
     *
     * <p>
     *
     * @param alt_char The alternate color code character to replace. Ex: {@literal &}
     * @param array StringUtils array to translate
     * @return New strings array containing the translated strings
     */
    public static String[] translateAlternateColorCodes(char alt_char, String[] array) {
        String[] copy = new String[array.length];

        for (int i = 0; i < copy.length; i++)
            copy[i] = translateAlternateColorCodes(alt_char, array[i]);

        return copy;
    }

    /**
     * Translates the strings of the provided array using the alternate color code character
     * '{@literal &}' into a string that uses the internal {@link ChatColor#COLOR_CHAR} color code
     * character. The alternate color code character will only be replaced if it is immediately
     * followed by 0-9, A-F, a-f, K-O, k-o, R or r.
     *
     * <p>
     *
     * @param array StringUtils collection to translate
     * @return New strings array containing the translated strings
     */
    public static String[] translateAlternateColorCodes(String[] array) {
        return translateAlternateColorCodes('&', array);
    }

    /**
     * Translates the strings of the provided collection using an alternate color code character
     * into a string that uses the internal {@link ChatColor#COLOR_CHAR} color code character. The
     * alternate color code character will only be replaced if it is immediately followed by 0-9,
     * A-F, a-f, K-O, k-o, R or r.
     *
     * <p>
     *
     * @param alt_char The alternate color code character to replace. Ex: {@literal &}
     * @param collection the collection of strings to translate
     * @return New {@link List} of string containing the translated strings
     */
    public static List<String> translateAlternateColorCodes(
            char alt_char, Collection<String> collection) {
        List<String> list = new ArrayList<>();

        for (String string : collection) list.add(translateAlternateColorCodes(alt_char, string));

        return list;
    }

    /**
     * Translates the strings of the provided collection using the alternate color code character
     * '{@literal &}' into a string that uses the internal {@link ChatColor#COLOR_CHAR} color code
     * character. The alternate color code character will only be replaced if it is immediately
     * followed by 0-9, A-F, a-f, K-O, k-o, R or r.
     *
     * <p>
     *
     * @param collection Collection of strings to translate
     * @return New {@link List} of string containing the translated strings
     */
    public static List<String> translateAlternateColorCodes(Collection<String> collection) {
        return translateAlternateColorCodes('&', collection);
    }

    /**
     * Translates the strings of the provided list using an alternate color code character into a
     * string that uses the internal {@link ChatColor#COLOR_CHAR} color code character. The
     * alternate color code character will only be replaced if it is immediately followed by 0-9,
     * A-F, a-f, K-O, k-o, R or r.
     *
     * <p>
     *
     * @param alt_char The alternate color code character to replace. Ex: {@literal &}
     * @param list List of strings to translate
     * @return {@link List} containing the translated strings
     */
    public static List<String> translateAlternateColorCodes(char alt_char, List<String> list) {
        for (int i = 0; i < list.size(); i++)
            list.set(i, translateAlternateColorCodes(alt_char, list.get(i)));

        return list;
    }

    /**
     * Translates the strings of the provided list using the alternate color code character
     * '{@literal &}' into a string that uses the internal {@link ChatColor#COLOR_CHAR} color code
     * character. The alternate color code character will only be replaced if it is immediately
     * followed by 0-9, A-F, a-f, K-O, k-o, R or r.
     *
     * <p>
     *
     * @param list List of strings to translate
     * @return {@link List} containing the translated strings
     */
    public static List<String> translateAlternateColorCodes(List<String> list) {
        return translateAlternateColorCodes('&', list);
    }

    /**
     * Replaces the Bukkit internal color character {@link ChatColor#COLOR_CHAR} by the provided
     * character.
     *
     * <p>
     *
     * @param alt_char Character replacer
     * @param string Target string
     * @return Formatted string
     */
    public static String untranslateAlternateColorCodes(char alt_char, String string) {
        char[] contents = string.toCharArray();

        for (int i = 0; i < contents.length; i++) {
            if (contents[i] == '\'') contents[i] = alt_char;
        }

        return new String(contents);
    }

    /**
     * Replaces the Bukkit internal color character {@link ChatColor#COLOR_CHAR} by the well known
     * color character '{@literal &}'.
     *
     * <p>
     *
     * @param string Target string
     * @return Formatted string
     */
    public static String untranslateAlternateColorCodes(String string) {
        return untranslateAlternateColorCodes('&', string);
    }

    /**
     * Replaces from the strings of the provided array the Bukkit internal color character {@link
     * ChatColor#COLOR_CHAR} by the provided character.
     *
     * <p>
     *
     * @param alt_char Character replacer
     * @param array StringUtils array to replace
     * @return StringUtils array containing the processed strings
     */
    public static String[] untranslateAlternateColorCodes(char alt_char, String[] array) {
        String[] copy = new String[array.length];

        for (int i = 0; i < copy.length; i++)
            copy[i] = untranslateAlternateColorCodes(alt_char, array[i]);

        return copy;
    }

    /**
     * Replaces from the strings of the provided array the Bukkit internal color character {@link
     * ChatColor#COLOR_CHAR} by the well known color character '{@literal &}'.
     *
     * <p>
     *
     * @param array StringUtils array to replace
     * @return StringUtils array containing the processed strings
     */
    public static String[] untranslateAlternateColorCodes(String[] array) {
        return untranslateAlternateColorCodes('&', array);
    }

    /**
     * Replaces from the strings of the provided collection the Bukkit internal color character
     * {@link ChatColor#COLOR_CHAR} by the provided character.
     *
     * <p>
     *
     * @param alt_char Character replacer
     * @param collection StringUtils collection to replace
     * @return List containing the processed strings
     */
    public static List<String> untranslateAlternateColorCodes(
            char alt_char, Collection<String> collection) {
        List<String> list = new ArrayList<>();

        for (String string : collection) list.add(translateAlternateColorCodes(alt_char, string));

        return list;
    }

    /**
     * Replaces from the strings of the provided list the Bukkit internal color character {@link
     * ChatColor#COLOR_CHAR} by the well known color character '{@literal &}'.
     *
     * <p>
     *
     * @param collection StringUtils collection to replace
     * @return List containing the processed strings
     */
    public static List<String> untranslateAlternateColorCodes(Collection<String> collection) {
        return untranslateAlternateColorCodes('&', collection);
    }

    /**
     * Replaces from the strings of the provided collection the Bukkit internal color character
     * {@link ChatColor#COLOR_CHAR} by the provided character.
     *
     * <p>
     *
     * @param alt_char Character replacer
     * @param list List of strings to replace
     * @return {@link List} containing the processed strings
     */
    public static List<String> untranslateAlternateColorCodes(char alt_char, List<String> list) {
        for (int i = 0; i < list.size(); i++)
            list.set(i, translateAlternateColorCodes(alt_char, list.get(i)));

        return list;
    }

    /**
     * Replaces from the strings of the provided collection the Bukkit internal color character
     * {@link ChatColor#COLOR_CHAR} by the well known color character '{@literal &}'.
     *
     * <p>
     *
     * @param list List of strings to replace
     * @return {@link List} containing the processed strings
     */
    public static List<String> untranslateAlternateColorCodes(List<String> list) {
        return untranslateAlternateColorCodes('&', list);
    }

    /**
     * Strips the given string of all colors.
     *
     * <p>
     *
     * @param string the string to strip of color
     * @return Formatted string
     */
    public static String stripColors(String string) {
        return ChatColor.stripColor(string);
    }

    /**
     * Fixes excessive whitespaces replacing it with a single whitespace.
     *
     * <p>
     *
     * @param string String to fix
     * @return Formatted string
     */
    public static String fixExcessiveWhitespaces(String string) {
        return string.replaceAll("\\s{2,}", " ");
    }

    /**
     * Converts only the characters sequence of the given target to lower case.
     *
     * <p>
     *
     * @param string {@code String} where the characters to convert are located
     * @param target Characters sequence reference
     * @return Characters sequence of the given target in the given string converted to lower case
     */
    public static String toLowerCase(String string, String target) {
        return toLowerCase(string, target, Locale.getDefault());
    }

    /**
     * Get a string progress bar for your given data,
     *
     * <p>
     *
     * @param current The current progress
     * @param max Max progress for progress bar
     * @param totalBars Number of individual bars in the progress bar
     * @param symbol Symbol of the bars in progress bar
     * @param completedColor Color of completed bars
     * @param notCompletedColor Default color of bars
     * @return Progress bar for the given data
     * @deprecated Better method mentioned in seeAlso
     * @see StringUtils#getProgressBar(double, double, int, String, ChatColor, ChatColor)
     */
    @Deprecated
    public static String getProgressBar(
            int current,
            int max,
            int totalBars,
            char symbol,
            ChatColor completedColor,
            ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    /**
     * Get a string progress bar for your given data,
     *
     * <p>
     *
     * @param current The current progress
     * @param max Max progress for progress bar
     * @param totalBars Number of individual bars in the progress bar
     * @param symbol Symbol of the bars in progress bar
     * @param completedColor Color of completed bars
     * @param notCompletedColor Default color of bars
     * @return Progress bar for the given data
     */
    public static String getProgressBar(
            double current,
            double max,
            int totalBars,
            String symbol,
            ChatColor completedColor,
            ChatColor notCompletedColor) {
        float percent = (float) ((float) current / max);
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    /**
     * Converts only the characters sequence of the given target to lower case, using the given
     * {@code Locale} rules.
     *
     * <p>
     *
     * @param string {@code String} where the characters to convert are located
     * @param target Characters sequence reference
     * @param locale {@code Locale} rules reference
     * @return Characters sequence of the given target in the given string converted to lower case
     */
    public static String toLowerCase(String string, String target, Locale locale) {
        String lower_case = string.toLowerCase(locale);
        String target_lower_case = target.toLowerCase(locale);

        if (!lower_case.contains(target_lower_case)) return lower_case;

        char[] chars = string.toCharArray();
        int last_index = 0;

        for (int i = 0; i < lower_case.length(); i++) {
            int current_index = lower_case.indexOf(target_lower_case, last_index);

            if (current_index != -1) {
                int end_index = current_index + target_lower_case.length();
                last_index = end_index;

                for (int j = current_index; j < end_index; j++) chars[j] = lower_case.charAt(j);
            }
        }

        return new String(chars);
    }

    /**
     * Converts only the characters sequence of the given target to upper case.
     *
     * <p>
     *
     * @param string {@code String} where the characters to convert are located
     * @param target Characters sequence reference
     * @return Characters sequence of the given target in the given string converted to upper case
     */
    public static String toUpperCase(String string, String target) {
        return toUpperCase(string, target, Locale.getDefault());
    }

    /**
     * Converts only the characters sequence of the given target to upper case, using the given
     * {@code Locale} rules.
     *
     * <p>
     *
     * @param string {@code String} where the characters to convert are located.
     * @param target Characters sequence reference.
     * @param locale {@code Locale} rules reference
     * @return Characters sequence of the given target in the given string converted to upper case.
     */
    public static String toUpperCase(String string, String target, Locale locale) {
        String upper_case = string.toUpperCase(locale);
        String target_upper_case = target.toUpperCase(locale);

        if (!upper_case.contains(target_upper_case)) return upper_case;

        char[] chars = string.toCharArray();
        int last_index = 0;

        for (int i = 0; i < upper_case.length(); i++) {
            int current_index = upper_case.indexOf(target_upper_case, last_index);

            if (current_index != -1) {
                int end_index = current_index + target_upper_case.length();
                last_index = end_index;

                for (int j = current_index; j < end_index; j++) chars[j] = upper_case.charAt(j);
            }
        }

        return new String(chars);
    }
}
