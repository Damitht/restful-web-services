package com.damith.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {
	
	@GetMapping("/v1/person")
	public PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("Thenu Tennakoon");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 getSecondVersionOfPerson() {
		return new PersonV2(new Name("Thinu", "Tennakoon"));
	}
	
	@GetMapping(path = "/person", params = "version=1")
	public PersonV1 getFirstVersionRequestParameter() {
		return new PersonV1("ThinuThenu Tennakoon");
	}
	
	@GetMapping(path = "/person", params = "version=2")
	public PersonV2 getSecondVersionRequestParameter() {
		return new PersonV2(new Name("ThinugiThenuga", "Tennakoon"));
	}
	
	@GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
	public PersonV1 getFirstVersionHeader() {
		return new PersonV1("ThinuThenu Tennakoon");
	}
	
	@GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
	public PersonV2 getSecondVersionHeader() {
		return new PersonV2(new Name("ThinugiThenuga", "Tennakoon"));
	}
	
	@GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
	public PersonV1 getFirstVersionAcceptHeader() {
		return new PersonV1("ThinuThenu Tennakoon");
	}
	
	@GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json")
	public PersonV2 getSecondVersionAcceptHeader() {
		return new PersonV2(new Name("ThinugiThenuga", "Tennakoon"));
	}
}
