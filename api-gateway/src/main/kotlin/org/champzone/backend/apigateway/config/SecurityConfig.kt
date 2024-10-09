package org.champzone.backend.apigateway.config

//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.convert.converter.Converter
//import org.springframework.security.authentication.AbstractAuthenticationToken
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.core.authority.SimpleGrantedAuthority
//import org.springframework.security.oauth2.jwt.Jwt
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
//import org.springframework.security.web.SecurityFilterChain
//
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//class SecurityConfig {
//
//    interface AuthoritiesConverter : Converter<Map<String, Any>, Collection<GrantedAuthority>>
//
//    class RealmRolesAuthoritiesConverter : AuthoritiesConverter {
//        override fun convert(claims: Map<String, Any>): Collection<GrantedAuthority> {
//            val realmAccess = claims["realm_access"] as? Map<*, *>
//            val roles = realmAccess?.get("roles") as? List<String>
//            return roles?.asSequence()?.map { SimpleGrantedAuthority(it) }?.toList() ?: emptyList()
//        }
//    }
//
//    @Bean
//    fun realmRolesAuthoritiesConverter(): AuthoritiesConverter = RealmRolesAuthoritiesConverter()
//
//    @Bean
//    fun authenticationConverter(authoritiesConverter: AuthoritiesConverter): JwtAuthenticationConverter {
//        return JwtAuthenticationConverter().apply {
//            setJwtGrantedAuthoritiesConverter { jwt: Jwt ->
//                authoritiesConverter.convert(jwt.claims)
//            }
//        }
//    }
//
//    @Bean
//    fun resourceServerSecurityFilterChain(
//        http: HttpSecurity,
//        jwtAuthenticationConverter: Converter<Jwt, AbstractAuthenticationToken>
//    ): SecurityFilterChain {
//        http.oauth2ResourceServer { resourceServer ->
//            resourceServer.jwt { jwtDecoder ->
//                jwtDecoder.jwtAuthenticationConverter(jwtAuthenticationConverter)
//            }
//        }
//
//        http.sessionManagement { sessions ->
//            sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        }.csrf { csrf ->
//            csrf.disable()
//        }
//
//        http.authorizeHttpRequests { requests ->
//            requests.requestMatchers("/*").permitAll()
////            requests.requestMatchers("/*").authenticated()
//            requests.anyRequest().denyAll()
//        }
//
//        return http.build()
//    }
//}
