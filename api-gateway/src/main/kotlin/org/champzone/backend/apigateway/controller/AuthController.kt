//package org.champzone.backend.apigateway.controller
//
//import org.springframework.http.ResponseEntity
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.security.oauth2.jwt.JwtEncoder
//import org.springframework.security.oauth2.jwt.JwtEncoderParameters
//import org.springframework.web.bind.annotation.*
//import reactor.core.publisher.Mono
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin
//class AuthController(
//    private val authenticationManager: AuthenticationManager,
//    private val jwtEncoder: JwtEncoder  // Используем для создания токенов
//) {
//
//    @PostMapping("/login")
//    fun login(@RequestBody credentialsDto: CredentialsDto): Mono<ResponseEntity<String>> {
//        val authToken = UsernamePasswordAuthenticationToken(credentialsDto.username, credentialsDto.password)
//
//        return Mono.just(authToken)
//            .flatMap { token -> Mono.just(authenticationManager.authenticate(token)) }
//            .map { authentication -> SecurityContextHolder.getContext().authentication = authentication }
//            .map { generateJwtToken(authentication) }
//            .map { ResponseEntity.ok(it) }
//    }
//
//    private fun generateJwtToken(authentication: Authentication): String {
//        val claims = mapOf("username" to authentication.name, "roles" to authentication.authorities.map { it.authority })
//        val encoderParameters = JwtEncoderParameters.from(JwtClaimsSet.builder().claims(claims).build())
//        return jwtEncoder.encode(encoderParameters).tokenValue
//    }
//}
