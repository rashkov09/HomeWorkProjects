package com.slm.springlibrarymanagement;

import com.slm.springlibrarymanagement.cviews.ConsoleView;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLibraryManagementApplication implements CommandLineRunner {
    private final ConsoleView mainView;

    public SpringLibraryManagementApplication(@Qualifier("mainMenuView") ConsoleView mainView) {
        this.mainView = mainView;
    }

    public static void main(String[] args) {
     SpringApplication.run(SpringLibraryManagementApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        mainView.showItemMenu(mainView);
    }
}
