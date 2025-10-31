package com.example.userservice;

import static org.springframework.http.MediaType.*;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
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
import com.example.userservice.vo.RequestUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user-service")
@Slf4j
@RequiredArgsConstructor
public class UserController {
	private final Greeting greeting;
	private final Environment env;
	private final UserService userService;


	@GetMapping("/health-check")
	public String status(){
		return String.format("It's Working in User Service, port(local.server.port)=%s, port(server.port)=%s", env.getProperty("local.server.port"), env.getProperty("server.port"));
	}

	@GetMapping("/welcome")
	public String welcome(HttpServletRequest request){
		log.info("users.welcome ip: {}, {}, {}, {}", request.getRemoteAddr(),
			request.getRemoteHost(), request.getRequestURI(), request.getRequestURL());
		return greeting.getMessage();
	}

	@PostMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseUser> createUser(@Valid @RequestBody RequestUser user){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);

		UserDto userDto = mapper.map(user, UserDto.class);
		userService.createUser(userDto);

		ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
	}

	@GetMapping(value = "/users")
	public ResponseEntity<List<ResponseUser>> getUsers(){
		Iterable<UserEntity> userList = userService.getUserByAll();

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);

		List<ResponseUser> result = new ArrayList<>();
		userList.forEach(v-> result.add(mapper.map(v, ResponseUser.class)));

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping(value = "/users/{userId}")
	public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){
		UserDto userDto = userService.getUserByUserId(userId);

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);
		ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

		return ResponseEntity.status(HttpStatus.OK).body(responseUser);
	}
}
