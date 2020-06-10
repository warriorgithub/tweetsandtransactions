package techmojo.tweets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    /**
     *
     * @param csvFile
     * @return All tweets
     */
    public static List<String> read(String csvFile) {
        Scanner scanner = null;
        List<String>tweets = new ArrayList<>();
        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new FileReader(new File(csvFile)));
            String inputLine = null;
            while((inputLine = rd.readLine()) != null)
                tweets.add(inputLine);
        }
        catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }
        finally {
            try {
                rd.close();
            }
            catch (IOException ex) {
                System.err.println("An IOException was caught!");
                ex.printStackTrace();
            }
        }
        return tweets;
    }

    /**
     *
     * @param tweets
     * @return extracted hashtag list out of tweets
     */
    public static List<String> extractHashTag(List<String> tweets){
        List<String> hashTags =  new ArrayList<>();
        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
        for(String tweet : tweets){
            Matcher mat = MY_PATTERN.matcher(tweet);
            if (mat.find()) {    //assuming we will have only one hashtag per tweet
                hashTags.add(mat.group());
            }
        }
        return hashTags;
    }

}
