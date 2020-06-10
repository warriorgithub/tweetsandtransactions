package techmojo.tweets;

import java.util.List;

public interface Tweets {
    List<String> topNTweets(List<String> hashTags, int topNTweets);
}
