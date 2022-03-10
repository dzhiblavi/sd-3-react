package ru.dzhiblavi.sd.rxshop.controller;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.dzhiblavi.sd.rxshop.dao.UsersDao;
import ru.dzhiblavi.sd.rxshop.entity.Cost;
import ru.dzhiblavi.sd.rxshop.entity.User;
import ru.dzhiblavi.sd.rxshop.exception.HttpException;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class NewUserController extends Controller {
    private final UsersDao usersDao;

    public NewUserController(final UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public Observable<String> handleRequestImpl(final HttpServerRequest<ByteBuf> request) throws HttpException {
        final Map<String, List<String>> parameters = request.getQueryParameters();
        final String userName = getArgument(parameters, "user");
        final Cost.Currency currency = getCurrencyParameter(parameters);
        return usersDao
                .add(new User(userName, currency))
                .map(u -> "User " + userName + " has been successfully added!" + System.lineSeparator());
    }
}
