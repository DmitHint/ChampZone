package org.champzone.backend.apigateway.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

//@Configuration
//class ApiGatewayConfig {
//
//    @Bean
//    fun routes(builder: RouteLocatorBuilder): RouteLocator {
//        return builder.routes()
//            .route("coach_service") { r ->
//                r.path("/coach/**")
//                    .uri("http://coach-service:8082")
////            }.route("coach_service") { r ->
////                r.path("/training/**")
////                    .uri("http://coach-service:8080")
//            }
//            .build()
//    }
//}
