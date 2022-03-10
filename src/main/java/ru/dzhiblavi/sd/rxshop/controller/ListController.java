package ru.dzhiblavi.sd.rxshop.controller;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.dzhiblavi.sd.rxshop.dao.ItemsDao;
import ru.dzhiblavi.sd.rxshop.dao.UsersDao;
import ru.dzhiblavi.sd.rxshop.entity.Cost;
import ru.dzhiblavi.sd.rxshop.entity.Item;
import ru.dzhiblavi.sd.rxshop.exception.HttpException;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class ListController extends Controller {
    private final ItemsDao itemsDao;
    private final UsersDao usersDao;

    public ListController(final ItemsDao itemsDao, final UsersDao usersDao) {
        this.itemsDao = itemsDao;
        this.usersDao = usersDao;
    }

    @Override
    public Observable<String> handleRequestImpl(final HttpServerRequest<ByteBuf> request) throws HttpException {
        final Map<String, List<String>> parameters = request.getQueryParameters();
        final String userName = getArgument(parameters, "user");
        return usersDao.get(userName)
                .flatMap(user ->
                        Observable.merge(Observable.just("Hello, " + userName + "! Prices in " + user.currency.name() + ":" + System.lineSeparator()),
                                itemsDao.getAll()
                                        .map(item -> {
                                            final Cost cost = new Cost(item.cost.getCurrency(), item.cost.getValue());
                                            cost.setCurrency(user.currency);
                                            return new Item(item.name, cost);
                                        })
                                        .map(item -> item.name + ": " + item.cost.getValue() + System.lineSeparator()))
                );
    }
}
