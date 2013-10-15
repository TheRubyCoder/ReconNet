package util;

public class StringUtilities {
    /**
     * Returns the substring before the last occurrence of a delimiter. The
     * delimiter is not part of the result.
     *
     * @param string    String to get a substring from.
     * @param delimiter String to search for.
     * @return          Substring before the first occurrence of the delimiter.
     */
    public static String substringBeforeLast( String string, String delimiter )
    {
        int pos = string.lastIndexOf( delimiter );

        return pos >= 0 ? string.substring( 0, pos ) : string;
    }
}
