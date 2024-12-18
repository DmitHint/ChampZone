package org.champzone.backend.apigateway.config

import com.jayway.jsonpath.JsonPath
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.security.Principal


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {

    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        customConverter: Converter<Jwt, Mono<out AbstractAuthenticationToken>>
    ): SecurityWebFilterChain {
        return http {
            oauth2ResourceServer {
                jwt { jwtAuthenticationConverter = customConverter }
            }

            authorizeExchange {
                authorize("/api/v1/coach/register", permitAll)
                authorize("/api/v1/client/register", permitAll)
                authorize("/api/v1/coach/delete", permitAll)
                authorize("/api/v1/client/delete", permitAll)
                authorize("/api/v1/**", authenticated)
            }
            csrf { disable() }

            requestCache { NoOpServerRequestCache.getInstance() }
            http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())

            exceptionHandling {
                this.accessDeniedHandler = customAccessDeniedHandler()
            }
        }
    }

    private fun customAccessDeniedHandler(): ServerAccessDeniedHandler {
        return ServerAccessDeniedHandler { exchange: ServerWebExchange, ex: AccessDeniedException ->
            exchange.getPrincipal<Principal>().flatMap {
                val response = exchange.response.apply {
                    statusCode =
                        if (it is AnonymousAuthenticationToken) HttpStatus.UNAUTHORIZED else HttpStatus.FORBIDDEN
                    headers.contentType = MediaType.APPLICATION_JSON
                }
                val errorMessage = ex.message?.toByteArray() ?: "Exception occurred".toByteArray()
                val buffer = response.bufferFactory().wrap(errorMessage)
                response.writeWith(Mono.just(buffer)).doOnError { DataBufferUtils.release(buffer) }
            }
        }
    }

    @Component
    class CustomJwtAuthenticationConverter : Converter<Jwt, Mono<out AbstractAuthenticationToken>> {
        override fun convert(jwt: Jwt): Mono<out AbstractAuthenticationToken> {
            val authorities = JwtGrantedAuthoritiesConverter().convert(jwt)
            val username = JsonPath.read<String>(jwt.claims, "preferred_username")
            return Mono.just(JwtAuthenticationToken(jwt, authorities, username))
        }
    }

    internal class JwtGrantedAuthoritiesConverter : Converter<Jwt, Collection<GrantedAuthority>> {
        override fun convert(jwt: Jwt): Collection<GrantedAuthority> {
            val claimRealm: Any? =
                JsonPath.read(jwt.claims, "$.realm_access.roles")

            val roles: List<String> = when (claimRealm) {
                is ArrayList<*> -> {
                    if (claimRealm.firstOrNull() is String) {
                        claimRealm.toList() as List<String>
                    } else {
                        emptyList()
                    }
                }

                is String -> claimRealm.split(",")
                is Array<*> -> {
                    if (claimRealm.isArrayOf<String>()) {
                        claimRealm.toList() as List<String>
                    } else {
                        emptyList()
                    }
                }

                else -> emptyList()
            }
            println(roles)
            return roles
                .map { SimpleGrantedAuthority(it) }
        }
    }
}