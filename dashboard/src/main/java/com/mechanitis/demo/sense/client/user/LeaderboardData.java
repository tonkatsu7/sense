package com.mechanitis.demo.sense.client.user;

import com.mechanitis.demo.sense.infrastructure.MessageListener;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class LeaderboardData implements MessageListener<String> {
    private static final int NUMBER_OF_LEADERS = 17;
    private final Map<String, TwitterUser> allTwitterUsers = new HashMap<>();

    private ObservableList<TwitterUser> items = observableArrayList();

    public ObservableList<TwitterUser> getItems() {
        return items;
    }

    @Override
    public void onMessage(String twitterHandle) {
        TwitterUser twiiterUser = allTwitterUsers.computeIfAbsent(twitterHandle, TwitterUser::new);

        twiiterUser.incrementCount();

        List<TwitterUser> topTweeters = allTwitterUsers.values().stream()
                                                        .sorted(Comparator
                                                                .comparing(TwitterUser::getTweetCount)
                                                                .reversed())
                                                        .limit(NUMBER_OF_LEADERS)
                                                        .collect(Collectors.toList());
        Platform.runLater(() -> items.setAll(topTweeters));
    }
}
