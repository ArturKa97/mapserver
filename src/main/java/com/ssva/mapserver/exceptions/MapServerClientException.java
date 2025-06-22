package com.ssva.mapserver.exceptions;

public class MapServerClientException extends RuntimeException {

    public MapServerClientException(String errorFetchingMapServerData, Throwable e) {
        super(errorFetchingMapServerData, e);
    }

}
