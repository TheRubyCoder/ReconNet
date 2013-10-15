package util;

public class StringUtilities{
    /**
     * Returns the substring before the last occurrence of a given delimiter -
     * excluding this delimiter
     *
     * @param string    String to process.
     * @param delimiter String to search for.
     * @return          Substring before the last occurrence of the delimiter.
     * example:
     * StringUtilities.substringBeforeLast("Hello.Brave.New.World", ".");
     * => "Hello.Brave.New"
     */
    public static String substringBeforeLast(String string, String delimiter){
        int pos = string.lastIndexOf(delimiter);

        return pos >= 0 ? string.substring(0, pos) : string;
    } 
}
