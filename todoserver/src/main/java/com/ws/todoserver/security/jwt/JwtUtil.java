package com.ws.todoserver.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	private HashMap<String, String> keyMap = new HashMap<>();

	public HashMap<String, String> getKeyMap() {
		return keyMap;
	}

	public String extractUsername(String token) throws io.jsonwebtoken.MalformedJwtException {
		String subject = extractClaim(token, Claims::getSubject);

		return subject;
	}

	public String extractToken(String token) throws io.jsonwebtoken.MalformedJwtException {
		String subject = extractClaim(token, Claims::getSubject);

		return subject;
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		String token = createToken(claims, userDetails.getUsername());
		keyMap.put(userDetails.getUsername(), token);
		return token;
	}

	private String createToken(Map<String, Object> claims, String subject) {

		String result = Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
		return result;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public void invalidateRelatedTokens(String username) {
		keyMap.remove(username);

	}

	public boolean isJWT(String jwt) {
		String[] jwtSplitted = jwt.split("\\.");
		if (jwtSplitted.length != 3) // The JWT is composed of three parts
			return false;
		try {
			String jsonFirstPart = new String(Base64.getDecoder().decode(jwtSplitted[0]));
			JSONObject firstPart = new JSONObject(jsonFirstPart); // The first part of the JWT is a JSON
			if (!firstPart.has("alg")) // The first part has the attribute "alg"
				return false;
			String jsonSecondPart = new String(Base64.getDecoder().decode(jwtSplitted[1]));
			new JSONObject(jsonSecondPart); // The first part of the JWT is a JSON
			// Put the validations you think are necessary for the data the JWT should take
			// to validate

		} catch (JSONException err) {
			return false;
		}
		return true;
	}
}