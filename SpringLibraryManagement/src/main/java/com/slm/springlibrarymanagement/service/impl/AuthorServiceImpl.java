package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.accessor.AuthorFileAccessor;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.repository.AuthorRepository;
import com.slm.springlibrarymanagement.service.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorFileAccessor authorFileAccessor;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorFileAccessor authorFileAccessor) {
        this.authorRepository = authorRepository;
        this.authorFileAccessor = authorFileAccessor;
    }



    @Override
    public void loadAllAuthors() {
        authorFileAccessor.readAllLines().forEach(line -> {
            String[] data = line.split("\\.",2);
            Author author = new Author();
            author.setName(data[1]);
           authorRepository.save(author);
        });
    }
}
