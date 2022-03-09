package ru.dzhiblavi.sd.rxshop.dao;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;

public class MongoDao {
    protected MongoClient mongoClient = createMongoClient();

    private static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }
}
