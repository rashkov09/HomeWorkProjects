package com.lma;

import com.lma.cviews.ConsoleView;
import com.lma.cviews.MainMenuView;
import com.lma.model.Author;
import com.lma.service.AuthorService;
import com.lma.service.BookService;
import com.lma.service.ClientService;
import com.lma.service.OrderService;
import com.lma.service.impl.AuthorServiceImpl;
import com.lma.service.impl.BookServiceImpl;
import com.lma.service.impl.ClientServiceImpl;
import com.lma.service.impl.OrderServiceImpl;

public class LibraryManager {
  private static final ConsoleView mainConsoleView = new MainMenuView();



    public static void main(String[] args) {

        mainConsoleView.showItemMenu();
    }
}