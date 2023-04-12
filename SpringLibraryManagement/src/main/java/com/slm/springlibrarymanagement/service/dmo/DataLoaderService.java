package com.slm.springlibrarymanagement.service.dmo;

import java.util.List;

public interface DataLoaderService<T> {

    List<T> loadData(String sql, T object);
}
