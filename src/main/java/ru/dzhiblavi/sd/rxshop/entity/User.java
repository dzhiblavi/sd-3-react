package ru.dzhiblavi.sd.rxshop.entity;

import org.bson.Document;

public class User {
    private final String name;
    private final Cost.Currency currency;
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
                document.getString("name"),
                Cost.Currency.valueOf(document.getString("currency")),
                document.getString("_id")
        );
    }

    public static Document toDocument(final User user) {
        final Document document = new Document();
        if (!user.id.isEmpty()) {
            document.append("_id", user.id);
        }
        document.append("name", user.name);
        document.append("currency", user.currency);
        return document;
    }
}
