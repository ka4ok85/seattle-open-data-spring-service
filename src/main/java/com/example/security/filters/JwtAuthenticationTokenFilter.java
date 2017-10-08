package com.example.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.security.JwtTokenUtilility;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtilility jwtTokenUtilility;


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		log.info("Incoming request is {}.", request);

		//String authToken = httpRequest.getHeader(this.tokenHeader);
		//log.info("ncoming Raw Authorization Header Value {}.", authToken);
/*
		String username = jwtTokenUtilility.getUsernameFromToken(authToken);
		log.info("JWT login is {}.", username);
		Long store = null;
		if (username != null) {
			store = jwtTokenUtilility.getStoreFromToken(authToken);
			log.info("JWT Store is {}.", store);
		}

		if (username != null && store != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (jwtTokenUtilility.validateToken(authToken, userDetails)) {
				log.info("User Details is {}.", userDetails);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
		}
*/
		chain.doFilter(request, response);
	}
}
