package com.damith.rest.webservices.restfulwebservices.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	//JPA/Hibernate > Database
	//UserDaoService > Static List
	
	private static List<User> users = new ArrayList<User>();
	private static int count = 0;
	
	static{
		users.add(new User(++count, "Damith", LocalDate.now().minusYears(42)));
		users.add(new User(++count, "Chaturi", LocalDate.now().minusYears(39)));
		users.add(new User(++count, "Thinugi", LocalDate.now().minusYears(10)));
		users.add(new User(++count, "Thenuga", LocalDate.now().minusYears(6)));
	}
	
	public List<User> findAll(){
		return users;
	}

	public User findUserById(Integer id) {
//		Predicate<User> predicate = user -> user.getId().intValue() == id.intValue();
		return users.stream()
				.filter(user -> user.getId().intValue() == id.intValue())
//				.findFirst().get();
				.findFirst().orElse(null);
	}
	
	public User save(User user){
		user.setId(++count);
		users.add(user);
		return user;
	}
	
	public void deleteUserById(Integer id) {
		Predicate<User> predicate = user -> user.getId().intValue() == id.intValue();
		users.removeIf(predicate);
	}
	
	

}
