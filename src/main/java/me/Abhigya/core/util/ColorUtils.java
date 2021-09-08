package me.Abhigya.core.util;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utility class to do color parsing or converting work. A color is a either a
 * keyword or a numerical RGB specification.
 * <p>
 * Color property format preference have the following choices:
 * <ul>
 * <li>INT_FORMAT, display color as an integer</li>
 * <li>HTML_FORMAT (#RRGGBB)</li>
 * <li>JAVA_FORMAT (0xRRGGBB)</li>
 * <li>CSS_ABSOLUTE_FORMAT (RGB(r,g,b))</li>
 * <li>CSS_RELATIVE_FORMAT (RGB(r%,g%,b%))</li>
 * </ul>
 */
public class ColorUtils {

    /**
     * Color display preference for HTML style: #RRGGBB.
     * <p>
     * #RRGGBB
     */
    public final static int HTML_FORMAT = 1;
    /**
     * Useful constant for Color display preference, display Color as integer.
     */
    public final static int INT_FORMAT = 0;
    /**
     * Color display preference for JAVA style: 0xRRGGBB.
     */
    public final static int JAVA_FORMAT = 2;
    /**
     * Color display preference for CSS absolute style: RGB(r,g,b).
     */
    public final static int CSS_ABSOLUTE_FORMAT = 3;
    /**
     * Color display preference for CSS relative style: RGB(r%,g%,b%).
     */
    public final static int CSS_RELATIVE_FORMAT = 4;
    /**
     * Default format for display preference: <code>CSS_ABSOLUTE_FORMAT</code>.
     */
    public final static int DEFAULT_FORMAT = CSS_ABSOLUTE_FORMAT;
    public static final Map<String, Color> COLOR_BY_NAME = new HashMap<>(ImmutableMap.<String, Color>builder()
            .put("WHITE", Color.WHITE)
            .put("SILVER", Color.SILVER)
            .put("GRAY", Color.GRAY)
            .put("BLACK", Color.BLACK)
            .put("RED", Color.RED)
            .put("MAROON", Color.MAROON)
            .put("YELLOW", Color.YELLOW)
            .put("OLIVE", Color.OLIVE)
            .put("LIME", Color.LIME)
            .put("GREEN", Color.GREEN)
            .put("AQUA", Color.AQUA)
            .put("TEAL", Color.TEAL)
            .put("BLUE", Color.BLUE)
            .put("NAVY", Color.NAVY)
            .put("FUCHSIA", Color.FUCHSIA)
            .put("PURPLE", Color.PURPLE)
            .put("ORANGE", Color.ORANGE)
            .build());
    /**
     * Regular expression for "RGB(255, 0, 0)", integer range 0 - 255
     */
    private static final String COLOR_CSS_PATTERN = "[rR][gG][bB][(]" +
            "[\\s]*[\\d]+[\\s]*[,]" +
            "[\\s]*[\\d]+[\\s]*[,]" +
            "[\\s]*[\\d]+[\\s]*" +
            "[)]";
    /**
     * Regular expression for "RGB(255.0%, 0.0%, 0.0%)", float range 0.0% -
     * 100.0%
     */
    private static final String COLOR_CSS_PERCENT_PATTERN = "[rR][gG][bB][(]" +
            "[\\s]*[\\d]+(.\\d+)?[\\s]*[%][\\s]*[,]" +
            "[\\s]*[\\d]+(.\\d+)?[\\s]*[%][\\s]*[,]" +
            "[\\s]*[\\d]+(.\\d+)?[\\s]*[%][\\s]*" +
            "[)]";
    /**
     * Compiled pattern for CSS absolute pattern: "RGB(255, 0, 0)"
     */
    private static Pattern cssAbsolutePattern = Pattern
            .compile(COLOR_CSS_PATTERN);
    /**
     * Compiled pattern for CSS relative pattern: "RGB(255%, 0%, 0%)"
     */
    private static Pattern cssRelativePattern = Pattern
            .compile(COLOR_CSS_PERCENT_PATTERN);

    /**
     * Indicates whether the color value is of valid css absolute format:
     * "RGB(r,g,b)". The RGB prefix is case insensitive and r, g, b should be
     * integer value. Whitespace characters are allowed around the numerical
     * values. The followings are some cases of valid css absolute colors:
     * <p>
     * <ul>
     * <li>RGB(255,0,0)</li>
     * <li>Rgb( 255, 0, 0)</li>
     * <li>rgb(300,300,300)</li>
     * </ul>
     * <p>
     *
     * @param value String color value
     * @return <code>true</code> if the color value is in a valid css absolute
     * color representation
     */
    public static boolean isCssAbsolute(String value) {
        return cssAbsolutePattern.matcher(value).matches();
    }

    /**
     * Indicates whether the color value is of a valid css relative format:
     * "RGB( r%, g%, b%)". The RGB prefix is case insensitive and r, g, b should
     * be a float value. Whitespace characters are allowed around the numerical
     * values. The followings are some cases of valid css relative colors:
     * <p>
     * <ul>
     * <li>RGB(100%,0%,0%)</li>
     * <li>Rgb( 100% , 0% , 0% )</li>
     * <li>rgb(200%,200%,200%)</li>
     * </ul>
     * <p>
     *
     * @param value String color value
     * @return <code>true</code> if the color value is in a valid css relative.
     * color representation
     */
    public static boolean isCssRelative(String value) {
        return cssRelativePattern.matcher(value).matches();
    }

    /**
     * Formats an integer RGB value according to the format preference provided.
     * An integer RGB value can be formatted as follows:
     * <ul>
     * <li>INT_FORMAT, display color as an integer.</li>
     * <li>HTML_FORMAT (#RRGGBB)</li>
     * <li>JAVA_FORMAT (0xRRGGBB)</li>
     * <li>CSS_ABSOLUTE_FORMAT (RGB(r,g,b))</li>
     * <li>CSS_RELATIVE_FORMAT (RGB(r%,g%,b%))</li>
     * </ul>
     * <p>
     * The integer value will first be converted into 6-digits hex
     * format(filling "0" from left), so only the right most 6 digits will be
     * used.
     * <p>
     *
     * @param rgbValue  Integer RGB value for a color
     * @param rgbFormat Color display preference, one of Color display preference
     *                  constants. For example, CSS_ABSOLUTE_FORMAT that will convert
     *                  into style "RGB(255,0,0)". If the preference provided is not
     *                  in the predefined list, then
     *                  {@link ColorUtils#CSS_ABSOLUTE_FORMAT} will be applied.
     * @return String representation of the color in the target format.
     * @see ColorUtils#INT_FORMAT
     * @see ColorUtils#HTML_FORMAT
     * @see ColorUtils#JAVA_FORMAT
     * @see ColorUtils#CSS_ABSOLUTE_FORMAT
     * @see ColorUtils#CSS_RELATIVE_FORMAT
     * @see ColorUtils#DEFAULT_FORMAT
     */
    public static String format(int rgbValue, int rgbFormat) {
        String rgbText = StringUtils.toRgbText(rgbValue).toUpperCase();

        switch (rgbFormat) {
            case ColorUtils.INT_FORMAT:
                return String.valueOf(rgbValue);
            case ColorUtils.HTML_FORMAT:
                return rgbText;
            case ColorUtils.JAVA_FORMAT:
                return rgbText.replaceFirst("#", "0x");
            case ColorUtils.CSS_ABSOLUTE_FORMAT:
                return hexToRGB(rgbText, true);
            case ColorUtils.CSS_RELATIVE_FORMAT:
                return hexToRGB(rgbText, false);
            default:
                return hexToRGB(rgbText, true);
        }
    }

    /**
     * Formats an color value according to the format preference provided. See
     * {@link #parseColor(String)}for the allowed color value representations.
     * <p>
     *
     * @param value     Given string containing one of the allowed notation.
     * @param rgbFormat Color display preference, one of Color display preference
     *                  constants. For example, CSS_ABSOLUTE_FORMAT that will convert
     *                  into style "RGB(255,0,0)". If the preference provided is not
     *                  in the predefined list, then
     *                  {@link ColorUtils#CSS_ABSOLUTE_FORMAT}will be applied.
     * @return String representation of the color in the target format.
     * @throws NumberFormatException if the <code>String</code> representing a numerical value
     *                               does not contain a parsable integer.
     * @see ColorUtils#INT_FORMAT
     * @see ColorUtils#HTML_FORMAT
     * @see ColorUtils#JAVA_FORMAT
     * @see ColorUtils#CSS_ABSOLUTE_FORMAT
     * @see ColorUtils#CSS_RELATIVE_FORMAT
     * @see ColorUtils#DEFAULT_FORMAT
     * @see #parseColor(String)
     * @see #format(int, int)
     */
    public static String format(String value, int rgbFormat) {
        int rgbValue = parseColor(value);

        if (rgbValue != -1)
            return format(rgbValue, rgbFormat);

        return null;
    }

    /**
     * Convert an hex color text into an css absolute or relative format. For
     * example, "#FF0000" to "RGB(255, 0, 0) or to "RGB(100.0%, 0%, 0%)s".
     * <p>
     *
     * @param hexColor   Hex color text
     * @param isAbsolute if <tt>true</tt>, return will be in css absolute format, e.g,
     *                   RGB(255,0,0); if <tt>false</tt>, return will be in css
     *                   relative format, e.g, RGB(100.0%,0%,0%).
     * @return Formatted css relative or absolute color format.
     */
    private static String hexToRGB(String hexColor, boolean isAbsolute) {
        assert hexColor.length() == 7;

        // convert from "#FF0000" to "RGB(255, 0, 0)" or to "RGB(100.0%, 0%, 0%)"
        String r = hexColor.substring(1, 3);
        String g = hexColor.substring(3, 5);
        String b = hexColor.substring(5, 7);

        r = Integer.valueOf(r, 16).toString();
        g = Integer.valueOf(g, 16).toString();
        b = Integer.valueOf(b, 16).toString();

        StringBuilder sb = new StringBuilder();

        if (isAbsolute) {
            sb.append("RGB(").append(r).append(",");
            sb.append(g).append(",");
            sb.append(b).append(")");
        } else {
            int r_iValue = Integer.valueOf(r).intValue();
            int g_iValue = Integer.valueOf(g).intValue();
            int b_iValue = Integer.valueOf(b).intValue();

            // round to 1 digit after the comma
            float r_fValue = ((int) (r_iValue * 10 * 100 / 255f + 0.5f)) / 10.0f;
            float g_fValue = ((int) (g_iValue * 10 * 100 / 255f + 0.5f)) / 10.0f;
            float b_fValue = ((int) (b_iValue * 10 * 100 / 255f + 0.5f)) / 10.0f;

            sb.append("RGB(").append(String.valueOf(r_fValue)).append("%,");
            sb.append(String.valueOf(g_fValue)).append("%,");
            sb.append(String.valueOf(b_fValue)).append("%)");
        }

        return sb.toString();
    }

    /**
     * Parse a valid css color expressed in absolute or relative format as
     * "RGB(r%,g%,b%)" or "RGB(r,g,b)" and return the integer value of the
     * color.
     * <p>
     *
     * @param rgbColor Input color value of a valid css relative or absolute format.
     * @return Integer value of the color representation. Return
     * <code>-1</code> if the string is not in a valid absolute or
     * relative representation.
     * @see #isCssAbsolute(String)
     * @see #isCssRelative(String)
     */
    private static int parseRGBColor(String rgbColor) {
        // not valid, return -1.

        if (!isCssAbsolute(rgbColor) && !isCssRelative(rgbColor))
            return -1;

        boolean hasPercentage = false;

        int start = rgbColor.indexOf('(');
        int end = rgbColor.indexOf(')');

        // Cut from "rgb(255,0,0)" to "255, 0, 0", rgb(100%,100%,100%) to "100%,100%,100%"
        String subStr1 = rgbColor.substring(start + 1, end).trim();

        if (subStr1.indexOf('%') != -1) {
            // get rid of '%'
            subStr1 = subStr1.replace('%', ' ');
            hasPercentage = true;
        }

        // Split into 3 strings, we got {255,0,0}
        String[] numbers = subStr1.split(","); //$NON-NLS-1$

        // #FF0000
        StringBuilder colorValue = new StringBuilder("#"); //$NON-NLS-1$

        // parse String(base 10) into String(base 16)

        for (int i = 0; i < 3; i++) {
            int intValue = 0;

            String number = numbers[i].trim();

            if (hasPercentage) {
                float value = Float.parseFloat(number);

                if (value > 100.0f)
                    value = 100.0f;

                // 100.0% => 255, 0.0% => 0
                intValue = (int) (value * 255.0f / 100.0f + 0.5f);
            } else {
                intValue = Integer.parseInt(number);

                // e,g. clip "300" to "255"
                if (intValue > 255)
                    intValue = 255;
            }

            // Fill "f" to "0f"
            String strValue = "0" + Integer.toHexString(intValue); //$NON-NLS-1$
            strValue = strValue.substring(strValue.length() - 2);

            colorValue.append(strValue);
        }

        return Integer.decode(colorValue.toString()).intValue();

    }

    /**
     * Parses the string color value as a color keyword or a numerical RGB
     * notation, return its corresponding rgb integer value. The string value
     * can be one of the followings:
     * <p>
     * <ul>
     * <li>A decimal number: "16711680". The number will be clipped into
     * 0~#FFFFFF</li>
     * <li>A hexadecimal number in HTML format. '#' immediately followed by
     * either three or six hexadecimal characters. The three-digit RGB notation
     * (#rgb) is converted into six-digit form (#rrggbb) by replicating digits,
     * not by adding zeros. For example, #fb0 expands to #ffbb00. Values outside
     * "#FFFFFF" will be clipped.</li>
     * <li>A hexadecimal number in Java format: "0xRRGGBB"</li>
     * <li>A css predefined color name: "red", "green", "yellow" etc.</li>
     * <li>A css absolute or relative notation: "rgb(r,g,b)" or "rgb(r%,g%,b%)".
     * 'rgb(' followed by a comma-separated list of three numerical values
     * (either three integer values in the range of 0-255, or three percentage
     * values in the range of 0.0% to 100.0%) followed by '), Values outside the
     * numerical ranges will be clipped. Whitespace characters are allowed
     * around the numerical values.</li>
     * </ul>
     * <p>
     * These examples given a allowed color value that can be parsed into an
     * integer.
     * <ul>
     * <li>"16711680"</li>
     * <li>"#FFFF00"</li>
     * <li>"#FFFF00F", will be clipped into "#FFFFFF"</li>
     * <li>"0xFF00FF"</li>
     * <li>"red" or "green"</li>
     * <li>rgb(255,0,0)</li>
     * <li>rgb(100.0%,0%,0%)</li>
     * </ul>
     * <p>
     *
     * @param value Given string containing one of the allowed notation.
     * @return Integer value of the color, return <code>-1</code> if the
     * value is not in one of the allowed format. If the value is in a
     * valid integer format, return value will be clipped to 0 ~
     * 0xFFFFFF
     */
    public static int parseColor(String value) {
        if (StringUtils.isBlank(value))
            return -1;

        // 1. Is the value a hexadecimal number or decimal number. It can be
        // six-digit form (#rrggbb) or three-digit form (#rgb) or java style
        // as (0xRRGGBB )
        int first = value.charAt(0);

        if (first == '#' || (first >= '0' && first <= '9')) {
            if (first == '#' && value.length() == 4) {
                // #RGB
                char[] rgb_chars = value.toCharArray();
                char[] rrggbb_chars = {'#', rgb_chars[1], rgb_chars[1],
                        rgb_chars[2], rgb_chars[2], rgb_chars[3], rgb_chars[3]};

                value = String.valueOf(rrggbb_chars);
            }

            try {
                int retValue = Integer.decode(value).intValue();

                return Math.min(retValue, 0xFFFFFF);

            } catch (NumberFormatException e) {
                return -1;
            }

        }


//		// 2. Is this a predefined color?
//
//		int rgbValue = parsePredefinedColor( value );
//		if ( rgbValue != -1 )
//			return rgbValue;

        // 3. CSS absolute or relative format: {rgb(r,g,b)} or {rgb(r%,g%,b%)}

        if (isCssAbsolute(value) || isCssRelative(value))
            return parseRGBColor(value);

        return -1;
    }

    public static Color parseToBukkitColor(String s) {
        String[] parts = s.split(";");
        if (parts.length != 3) {
            Color color;
            if (s.charAt(0) == '#' && s.length() >= 7) {//Hex color
                try {
                    color = Color.fromRGB(Integer.parseUnsignedInt(s.substring(1, 7), 16));
                } catch (IllegalArgumentException e) {
                    color = null;
                }
            } else {
                color = COLOR_BY_NAME.get(s.toUpperCase());
            }
            if (color == null) {
                throw new IllegalStateException("Invalid color \"" + s + "\", use \"R;G;B\", \"#RRGGBB\" or color value!");
            } else return color;
        } else {
            return Color.fromRGB(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        }
    }

    /**
     * Returns the Red, Blue, Green value for a integer RGB color value. The
     * given RGB value should be in the scope of (0~0xFFFFFF), otherwise return
     * <code>null</code>.
     * <p>
     *
     * @param rgbValue Given integer RGB color value.
     * @return Array containing Red, Blue, Green separately. Return
     * <code>null</code> if the value is not in (0~0xFFFFFF).
     */
    public static int[] getRGBs(int rgbValue) {
        if (rgbValue > 0xFFFFFF || rgbValue < 0)
            return null;

        int r = rgbValue >> 16;
        int g = (rgbValue >> 8) & 0xFF;
        int b = rgbValue & 0xFF;

        return new int[]{r, g, b};
    }

    /**
     * Returns the Red, Blue, Green value for a color value. The given string
     * containing one of the allowed notations.
     * <p>
     *
     * @param colorValue Given string color value in one of the allowed notations.
     * @return Array containing Red, Blue, Green separately. Return
     * <code>null</code> if the given color value is not parsable.
     */
    public static int[] getRGBs(String colorValue) {
        int rgb = parseColor(colorValue);
        return rgb == -1 ? null : getRGBs(rgb);
    }

    /**
     * Calculates the integer color value given its red, blue, green values. If
     * any color factor is over 0xFF, it will be clipped to 0xFF; if any color
     * factor is below 0, it will be increased to 0.
     * <p>
     *
     * @param r Red value.
     * @param g Green value.
     * @param b Blue value.
     * @return Integer color value of the given color factors.
     */
    public static int formRGB(int r, int g, int b) {
        // clip to 0 ~ 0xFF
        r = r > 0xFF ? 0xFF : Math.max(r, 0);
        g = g > 0xFF ? 0xFF : Math.max(g, 0);
        b = b > 0xFF ? 0xFF : Math.max(b, 0);

        return (r << 16) + (g << 8) + b;
    }
}
