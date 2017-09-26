package com.sdw.soft.demo.web.controller;

import com.sdw.soft.demo.repository.UserRepository;
import com.sdw.soft.demo.vo.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by shangyindong on 2017/3/1.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "获取用户信息",notes = "根据id查询用户信息")
    @ApiImplicitParam(name = "id",value = "用户id",paramType = "path",dataType = "Long",required = true)
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User view(@PathVariable("id")Long id){
        logger.info("UserControler test for logback configuration.");
        return userRepository.findOne(id);
    }

    @ApiOperation(value = "保存用户信息",notes = "")
//    @ApiImplicitParams({
//            @ApiImplicitParam(value = "userName",paramType = "query",dataType = "String",required = true),
//            @ApiImplicitParam(value = "age",paramType = "query",dataType = "integer" ,required = true)
//    })
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public User save(@RequestParam(value = "userName")String userName,
                       @RequestParam(value = "age")Integer age) {
        return userRepository.save(new User(userName, age));

    }
    @ApiIgnore
    @RequestMapping("/test")
    public String view() {
        return "hello world";
    }

    @ApiIgnore
    @RequestMapping("/exceptionHandler")
    public String testExceptionHandler() throws Exception {
        System.out.println("test");
        throw new Exception("Test Exception Handler");
    }
}
