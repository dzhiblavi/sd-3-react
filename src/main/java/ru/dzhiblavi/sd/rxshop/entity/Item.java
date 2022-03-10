package ru.dzhiblavi.sd.rxshop.entity;

import org.bson.Document;

public class Item {
    public static final String DAO_NAME = "Item";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_COST = "cost";
    public static final String FIELD_CURRENCY = "currency";

    public final Cost cost;
    public final String name;
    private String id = "";

    private Item(final String name, final Cost cost, final String id) {
        this.name = name;
        this.cost = cost;
        this.id = id;
    }

    public Item(final String name, final Cost cost) {
        this(name, cost, "");
    }

    public static Item fromDocument(final Document document) {
        return new Item(
                document.getString(FIELD_NAME),
                new Cost(
                        Cost.Currency.valueOf(document.getString(FIELD_CURRENCY)),
                        document.getDouble(FIELD_COST)
                ),
                document.getObjectId(FIELD_ID).toString()
        );
    }

    public static Document toDocument(final Item item) {
        final Document document = new Document();
        if (!item.id.isEmpty()) {
            document.append(FIELD_ID, item.id);
        }
        document.append(FIELD_NAME, item.name);
        document.append(FIELD_CURRENCY, item.cost.getCurrency().toString());
        document.append(FIELD_COST, item.cost.getValue());
        return document;
    }
}
