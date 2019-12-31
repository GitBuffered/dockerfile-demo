package com.wbb.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerfileController {

    @RequestMapping(value = "/execute/{name}", method = RequestMethod.GET)
    public String execute(@PathVariable("name") String name){
        return "This is your write [ " + name + " ]";
    }
}
