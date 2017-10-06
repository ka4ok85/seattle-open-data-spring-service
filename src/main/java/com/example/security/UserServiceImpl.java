package com.example.security;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.models.JwtUser;
import com.example.repository.UserRepository;

@Service("userDetailsService")
public class UserServiceImpl implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public JwtUser loadUserByUsername(String login) throws UsernameNotFoundException {

		User user = userRepository.findByLoginAndStatus(login, User.STATUS_ENABLED);
		if (user == null) {
			log.info("No active user found with login {}.", login);

			throw new UsernameNotFoundException(String.format("No active user found with login '%s'.", login));
		} else {
			SimpleGrantedAuthority simpleGrantedAuthority;
			if (user.isAdmin()) {
				simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
			} else {
				simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
			}

			List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
			grantedAuthorityList.add(simpleGrantedAuthority);

			JwtUser jwtUser = new JwtUser(user.getLogin(), user.getEmail(), user.getPassword(), user.getRole(),
					user.getStatus(), grantedAuthorityList);

			return jwtUser;
		}
	}
}
