package ru.dzhiblavi.sd.rxshop.controller;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.dzhiblavi.sd.rxshop.dao.ItemsDao;
import ru.dzhiblavi.sd.rxshop.dao.UsersDao;
import rx.Observable;

public class DeleteController extends Controller {
    private final ItemsDao itemsDao;
    private final UsersDao usersDao;

    public DeleteController(final ItemsDao itemsDao, final UsersDao usersDao) {
        this.itemsDao = itemsDao;
        this.usersDao = usersDao;
    }

    @Override
    public Observable<String> handleRequestImpl(HttpServerRequest<ByteBuf> request) {
        return Observable.merge(
                itemsDao.clear().map(e -> "Dropped items." + System.lineSeparator()),
                usersDao.clear().map(e -> "Dropped users." + System.lineSeparator())
        );
    }
}
