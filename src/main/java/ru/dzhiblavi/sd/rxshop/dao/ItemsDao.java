package ru.dzhiblavi.sd.rxshop.dao;

import rx.Observable;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import com.mongodb.rx.client.Success;
import org.bson.Document;
import ru.dzhiblavi.sd.rxshop.entity.Item;

public class ItemsDao extends MongoDao {
    private final MongoDatabase mongoDatabase;

    public ItemsDao() {
        this.mongoDatabase = mongoClient.getDatabase("default");
    }

    private MongoCollection<Document> getCollection() {
        return this.mongoDatabase.getCollection(Item.class.getName());
    }

    public Observable<Success> add(final Item item) {
        return getCollection().insertOne(Item.toDocument(item));
    }

    public Observable<Item> getAll() {
        return getCollection().find().toObservable().map(Item::fromDocument);
    }
}
