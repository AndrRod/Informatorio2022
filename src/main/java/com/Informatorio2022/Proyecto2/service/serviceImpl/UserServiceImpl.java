package com.Informatorio2022.Proyecto2.service.serviceImpl;

import com.Informatorio2022.Proyecto2.dtos.UserCompleteDto;
import com.Informatorio2022.Proyecto2.dtos.UserLoginResponseDto;
import com.Informatorio2022.Proyecto2.dtos.UserPartDto;
import com.Informatorio2022.Proyecto2.dtos.mapper.UserMapper;
import com.Informatorio2022.Proyecto2.enums.Role;
import com.Informatorio2022.Proyecto2.exception.*;
import com.Informatorio2022.Proyecto2.model.User;
import com.Informatorio2022.Proyecto2.repository.UserRepository;
import com.Informatorio2022.Proyecto2.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final int SIZE_TEN = 10;
    @Autowired
    private MessageResum messageResum;
    @Autowired
    private PaginationMessage paginationMessage;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;



    private PasswordEncoder passwordEncoder;
    @Override
    public UserCompleteDto findUserById(Long id) {
        return userMapper.userEntityToCompleteDto(Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new NotFoundException(messageResum.message("user.id.not.found", String.valueOf(id))))).get());
    }

    @Override
    public UserPartDto createUser(UserPartDto userPartDto) {
        if (userRepository.existsByEmail(userPartDto.getEmail()))throw new BadRequestException(messageResum.message("email.already.exist", userPartDto.getEmail()));
        User user = userRepository.save(userMapper.dtoUserPartCreateToEntity(userPartDto));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.userEntityToPartDto(user);
    }

    @Override
    public MessagePag findPageBy10Users(int page, HttpServletRequest request) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, SIZE_TEN));
        return paginationMessage.messageInfo(userPage, userMapper.listUserCompleteToListDto(userPage.getContent()), request);
    }

    @Override
    public UserPartDto updateUser(Long id, UserPartDto dto) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new NotFoundException(messageResum.message("user.id.not.found", String.valueOf(id)))));
        if (user.get() == userRepository.findByEmail(dto.getEmail())) dto.setEmail(null);
        if (userRepository.existsByEmail(dto.getEmail()))throw new BadRequestException(messageResum.message("email.already.exist", dto.getEmail()));
        user.stream().forEach((u) -> {
            if (dto.getFirstName() != null) u.setFirstName(dto.getFirstName());
            if (dto.getLastName() != null) u.setLastName(dto.getLastName());
            if (dto.getEmail() != null) u.setEmail(dto.getEmail());
            if (dto.getPassword() != null) u.setPassword(passwordEncoder.encode(dto.getPassword()));
        });
        return userMapper.userEntityToPartDto(userRepository.save(user.get()));
    }
    @Override
    public User findUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() -> new NotFoundException(messageResum.message("user.email.not.found", email)));
    }
    @Override
    public void updateUserRol(Long idUser, String roleName) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(idUser).orElseThrow(() -> new NotFoundException(messageResum.message("user.id.not.found", String.valueOf(idUser)))));
        if(user.get().getRole().toString().equals(roleName)) throw new BadRequestException(messageResum.message("user.has.that.role", roleName));
        try {user.get().setRole(Role.valueOf(roleName));}
        catch (Exception e) {throw new NotFoundException(messageResum.message("user.rol.not.found", roleName));}
    }


    @Override
    public Authentication authenticationFilter(String email, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication autenticacionFilter = authenticationManager.authenticate(authenticationToken);
        return autenticacionFilter;
    }
    @Override
    public UserLoginResponseDto userLogin(String email, String password, HttpServletRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() -> new NotFoundException(messageResum.message("user.email.not.found", email)));
        if(!passwordEncoder.matches(password, user.getPassword())) throw new NotFoundException(messageResum.message("user.password.error",null));
        org.springframework.security.core.userdetails.User userAut = (org.springframework.security.core.userdetails.User) authenticationFilter(email, password).getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String access_token = JWT.create()
                .withSubject(userAut.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 10 minutos
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role",userAut.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String update_token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 minutos
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        return new UserLoginResponseDto(user.getEmail(), user.getRole(), access_token, update_token);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() -> new NotFoundException(messageResum.message("user.email.not.found", email)));
        Collection<SimpleGrantedAuthority> autorizaciones = Collections.singleton(new SimpleGrantedAuthority(user.getRole().getAuthority()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), autorizaciones);
    }
}