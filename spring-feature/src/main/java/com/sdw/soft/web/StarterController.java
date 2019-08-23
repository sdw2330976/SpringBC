package com.sdw.soft.web;

import com.sdw.soft.route.service.ServerRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: shangyd
 * @create: 2019-08-23 17:36:29
 **/
@RequestMapping("/starter")
@RestController
public class StarterController {

    @Autowired
    ServerRouteService serverRouteService;

    @RequestMapping(value = "/route", method = RequestMethod.GET)
    public ResponseEntity<?> serverRoute() {

        return new ResponseEntity<String>(serverRouteService.hello(), HttpStatus.OK);
    }
}
