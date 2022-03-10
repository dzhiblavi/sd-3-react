package ru.dzhiblavi.sd.rxshop.http;

import io.reactivex.netty.protocol.http.server.HttpServer;
import ru.dzhiblavi.sd.rxshop.controller.DeleteController;
import ru.dzhiblavi.sd.rxshop.controller.NewItemController;
import ru.dzhiblavi.sd.rxshop.controller.NewUserController;
import ru.dzhiblavi.sd.rxshop.controller.ListController;
import ru.dzhiblavi.sd.rxshop.dao.ItemsDao;
import ru.dzhiblavi.sd.rxshop.dao.UsersDao;
import rx.Observable;

public class Server {
    private final NewUserController newUserController;
    private final NewItemController newItemController;
    private final ListController listController;
    private final DeleteController deleteController;

    public Server(final UsersDao usersDao, final ItemsDao itemsDao) {
        this.newUserController = new NewUserController(usersDao);
        this.newItemController = new NewItemController(itemsDao);
        this.listController = new ListController(itemsDao, usersDao);
        this.deleteController = new DeleteController(itemsDao, usersDao);
    }

    public void start() {
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {
                    final String method = req.getDecodedPath().substring(1);
                    switch (method) {
                        case "new-user":
                            return newUserController.handleRequest(req, resp);
                        case "new-item":
                            return newItemController.handleRequest(req, resp);
                        case "list-items":
                            return listController.handleRequest(req, resp);
                        case "clear":
                            return deleteController.handleRequest(req, resp);
                        default:
                            return resp.writeString(Observable.just(
                                    "Invalid method: " + method + System.lineSeparator()
                            ));
                    }
                })
                .awaitShutdown();
    }
}
