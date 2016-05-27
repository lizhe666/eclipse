package com.iXinfeng.utils;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisDemo {
    public static void main(String[] args) {
        // Connecting to Redis server on localhost
        Jedis jedis = new Jedis("localhost");
        System.out.println("Server is running: " + jedis.ping());
        // check whether server is running or not
        System.out.println("Connection to server sucessfully");

        // set the data in redis string
        jedis.set("tutorial-name", "Redis tutorial");
        // Get the stored data and print it
        System.out.println("Stored string in redis:: "
                + jedis.get("tutorial-name"));

        // Get the stored data and print it
        Set<String> set = jedis.keys("*");
        for (String key : set) {
            System.out.println(key);
        }
        
        
        
        
    }
}
