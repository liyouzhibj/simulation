package com.rrtx.simulationserver.controller.filtertest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterTestController {

    @RequestMapping(path = "/filterTest", method = RequestMethod.POST)
    public String filterTest(@RequestParam("data") String paramsStr){
        System.out.println("data: " + paramsStr);
        return "filter";
    }
}
