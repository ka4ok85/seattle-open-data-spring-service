package com.example.rest;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//import com.example.dto.error.ErrorDetail;
import com.example.models.JwtAuthenticationRequest;
import com.example.models.JwtAuthenticationResponse;
import com.example.security.JwtTokenUtilility;
import com.example.models.JwtUser;
import com.example.security.UserServiceImpl;
import com.example.security.WebAuthenticationDetailsSourceImpl;

@RestController("AuthenticationController")
public class AuthenticationController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserServiceImpl userDetailsService;
    
    @Autowired
    private JwtTokenUtilility jwtTokenUtilility;


	@RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(HttpServletRequest request, HttpServletResponse response, Device device) throws AuthenticationException, IOException {

		try {
			WebAuthenticationDetailsSourceImpl webAuthenticationDetailsSourceImpl = new WebAuthenticationDetailsSourceImpl();
			// read incoming request body into JwtAuthenticationRequest instance (username, password and storeId)
			JwtAuthenticationRequest jwtAuthenticationRequest = webAuthenticationDetailsSourceImpl.buildDetails(request);
			
			if (jwtAuthenticationRequest == null) {
				/*
				ErrorDetail errorDetail = new ErrorDetail();
				errorDetail.setDetail("POST Body is not a valid JSON"); // TODO: more generic message or pass exact reason here
				errorDetail.setDeveloperMessage(this.getClass().getName());
				errorDetail.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				errorDetail.setTimestamp(new Date().getTime());
				errorDetail.setTitle("Unauthorized");
				
				return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
				*/
			}
			
			
			log.info("Incoming request is {}.", jwtAuthenticationRequest);
			
			// standard Spring Authentication token
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					jwtAuthenticationRequest.getUsername(),
					jwtAuthenticationRequest.getPassword()
			);

			usernamePasswordAuthenticationToken.setDetails(jwtAuthenticationRequest);
			//userDetailsService.setStoreId(jwtAuthenticationRequest.getStoreId());

			// perform standard Spring Authentication
			Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			JwtUser jwtUser = userDetailsService.loadUserByUsername(jwtAuthenticationRequest.getUsername());
			final String token = jwtTokenUtilility.generateToken(jwtUser, device);

			final Cookie cookie = new Cookie("token", token);
			cookie.setDomain("localhost");
			cookie.setPath("/");
			//cookie.setSecure(true); // enable later
			cookie.setHttpOnly(true);
			//cookie.setMaxAge(-1);
			response.addCookie(cookie);


		    // Return the token
		    return ResponseEntity.ok(new JwtAuthenticationResponse(token));
		} catch (BadCredentialsException e) {
log.info("Authentication bad!");
			/*
			ErrorDetail errorDetail = new ErrorDetail();
			errorDetail.setDetail("Bad Credentials");
			errorDetail.setDeveloperMessage(this.getClass().getName());
			errorDetail.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			errorDetail.setTimestamp(new Date().getTime());
			errorDetail.setTitle("Unauthorized");
			
			return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
			*/
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
	}

}