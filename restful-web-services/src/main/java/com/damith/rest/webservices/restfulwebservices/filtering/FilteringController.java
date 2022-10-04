package com.damith.rest.webservices.restfulwebservices.filtering;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
		SomeBean somebean = new SomeBean("value1", "value2", "value3", "value4", "value5", "value6", "value7");
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(somebean);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field5","field6");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}

	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringList() {
		List<SomeBean> list  = Arrays.asList(new SomeBean("value1", "value2", "value3", "value4", "value5", "value6", "value7"), new SomeBean("value8", "value9", "value10", "value11", "value12", "value13", "value14"));
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field5","field6");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
}
