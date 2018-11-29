package com.mio4.mapper;

import com.mio4.domain.Book;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BookMapper {

    @Select("SELECT * FROM TB_BOOK")
    List<Book> findAll();
}
