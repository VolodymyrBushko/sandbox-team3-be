package com.exadel.discountwebapp.temp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController
{
    @GetMapping( "/{name}" )
    public String hello( @PathVariable( "name" ) String name )
    {
        return "Hello world! This is Sandbox";
    }
}
