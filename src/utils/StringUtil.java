package utils;

public class StringUtil {

    public static String padLeft(String str,char ch, int numberOfCharacters) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        while (sb.length() < numberOfCharacters){
            sb.insert(0,ch);
        }
        return sb.toString();
    }
}
