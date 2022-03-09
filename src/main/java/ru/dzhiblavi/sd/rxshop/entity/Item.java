package ru.dzhiblavi.sd.rxshop.entity;

import org.bson.Document;

public class Item {
    private final Cost cost;
    private final String name;
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
                document.getString("name"),
                new Cost(
                        Cost.Currency.valueOf(document.getString("currency")),
                        document.getDouble("cost")
                ),
                document.getString("_id")
        );
    }

    public static Document toDocument(final Item item) {
        final Document document = new Document();
        if (!item.id.isEmpty()) {
            document.append("_id", item.id);
        }
        document.append("name", item.name);
        document.append("currency", item.cost.getCurrency());
        document.append("cost", item.cost.getValue());
        return document;
    }
}
