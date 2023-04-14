package com.slm.springlibrarymanagement.service.dmo;

import com.slm.springlibrarymanagement.constants.ClassesEnum;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface DataWriterService<T> {

    Long save(String sql, T param);

    int saveAll(String sql, List<T> paramList, ClassesEnum classType) throws SQLException;

    boolean writeDataToFile(List<T> data, ClassesEnum classType) throws BackUpFailedException;

    boolean update(String sql, T param, ClassesEnum book) throws SQLException;
}
