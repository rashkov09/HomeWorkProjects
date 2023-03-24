package com.lma;

import com.lma.service.AuthorService;
import com.lma.service.BookService;
import com.lma.service.ClientService;
import com.lma.service.impl.AuthorServiceImpl;
import com.lma.service.impl.BookServiceImpl;
import com.lma.service.impl.ClientServiceImpl;

public class LibraryManager {
    private final static AuthorService authorService = new AuthorServiceImpl();

    private final static BookService bookService = new BookServiceImpl();
    private final static ClientService clientService = new ClientServiceImpl();


    public static void main(String[] args) {


        clientService.addClient("Todor","Petrov");

       // authorService.addAuthor("Nora Roberts");

      // bookService.addBook("Nightwork: A Novel", "Nora Roberts", "24/05/2022");
       //System.out.println( bookService.getAllBooks());
      // System.out.println(authorService.getAllAuthors());
    }
}