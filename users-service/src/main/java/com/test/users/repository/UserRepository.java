package com.test.users.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.test.users.domain.User;



@RepositoryRestResource(path = "users-repo")
public interface UserRepository extends PagingAndSortingRepository<User, Long>{

	//localhost:8090/api/users/users-repo/search/findByUsername?username=fercort
	// En el cliente que invoca el WS se apenda al path la palabra "search" para poder acceder a estos metodos personalizados
	@RestResource(path="get-by-username")
	User findByUsername(@Param("username") String username);
}
