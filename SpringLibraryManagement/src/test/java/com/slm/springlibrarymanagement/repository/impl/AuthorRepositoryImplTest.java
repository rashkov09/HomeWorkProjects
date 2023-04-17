package com.slm.springlibrarymanagement.repository.impl;

import com.slm.springlibrarymanagement.constants.ClassesEnum;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.service.dmo.impl.DataLoaderServiceImpl;
import com.slm.springlibrarymanagement.service.dmo.impl.DataWriterServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorRepositoryImplTest {
    private final List<Author> authorList = new ArrayList<>();
    @Mock
    private DataLoaderServiceImpl<Author> authorDataLoaderService;
    @Mock
    private DataWriterServiceImpl<Author> authorDataWriterService;
    @InjectMocks
    private AuthorRepositoryImpl authorRepository;

    @Before
    public void setUp() throws SQLException {
        Author author = new Author();
        author.setName("Todor Atanasov");
        author.setId(1L);
        authorList.add(author);
        when(authorDataLoaderService.loadDataFromFile(ClassesEnum.Author)).thenReturn(authorList);
        authorRepository.loadAuthors();
    }

    @Test
    public void findAllAuthors() {
        List<Author> current = authorRepository.findAllAuthors();
        Assert.assertTrue(current.size() > 0);
        Assert.assertEquals(current.get(0).getName(), "Todor Atanasov");

    }

    @Test(expected = NoSuchElementException.class)
    public void checkThatFindByNameMethodThrowsAnException() {
        authorRepository.findByName("Dim4o");
    }

    @Test()
    public void checkThatFindByNameMethodRunsSuccessfully() {
        Author author = authorRepository.findByName("Todor Atanasov");
        Assert.assertEquals(1L, author.getId().longValue());
        Assert.assertEquals("Todor Atanasov", author.getName());
    }

    @Test(expected = NoSuchElementException.class)
    public void checkThatFindByIdMethodThrowsAnException() {
        authorRepository.findById(99L);
    }

    @Test()
    public void checkThatFindByIdMethodRunsSuccessfully() {
        Author author = authorRepository.findById(1L);
        Assert.assertEquals(1L, author.getId().longValue());
        Assert.assertEquals("Todor Atanasov", author.getName());
    }

    @Test
    public void addAuthor() {
        Author author = new Author();
        authorRepository.addAuthor(author);
        verify(authorDataWriterService).save(any(), any());
    }
}