package com.twg0.upgradecapstone.common.security.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.timcook.capstone.user.domain.User;
import com.timcook.capstone.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		log.info("email :: {}",email);
		
		User user = userRepository.findByEmail(email) 
								.orElseThrow(() -> new IllegalArgumentException("없음"));
			
		log.info("LOAD USER BY USERNAME = USER : {}, {}",user.getEmail(), user.getPassword());
		
		return new PrincipalDetails(user);
	}

}
