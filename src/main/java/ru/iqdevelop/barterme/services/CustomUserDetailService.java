package ru.iqdevelop.barterme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.iqdevelop.barterme.entities.RoleEntity;
import ru.iqdevelop.barterme.entities.UserEntity;
import ru.iqdevelop.barterme.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableTransactionManagement
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userDao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(final String email) {
		UserEntity user = userDao.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());

		return buildUserForAuthentication(user, authorities);
	}

	private UserDetails buildUserForAuthentication(UserEntity user, List<GrantedAuthority> authorities) {
		return new User(user.getEmail(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(List<RoleEntity> roles) {
		List<GrantedAuthority> result = new ArrayList<>();

		for (RoleEntity userRole : roles) {
			result.add(new SimpleGrantedAuthority(userRole.getTitle()));
		}

		return result;
	}

}
