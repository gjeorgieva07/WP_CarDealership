package mk.ukim.finki.wp_project.service.impl;

import mk.ukim.finki.wp_project.model.Country;
import mk.ukim.finki.wp_project.model.User;
import mk.ukim.finki.wp_project.model.exceptions.*;
import mk.ukim.finki.wp_project.repository.CountryRepository;
import mk.ukim.finki.wp_project.repository.UserRepository;
import mk.ukim.finki.wp_project.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;

    public UserServiceImpl(UserRepository userRepository, CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public User login(String username, String password){
        if (username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new InvalidArgumentsExeption();
        }
        return userRepository.findByUsernameAndPassword(username, password).orElseThrow(InvalidUsernameCredentialsException::new);
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, String country){
        if (username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new IllegalArgumentException();
        }
        if (!password.equals(repeatPassword)){
            throw new PasswordsDoNotMatchException();
        }

        if(this.userRepository.findByUsername(username).isEmpty()){
            throw new UsernameAlreadyExistsException();
        }

        Country country1 = countryRepository.findByName(country).orElseThrow(InvalidCountryNameException::new);

        User user = new User(username,password,name,surname,country1);

        return userRepository.save(user);
    }
}