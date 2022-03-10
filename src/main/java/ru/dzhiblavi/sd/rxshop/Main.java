package ru.dzhiblavi.sd.rxshop;


import ru.dzhiblavi.sd.rxshop.dao.ItemsDao;
import ru.dzhiblavi.sd.rxshop.dao.UsersDao;
import ru.dzhiblavi.sd.rxshop.http.Server;

public class Main {
    public static void main(final String[] args) {
        new Server(new UsersDao(), new ItemsDao()).start();
    }
}
