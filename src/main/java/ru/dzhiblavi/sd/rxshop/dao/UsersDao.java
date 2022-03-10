package ru.dzhiblavi.sd.rxshop.dao;

import com.mongodb.bulk.DeleteRequest;
import com.mongodb.client.result.DeleteResult;
import org.bson.BsonDocument;
import org.bson.BsonString;
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
        return this.mongoDatabase.getCollection(User.DAO_NAME);
    }

    public Observable<Success> add(final User user) {
        return getCollection().insertOne(User.toDocument(user));
    }

    public Observable<User> get(final String name) {
        return getCollection()
                .find(new BsonDocument(User.FIELD_NAME, new BsonString(name)))
                .toObservable().map(User::fromDocument);
    }

    public Observable<User> getAll() {
        return getCollection().find().toObservable().map(User::fromDocument);
    }

    public Observable<DeleteResult> clear() {
        return getCollection().deleteMany(new Document());
    }
}
