package com.slm.springlibrarymanagement.service.dmo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataWriterService<T> {

    Long save(String sql,T param);

    int saveAll(String sql, List<T> paramList);
}
