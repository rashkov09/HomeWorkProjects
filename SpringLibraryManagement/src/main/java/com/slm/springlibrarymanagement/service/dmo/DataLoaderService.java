package com.slm.springlibrarymanagement.service.dmo;

import java.util.List;

public interface DataLoaderService<T> {

    List<T> loadDataFromDb(String sql, T object);

    List<T> loadDataFromFile(T object);
}
