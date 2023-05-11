package com.slm.springlibrarymanagement.jwt;

import com.slm.springlibrarymanagement.model.dto.UserDto;
import com.slm.springlibrarymanagement.model.entities.User;
import com.slm.springlibrarymanagement.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.getUserByUsername(username);

        if (user != null) {
            Set<GrantedAuthority> granted  = new HashSet<>();
             String[] auth = user.getAuthorities().replace("[","").replace("]","").split(",");
            Arrays.stream(auth).forEach(a -> granted.add(new SimpleGrantedAuthority(a)));
            return new UserDto(user.getUsername(), user.getPassword(),granted);
        } else {
            throw new UsernameNotFoundException("User has not been found in the db" + username);
        }
    }
}
