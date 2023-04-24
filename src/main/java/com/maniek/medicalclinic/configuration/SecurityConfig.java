package com.maniek.medicalclinic.configuration;

import com.maniek.medicalclinic.model.Role;
import com.maniek.medicalclinic.model.entity.UserData;
import com.maniek.medicalclinic.repository.UserDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf()
                .disable()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/patients").anonymous()
                .antMatchers(HttpMethod.GET,"/patients").hasAnyRole(Role.DOCTOR.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/patients/*").hasAnyRole(Role.DOCTOR.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE,"/patients/*").hasRole(Role.DOCTOR.name())
                .antMatchers(HttpMethod.PUT,"/patients/*").hasRole(Role.PATIENT.name())
                .antMatchers(HttpMethod.PATCH, "/patients/**").hasRole(Role.PATIENT.name())
                .antMatchers(HttpMethod.POST,"/facilities/*/assign").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST,"/facilities").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST,"/doctors/*/assign").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST,"/doctors").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST,"/visits").hasAnyRole(Role.ADMIN.name(), Role.DOCTOR.name())
                .antMatchers(HttpMethod.POST,"/visits/*").hasAnyRole(Role.DOCTOR.name(), Role.PATIENT.name())
                .antMatchers(HttpMethod.GET,"/visits").hasAnyRole(Role.DOCTOR.name(), Role.ADMIN.name(), Role.PATIENT.name())
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .build();
    }
    @Bean
    public UserDetailsService userDetailsService(UserDataRepository userRepository) {
        return username -> userRepository.findByEmail(username)
                .map(this::buildUserDetailsFrom)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private UserDetails buildUserDetailsFrom(UserData user) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authority)
                .build();
    }
}
