package com.lucifer.gada.electronics.helper;

import com.lucifer.gada.electronics.dtos.PageableResponse;
import com.lucifer.gada.electronics.dtos.UserDto;
import com.lucifer.gada.electronics.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UserPageable {

    public static <U,V>PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type){


        List<U> entity = page.getContent();

        List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object,type)).collect(Collectors.toList());
        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNo(page.getNumber());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }
}
