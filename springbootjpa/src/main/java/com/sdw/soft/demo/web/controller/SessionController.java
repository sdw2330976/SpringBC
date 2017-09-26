package com.sdw.soft.demo.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by shangyd on 2017/9/24.
 */
@RestController
@RequestMapping("/api/session")
public class SessionController {

    @RequestMapping(value = "/uid",method = RequestMethod.GET)
    public String peekSession(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
