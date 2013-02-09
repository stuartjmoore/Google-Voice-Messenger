package temp.gvm;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class Utilities
{
    /**
     * Escapes a raw text to be used in Regular Expression patterns
     * 
     * @param text
     *            The text needing to be used in regular expressions
     * @param nonCapturing
     *            Whether or not this text needs be grouped as a non capturing
     *            group
     * @return Regex escaped text
     */
    private static final String escapeForRegEx(String text, boolean nonCapturing)
    {
        if (nonCapturing)
            return "(?:" + Pattern.quote(text) + ")";

        return Pattern.quote(text);
    }

    /**
     * Pulls the text between the <code>startToken</code> and the
     * <code>endToken</code>
     * 
     * @param text
     *            Text to search
     * @param startToken
     *            Identifier for start of selection
     * @param endToken
     *            Identifier for end of selection
     * @return <code>null</code> if not found
     */
    public static final String getSubstring(String text, String startToken,
            String endToken)
    {
        return getSubstring(text, startToken, endToken, false, false, false);
    }

    /**
     * Pulls the text between the <code>startToken</code> and the
     * <code>endToken</code>
     * 
     * @param text
     *            Text to search
     * @param startToken
     *            Identifier for start of selection
     * @param endToken
     *            Identifier for end of selection
     * @param includeNewLines
     *            Whether to include newline characters in our selection
     * @param gotoEnd
     *            Whether to go to the end of the string if
     *            <code>endToken</code> cannot be found
     * @param includeTokens
     *            Whether to return the <code>startToken</code> and
     *            <code>endToken</code> in the returned output
     * @return <code>null</code> if not foud
     */
    public static final String getSubstring(String text, String startToken,
            String endToken, boolean includeNewLines, boolean gotoEnd,
            boolean includeTokens)
    {
        String rgxStartToken = escapeForRegEx(startToken, true);
        String rgxEndToken = escapeForRegEx(endToken, true);
        String ret = null;
        Pattern rgxPattern = null;
        Matcher rgxMatch = null;

        if (includeNewLines) {
            rgxPattern = Pattern.compile(rgxStartToken + "(.*?)" + rgxEndToken,
                    Pattern.DOTALL);
        } else {
            rgxPattern = Pattern.compile(rgxStartToken + "(.*?)" + rgxEndToken);
        }

        rgxMatch = rgxPattern.matcher(text);
        if (rgxMatch.find()) {
            // Found, start/end tokens are non-capturing so grab first group
            ret = rgxMatch.group(1);
        } else if (gotoEnd) {
            if (includeNewLines) {
                rgxPattern = Pattern.compile(
                        rgxStartToken + "(.*?)(?:($|\\Z))", Pattern.DOTALL);
            } else {
                rgxPattern = Pattern
                        .compile(rgxStartToken + "(.*?)(?:($|\\Z))");
            }

            rgxMatch = rgxPattern.matcher(text);
            if (rgxMatch.find()) {
                // Found, start/end tokens are non-capturing so grab first group
                ret = rgxMatch.group(1);
            }
        }

        // ret == null if not found
        if (ret != null && includeTokens)
            ret = startToken + ret + endToken;

        return ret;
    }
}
