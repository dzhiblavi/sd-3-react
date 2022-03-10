package ru.dzhiblavi.sd.rxshop.entity;

import org.bson.Document;

public class User {
    public static final String DAO_NAME = "User";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_CURRENCY = "currency";

    public final String name;
    public final Cost.Currency currency;
    private String id = "";

    private User(final String name, final Cost.Currency currency, final String id) {
        this.name = name;
        this.currency = currency;
        this.id = id;
    }

    public User(final String name, final Cost.Currency currency) {
        this(name, currency, "");
    }

    public static User fromDocument(final Document document) {
        return new User(
                document.getString(FIELD_NAME),
                Cost.Currency.valueOf(document.getString(FIELD_CURRENCY)),
                document.getObjectId(FIELD_ID).toString()
        );
    }

    public static Document toDocument(final User user) {
        final Document document = new Document();
        if (!user.id.isEmpty()) {
            document.append(FIELD_ID, user.id);
        }
        document.append(FIELD_NAME, user.name);
        document.append(FIELD_CURRENCY, user.currency.toString());
        return document;
    }
}
