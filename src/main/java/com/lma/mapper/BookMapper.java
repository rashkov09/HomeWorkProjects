package com.lma.mapper;

import com.lma.model.Author;
import com.lma.model.Book;
import com.lma.util.LocalDateFormatter;

import java.time.LocalDate;

public class BookMapper {

    public static Book mapBookFromString(String value) {
        String[] subValues=  value.split("_");
        String bookName = subValues[0];
        Author author = new Author(subValues[1]);
        LocalDate publishDate = LocalDateFormatter.stringToLocalDate(subValues[2]);
        return new Book(bookName,author,publishDate);
    }

    public static String mapBookToString(Book book){
        String publishDateString = LocalDateFormatter.localDateToString(book.getPublishDate());
        return String.format("%s_%s_%s",book.getName(),book.getAuthor().getName(),publishDateString);
    }

}
