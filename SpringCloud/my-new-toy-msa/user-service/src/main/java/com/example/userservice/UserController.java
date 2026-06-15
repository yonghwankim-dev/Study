package com.example.userservice;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.vo.ResponseUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class UserController {
	private final Environment environment;
	private final Greeting greeting;
	private final UserService userService;

	public UserController(Environment environment, Greeting greeting, UserService userService) {
		this.environment = environment;
		this.greeting = greeting;
		this.userService = userService;
	}

	@GetMapping("/health-check")
	public String status(){
		return String.format("It's Working in User Service, "
				+ "port(local.server.port)=%s, "
				+ "port(server.port)=%s "
				+ "welcome message=%s"
				+ "gateway.ip(env)= %s "
				+ "token secret key= %s "
				+ "token expiration  time= %s ",
			environment.getProperty("local.server.port"),
			environment.getProperty("server.port"),
			environment.getProperty("greeting.message"),
			environment.getProperty("gateway.ip"),
			environment.getProperty("token.secret"),
			environment.getProperty("token.expiration-time")
		);
	}

	@GetMapping("/welcome")
	public String welcome(HttpServletRequest request){
		log.info("users.welcome ip: {}, {}, {}, {}", request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), request.getRequestURL());
		return greeting.getMessage();
	}

	@PostMapping("/users")
	public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = modelMapper.map(user, UserDto.class);

		userService.createUser(userDto);
		ResponseUser responseUser = modelMapper.map(userDto, ResponseUser.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
	}

	@GetMapping("/users")
	public ResponseEntity<List<ResponseUser>> getUsers(){
		Iterable<UserEntity> users = userService.getUserByAll();

		List<ResponseUser> result = new ArrayList<>();
		users.forEach(u->result.add(new ModelMapper().map(u, ResponseUser.class)));
		return ResponseEntity.ok(result);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){
		UserDto userDto = userService.getUserByUserId(userId);
		ResponseUser responseUser = new ModelMapper().map(userDto, ResponseUser.class);
		return ResponseEntity.ok(responseUser);
	}

}
