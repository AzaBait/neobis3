package com.example.neobis.service.impl;

import com.example.neobis.entity.Role;
import com.example.neobis.entity.User;
import com.example.neobis.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findUserByEmail(email);
        if (user.isPresent()) {
            Collection<? extends GrantedAuthority> authorities = mapRolesToAuthorities(user.get().getRoles());
            return new org.springframework.security.core.userdetails.User(
                    user.get().getEmail(), user.get().getPassword(), mapRolesToAuthorities(user.get().getRoles()));
        }
        throw new UsernameNotFoundException(String.format("User with '%s' not found!", email));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
