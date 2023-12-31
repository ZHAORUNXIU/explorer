package com.crypted.explorer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories("com.crypted.explorer.service")
//@EnableMongoRepositories("com.crypted.explorer.service")
class ExplorerApplication

fun main(args: Array<String>) {
	runApplication<ExplorerApplication>(*args)
}
