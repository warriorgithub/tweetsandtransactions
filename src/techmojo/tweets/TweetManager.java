package techmojo.tweets;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * Using hashmap to holding occurance count to hashtags
 * sort them based on their count
 * display the result of topNcount (N = We can give any number here e.g 5,10,2)
 **/
public class TweetManager implements Tweets{
    private static final String FILE_PATH = "src/techmojo/tweets/sample.csv";
    public static void main(String[] args) {
        final int TOPN=10;
        //For tweets I'm using sample tweets dataset downloaded from internate "sample.csv"
        List<String> tweets = Utility.read(FILE_PATH);
        TweetManager tweetManager = new TweetManager();
        for(String hashTag : tweetManager.topNTweets(tweets,TOPN)){
            System.out.println(hashTag);
        }
    }

    /**
     *
     * @param tweets
     * @param topNTweets
     * @return List of top N tweets
     */
    @Override
    public List<String> topNTweets(List<String> tweets, int topNTweets) {
        Map<String,Integer> tweetMap = new HashMap<>();
        for(String hashTag : Utility.extractHashTag(tweets)){
            tweetMap.put(hashTag,tweetMap.getOrDefault(hashTag,0)+1);
        }
        List<String> topHashTags = sortByOccurance(tweetMap)
                                .entrySet()
                                .stream()
                                .map(Map.Entry::getKey)
                                .limit(topNTweets)
                                .collect(Collectors.toList());
        return topHashTags;
    }

    /**
     *
     * @param hashTags
     * @return sorted map by hastag occurance
     */
    private Map<String,Integer> sortByOccurance(Map<String,Integer> hashTags){
        hashTags.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return  hashTags;
    }
}
