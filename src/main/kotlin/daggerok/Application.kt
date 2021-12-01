package daggerok

import java.util.function.Consumer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.relational.core.mapping.Table
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.Many
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Table("customers")
data class Customer(

    @Id
    val id: Long? = null,
    val name: String = "",
)

interface Customers : R2dbcRepository<Customer, Long>

@Configuration
class EventProcessingConfig {

    @Bean
    fun customersEventPublisher(customersEventProcessor: Sinks.Many<Customer>) =
        java.util.function.Consumer<Customer> {
            customersEventProcessor.tryEmitNext(it)
        }

    @Bean
    fun customersEventSubscription(customersEventProcessor: Many<Customer>, customersEventStreamScheduler: Scheduler) =
        customersEventProcessor
            .asFlux()
            .publishOn(customersEventStreamScheduler)
            .subscribeOn(customersEventStreamScheduler)
            .onBackpressureBuffer(1025 * 16)

    @Bean
    fun customersEventProcessor() =
        Sinks.many()
            .multicast()
            .directBestEffort<Customer>()

    @Bean(destroyMethod = "dispose")
    fun customersEventStreamScheduler() =
        Schedulers.newSingle("customersEventStreamScheduler")
}

@Controller
class CustomersResource(
    private val customers: Customers,
    private val customersEventSubscription: Flux<Customer>,
    private val customersEventPublisher: Consumer<Customer>,
) {

    @MessageMapping("/api/v1/customers/stream")
    fun streamCustomers(): Flux<Customer> =
        Flux.concat(
            customers.findAll(),
            customersEventSubscription
        )

    @MessageMapping("/api/v1/customers/fire-and-forget")
    fun fireAndForgetCustomer(@RequestBody customer: Customer) {
        customers.save(customer)
            .subscribe { customersEventPublisher.accept(it) }
    }

    @MessageMapping("/api/v1/customers/create-customer")
    fun postCustomer(@RequestBody customer: Customer): Mono<Customer> =
        customers.save(customer)
            .doOnSuccess { customersEventPublisher.accept(it) }

    @MessageMapping("/api/v1/customers/{id}")
    fun getCustomer(@DestinationVariable id: Long): Mono<Customer> =
        customers.findById(id)

    @MessageMapping("/api/v1/customers")
    fun getAllCustomers(): Flux<Customer> =
        customers.findAll()
}
