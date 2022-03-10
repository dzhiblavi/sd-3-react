package ru.dzhiblavi.sd.rxshop.controller;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.dzhiblavi.sd.rxshop.dao.ItemsDao;
import ru.dzhiblavi.sd.rxshop.entity.Cost;
import ru.dzhiblavi.sd.rxshop.entity.Item;
import ru.dzhiblavi.sd.rxshop.exception.HttpException;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class NewItemController extends Controller {
    private final ItemsDao itemsDao;

    public NewItemController(final ItemsDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    @Override
    public Observable<String> handleRequestImpl(final HttpServerRequest<ByteBuf> request) throws HttpException {
        final Map<String, List<String>> parameters = request.getQueryParameters();
        final String itemName = getArgument(parameters, "name");
        final double cost = getCostParameter(parameters);
        final Cost.Currency currency = getCurrencyParameter(parameters);
        return itemsDao.add(new Item(itemName, new Cost(currency, cost)))
                .map(
                        e -> "Item " + itemName + " with price " + currency + " " + cost + " has been successfully added."
                                + System.lineSeparator()
                );
    }
}
