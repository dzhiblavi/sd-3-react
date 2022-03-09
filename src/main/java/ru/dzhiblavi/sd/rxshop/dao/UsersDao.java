package ru.dzhiblavi.sd.rxshop.dao;

import rx.Observable;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import com.mongodb.rx.client.Success;
import org.bson.Document;
import ru.dzhiblavi.sd.rxshop.entity.User;

public class UsersDao extends MongoDao {
    private final MongoDatabase mongoDatabase;

    public UsersDao() {
        this.mongoDatabase = mongoClient.getDatabase("default");
    }

    private MongoCollection<Document> getCollection() {
        return this.mongoDatabase.getCollection(User.class.getName());
    }

    public Observable<Success> add(final User user) {
        return getCollection().insertOne(User.toDocument(user));
    }

    public Observable<User> getAll() {
        return getCollection().find().toObservable().map(User::fromDocument);
    }
}
