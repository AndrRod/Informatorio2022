package com.Informatorio2022.Proyecto2.config;

import com.Informatorio2022.Proyecto2.Exception.MessagePag;
import com.Informatorio2022.Proyecto2.Exception.MessageResum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.List;

@Component
public class MessagePage {
    @Autowired
    private MessageResum messageResum;
    public MessagePag messageInfo(Page page, List<Object> dtoPageList, WebRequest request){
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        List <Object> content = dtoPageList;
        String nextPath = messageResum.message("page.dont.have.next", null);
        String prevPath = messageResum.message("page.dont.have.prev", null);
        if(!page.isLast()) nextPath= path+ "?page=" + (page.getNumber()+1);
        if(!page.isFirst()) prevPath= path+ "?page=" + (page.getNumber()-1);
        if(page.getContent().isEmpty()) content = Collections.singletonList(messageResum.message("page.empty", null));
        return new MessagePag(content, HttpStatus.ACCEPTED.value(), nextPath, prevPath);
    }
}
