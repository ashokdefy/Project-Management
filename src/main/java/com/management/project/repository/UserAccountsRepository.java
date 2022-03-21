package com.management.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.management.project.model.UserAccount;

@Repository
public interface UserAccountsRepository extends PagingAndSortingRepository<UserAccount, Long> {
	
	List<UserAccount> findAll();

	UserAccount getUserByEmail(String email);

	UserAccount getUserByuserName(String userName);
}
