package com.rrtx.simulationserver.controller.longhu;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @RequestMapping(path = "/securityTest", method = RequestMethod.POST)
    public String filterTest(@RequestParam("formData") String paramsStr){
        System.out.println("data: " + paramsStr);
        return "filter";
    }
}
