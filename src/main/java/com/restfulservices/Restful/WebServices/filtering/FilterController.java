package com.restfulservices.Restful.WebServices.filtering;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilterController {


    // field1, field2
    @GetMapping(path = "/filter")
    public MappingJacksonValue retrieveBean(){
        Testbean testbean=new Testbean("value1","value2","value3");

        SimpleBeanPropertyFilter filter =SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("TestBeanFilter",filter);

        MappingJacksonValue mapping  =new MappingJacksonValue(testbean);

        mapping.setFilters(filterProvider);

        return mapping;
    }


    // field2 and field3
    @GetMapping(path = "/filter-list")
    public MappingJacksonValue retrieveBeanList(){
        List<Testbean> testBeanList= Arrays.asList(new Testbean("value11","value22","value33"),
                new Testbean("value44","valu55","value66"));

        SimpleBeanPropertyFilter filter =SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("TestBeanFilter",filter);
        MappingJacksonValue mapping  =new MappingJacksonValue(testBeanList);
        mapping.setFilters(filterProvider);
        return mapping;
    }
}
