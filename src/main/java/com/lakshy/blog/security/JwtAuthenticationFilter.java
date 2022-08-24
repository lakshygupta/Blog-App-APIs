package com.lakshy.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	// this is called when we hit any api request
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// 1. get token from request from Authorization key in header
		String requestToken = request.getHeader("Authorization");
		
		System.out.println(requestToken);
		
		String username = null;
		String token = null;
		
		if(requestToken != null && requestToken.startsWith("Bearer")) 
		{
			token = requestToken.substring(7); // after bearer fetch original token
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
				
			}
			catch(IllegalArgumentException ex){
				System.out.println("Unable to get JWT Token");
			}
			catch(ExpiredJwtException ex) {
				System.out.println("JWT Token has expired");
			}
			catch(MalformedJwtException ex) {
				System.out.println("Invalid JWT");
			}
			
		}
		else 
		{
			System.out.println("JWT Token is null or doesnot begin with bearer");
		}
		
		// 2. we got the JWT token, Now validate the JWT Token
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// we have username and there is no security Authentication being applied over the apis
			// then only we need to validate the JWT Token
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				// all fine - can authenticate here
				// we know spring security needs a username/password to get api authorized so creating this using details we have for user 
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				//set spring security
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			} else {
				// validation of token failed
				System.out.println("Invalid JWT Token");
			}
			
		}else {
			// a security is already being defined in current application (basic db security or some other)
			// or the username is null for this JWT token
			System.out.println("username is null or context is not null");
		}
		
		// if jwt token is valid then above conditions might have set the authentication context to authorize the api
		// otherwise we won't be able to access the API -> will hit the JwtAuthenticationEntryPoint > commence method
		// request will be forwarded in both the cases using below function
		filterChain.doFilter(request, response);
		
	}

}
