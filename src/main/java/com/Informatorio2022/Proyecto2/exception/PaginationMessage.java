package com.Informatorio2022.Proyecto2.exception;

import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.exception.MessageResum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Component
public class PaginationMessage {
    @Autowired
    private MessageResum messageResum;
    public MessagePag messageInfo(Page page, List<Object> dtoPageList, HttpServletRequest request){
        String path = request.getRequestURI();
        List <Object> content = dtoPageList;
        String nextPath = messageResum.message("page.dont.have.next", null);
        String prevPath = messageResum.message("page.dont.have.prev", null);
        if(!page.isLast()) nextPath= path+ "?page=" + (page.getNumber()+1);
        if(!page.isFirst()) prevPath= path+ "?page=" + (page.getNumber()-1);
        if(page.getContent().isEmpty()) content = Collections.singletonList(messageResum.message("page.empty", null));
        return new MessagePag(content, HttpStatus.ACCEPTED.value(), nextPath, prevPath);
    }
}
