package com.slm.springlibrarymanagement.constants.messages;

public final class AuthorMessages {

    public static final String AUTHOR_NAME_INPUT_MESSAGE = "Please, insert author name: ";

    public static final String AUTHOR_ID_INPUT_MESSAGE = "Please, insert author ID: ";
    public static final String AUTHOR_ADDED_SUCCESSFULLY_MESSAGE = "Author %s added successfully!";
    public static final String AUTHOR_ADDITION_FAILED_MESSAGE = "Something went wrong! Failed to add author, please try again!";

    public static final String AUTHOR_OPTION_MESSAGE =
            """
                    Choose what to do with authors:
                    1. Print all authors
                    2. Search for a author by id
                    3. Search for a author by name
                    4. Add author
                                        
                    0. Back
                    """;

}
