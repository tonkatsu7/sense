package com.mechanitis.demo.sense.user;

import com.mechanitis.demo.sense.infrastructure.MessageHandler;
import com.mechanitis.demo.sense.infrastructure.Service;
import com.mechanitis.demo.sense.twitter.TweetParser;

import static com.mechanitis.demo.sense.twitter.TweetParser.getTwitterHandleFromTweet;

public class UserService implements Runnable {
    private static final int PORT = 8083;
    private final Service service;

    public UserService() {
        service = new Service("ws://localhost:8081/tweets/",
                "/users/",
                PORT,
                TweetParser::getTwitterHandleFromTweet);
    }

    @Override
    public void run() {
        service.run();
    }

    public void stop() throws Exception {
        service.stop();
    }

    public static void main(String[] args) {
        new UserService().run();
    }
}
