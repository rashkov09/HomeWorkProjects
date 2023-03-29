package com.lma;

import com.lma.cviews.ConsoleView;
import com.lma.cviews.MainMenuView;

public class LibraryManager {
  private static final ConsoleView mainConsoleView = new MainMenuView();



    public static void main(String[] args) {

        mainConsoleView.showItemMenu();
    }
}