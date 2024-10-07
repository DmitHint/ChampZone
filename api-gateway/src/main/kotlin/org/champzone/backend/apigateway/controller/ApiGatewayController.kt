//package org.champzone.backend.apigateway.controller
//
//import org.champzone.backend.apigateway.model.ClientDto
//import org.junit.jupiter.api.Tag
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.*
//import org.springframework.web.reactive.function.client.WebClient
//
//
//@RestController
//@RequestMapping("/api")
//@CrossOrigin
////@Tag(name = "API Gateway", description = "Gateway for forwarding requests to microservices.")
//class ApiGatewayController(
//    private val webClient: WebClient
//) {
//
//    @GetMapping("/client/details")
//    fun getClientDetails(@RequestHeader("Authorization") authHeader: String): Mono<ResponseEntity<ClientDto>> {
//        return webClient.get()
//            .uri("http://client-service/api/details")
//            .header("Authorization", authHeader)  // Передаем JWT токен в заголовке
//            .retrieve()
//            .bodyToMono(ClientDto::class.java)
//            .map { ResponseEntity.ok(it) }
//    }
//
//    @GetMapping("/coach/details")
//    fun getCoachDetails(@RequestHeader("Authorization") authHeader: String): Mono<ResponseEntity<CoachDto>> {
//        return webClient.get()
//            .uri("http://coach-service/api/details")
//            .header("Authorization", authHeader)
//            .retrieve()
//            .bodyToMono(CoachDto::class.java)
//            .map { ResponseEntity.ok(it) }
//    }
//}
