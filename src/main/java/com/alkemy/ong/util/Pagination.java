package com.alkemy.ong.util;

import org.springframework.web.util.UriComponentsBuilder;


public class Pagination {
    public static final int INITIAL_PAGE = 0;
    public static final int PAGE_SIZE = 10;

    public static String constructNextPageUri(UriComponentsBuilder uriBuilder, int page, int size) {
        return uriBuilder
                .replaceQueryParam("page", page + 1)
                .replaceQueryParam("size", size)
                .build()
                .encode()
                .toUriString();
    }

    public static String constructPreviousPageUri(UriComponentsBuilder uriBuilder, int page, int size) {
        return uriBuilder
                .replaceQueryParam("page", page - 1)
                .replaceQueryParam("size", size)
                .build()
                .encode()
                .toUriString();
    }
}