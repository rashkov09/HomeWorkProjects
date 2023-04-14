package com.slm.springlibrarymanagement.constants.messages;

public final class BookMessages {
    public static final String BOOKS_OPTION_MESSAGE =
            """
                    Choose what to do with orders:
                    1. Print all books
                    2. Search for a book by name
                    3. Search for a book by issue date
                    4. Search for a book by author name
                    5. Search for books starting with
                    6. Add book
                                        
                    0. Back
                    """;
    public static final String AUTHOR_ID_SELECT_MESSAGE = "Please, insert author id from the list above: ";
    public static final String BOOK_NAME_INPUT_MESSAGE = "Please, insert book name: ";
    public static final String AUTHOR_NAME_INPUT_MESSAGE = "Please, insert valid author name: ";
    public static final String BOOK_ISSUE_DATE_MESSAGE = """
            Can be empty if you are adding copies of a book!
            Please, insert book issue date (dd/MM/yyyy):""";
    public static final String BOOK_ISSUE_DATE_SEARCH_MESSAGE = """
            Please, insert book issue date (dd/MM/yyyy): """;
    public static final String BOOK_NUMBER_OF_COPIES_MESSAGE = "Please, insert number of copies: ";
    public static final String QUERY_NAME_PREFIX = "Please, insert value for search: ";
    public static final String BOOK_ADDED_SUCCESSFULLY_MESSAGE = "Book %s added successfully!";
    public static final String BOOK_ADDITION_FAILED_MESSAGE = "Book %s added successfully!";
    public static final String BOOK_COPIES_ADDED_SUCCESSFULLY_MESSAGE = "%s copies of %s added successfully!";
    public static final String BOOK_COPIES_ADDITION_FAILED_MESSAGE = "Failed to add copies! Please,try again!";
    public static final String BOOK_UPDATE_FAILED_MESSAGE = "Failed to update book!";
}
