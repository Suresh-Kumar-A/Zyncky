package app.web.zyncky.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.web.zyncky.model.User;
import app.web.zyncky.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User loggedInUser = userService.findByUserNameAndThrowBadCredExecption(username);
            return new CustomUserDetails(loggedInUser);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

}