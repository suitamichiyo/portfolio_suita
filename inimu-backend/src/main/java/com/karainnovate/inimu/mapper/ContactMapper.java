package com.karainnovate.inimu.mapper;

import com.karainnovate.inimu.model.Contact;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ContactMapper {

    @Insert("INSERT INTO contacts (category, name, company, email, message) " +
            "VALUES (#{category}, #{name}, #{company}, #{email}, #{message})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Contact contact);
}
