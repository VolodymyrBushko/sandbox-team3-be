package com.exadel.discountwebapp.temp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController
{
    @GetMapping( "/{name}" )
    public String hello( @PathVariable( value = "name", required = false ) String name )
    {
        return "Hello world! " + name;
    }
}
