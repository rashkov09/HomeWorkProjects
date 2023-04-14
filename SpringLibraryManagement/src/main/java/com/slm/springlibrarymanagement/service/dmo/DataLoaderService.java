package com.slm.springlibrarymanagement.service.dmo;

import com.slm.springlibrarymanagement.constants.ClassesEnum;

import java.util.List;

public interface DataLoaderService<T> {

    List<T> loadDataFromDb(String sql, ClassesEnum classType);

    List<T> loadDataFromFile(ClassesEnum classType);
}
