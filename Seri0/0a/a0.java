import java.util.Arrays;
import java.util.Scanner;

public class a0 {

    static String[] GetSplitedWords() {
        Scanner input = new Scanner(System.in);
        String data = input.nextLine();

        String SplitedData[] = data.split("-");
        for (int i = 0; i < SplitedData.length; i++) {
            SplitedData[i] = SplitedData[i].trim();
        } // Trimming withspace from each word

        input.close();
        return SplitedData;
    }

    static int CountCommonChars(String first, String second, Boolean fromStart) {
        int count = 0;
        while (count < Math.min(first.length(), second.length())) {
            String firstSubStr;
            String secondSubStr;
            if (fromStart) {
                firstSubStr = first.substring(0, count);
                secondSubStr = second.substring(0, count);
            } else {
                firstSubStr = first.substring(first.length() - count,
                        first.length());
                secondSubStr = second.substring(second.length() - count,
                        second.length());
            }
            if (firstSubStr.equals(secondSubStr)) {
                count++;
            } else {
                count--;
                break;
            }
        }
        return count;
    }

    static void PrintIdentifierChars(String[] wordArr, Boolean fromStart) {
        int[] commonCharsCount = new int[wordArr.length];
        Arrays.fill(commonCharsCount, 1);
        for (int i = 0; i < wordArr.length; i++) {
            for (int j = i + 1; j < wordArr.length; j++) {
                int charCount = CountCommonChars(wordArr[i], wordArr[j], fromStart);
                charCount++;
                // System.out.printf("'%s' to '%s' cmn:%d\n",wordArr[i],wordArr[j],charCount);
                if (commonCharsCount[i] < charCount) {
                    commonCharsCount[i] = (charCount > wordArr[i].length()) ? wordArr[i].length() : charCount;
                }
                if (commonCharsCount[j] < charCount) {
                    commonCharsCount[j] = (charCount > wordArr[j].length()) ? wordArr[j].length() : charCount;
                }
            }
            System.out.print(commonCharsCount[i]);
            if (i + 1 < wordArr.length)
                System.out.print(" ");
        }
    }

    public static void main(String[] args) {

        String[] wordArr = GetSplitedWords();

        // compare each word with others (first chars)
        PrintIdentifierChars(wordArr, true);
        System.out.print("\n");
        // compare each word with others (last chars)
        PrintIdentifierChars(wordArr, false);
    }
}