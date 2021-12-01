package daggerok

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.relational.core.mapping.Table
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody

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

@Controller
class CustomersResource(private val customers: Customers) {

    @MessageMapping("/api/v1/customers/create-customer")
    fun postCustomer(@RequestBody customer: Customer) =
        customers.save(customer)

    @MessageMapping("/api/v1/customers/{id}")
    fun getCustomer(@DestinationVariable id: Long) =
        customers.findById(id)

    @MessageMapping("/api/v1/customers")
    fun getAllCustomers() =
        customers.findAll()
}
