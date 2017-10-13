package com.example.security.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.example.entity.User;
import com.example.security.JwtTokenUtilility;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

	@Autowired
	private JwtTokenUtilility jwtTokenUtilility;

	private String getCookieValue(HttpServletRequest req, String cookieName) {
		return Optional.ofNullable(req.getCookies())
				.map(Arrays::stream)
				.orElseGet(Stream::empty)
				.filter(c -> c.getName()
						.equals(cookieName))
				.map(Cookie::getValue)
				.findFirst()
				.orElse(null);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (SecurityContextHolder.getContext()
				.getAuthentication() == null) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

			String cookieToken = getCookieValue(httpRequest, "token");

			if (cookieToken != null) {
				String username = jwtTokenUtilility.getUsernameFromToken(cookieToken);
				String role = jwtTokenUtilility.getRoleFromToken(cookieToken);

				// valid token and not authorized yet, let's authorize request
				if (username != null && role != null && SecurityContextHolder.getContext()
						.getAuthentication() == null) {

					List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					SimpleGrantedAuthority simpleGrantedAuthority;
					if (role.equals(User.ROLE_ADMIN)) {
						simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
					} else {
						simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
					}
	
					authorities.add(simpleGrantedAuthority);
	
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
							null, authorities);
	
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					SecurityContextHolder.getContext()
							.setAuthentication(authentication);
	
				}
			}
		}

		chain.doFilter(request, response);
	}
}
