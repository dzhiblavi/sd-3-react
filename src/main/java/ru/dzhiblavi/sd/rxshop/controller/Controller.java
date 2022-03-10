package ru.dzhiblavi.sd.rxshop.controller;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import ru.dzhiblavi.sd.rxshop.entity.Cost;
import ru.dzhiblavi.sd.rxshop.exception.HttpException;
import rx.Observable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public abstract class Controller {
    public abstract Observable<String> handleRequestImpl(final HttpServerRequest<ByteBuf> request) throws HttpException;

    public Observable<Void> handleRequest(final HttpServerRequest<ByteBuf> request,
                                          final HttpServerResponse<ByteBuf> response) {
        Observable<String> result;
        try {
            result = this.handleRequestImpl(request)
                    .onErrorReturn(error -> {
                        if (error instanceof HttpException) {
                            final HttpException httpException = (HttpException) error;
                            response.setStatus(HttpResponseStatus.valueOf(httpException.getCode()));
                            return httpException.getMessage() + System.lineSeparator();
                        } else {
                            response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
                            return getErrorString(error) + System.lineSeparator();
                        }
                    });
        } catch (final HttpException httpException) {
            response.setStatus(HttpResponseStatus.valueOf(httpException.getCode()));
            result = Observable.just(httpException.getMessage() + System.lineSeparator());
        } catch (final Exception e) {
            response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
            result = Observable.just(getErrorString(e) + System.lineSeparator());
        }
        return response.writeString(result);
    }

    private String getErrorString(final Throwable e) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return pw.toString();
    }

    protected String getArgument(final Map<String, List<String>> parameters, final String name) throws HttpException {
        final List<String> list = parameters.get(name);
        if (list == null || list.size() < 1) {
            throw new HttpException(400, "Required parameter is not found: " + name);
        }
        return list.get(0);
    }

    protected Cost.Currency getCurrencyParameter(final Map<String, List<String>> parameters) throws HttpException {
        return Cost.Currency.valueOf(getArgument(parameters, "currency"));
    }

    protected Double getCostParameter(final Map<String, List<String>> parameters) throws HttpException {
        try {
            return Double.parseDouble(getArgument(parameters, "cost"));
        } catch (final NumberFormatException e) {
            throw new HttpException(400, "Cost must be double.");
        }
    }
}
