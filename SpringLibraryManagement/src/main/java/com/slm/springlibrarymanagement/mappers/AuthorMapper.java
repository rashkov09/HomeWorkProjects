package com.slm.springlibrarymanagement.mappers;

import com.slm.springlibrarymanagement.model.dto.AuthorDto;
import com.slm.springlibrarymanagement.model.entities.Author;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author();
        author.setId(rs.getLong("id"));
        author.setName(rs.getString("name"));
        return author;
    }

    public List<AuthorDto> mapToDtoList(List<Author> inboundList) {
        return inboundList.stream().map(author -> new AuthorDto(author.getId(), author.getName())).collect(Collectors.toList());
    }

    public AuthorDto mapToDto(Author inboundAuthor) {
        return new AuthorDto(inboundAuthor.getId(), inboundAuthor.getName());
    }

    public Author mapFromDto(AuthorDto inboundAuthorDto) {
        Author author = new Author();
        author.setId(inboundAuthorDto.getId());
        author.setName(inboundAuthorDto.getName());
        return author;
    }

}
