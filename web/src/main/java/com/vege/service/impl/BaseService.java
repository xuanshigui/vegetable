package com.vege.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public class BaseService {

    protected Pageable getPageable(Map<String, String> condition) {
        int page = 0;
        int size = 7;
        String pageStr = condition.get("page");
        if (pageStr != null && !pageStr.equals("")) {
            page = Integer.valueOf(pageStr)-1;
        }

        String sizeStr = condition.get("size");
        if (sizeStr != null && !sizeStr.equals("")) {
            size = Integer.valueOf(sizeStr);
        }
        return PageRequest.of(page, size);
    }
}
