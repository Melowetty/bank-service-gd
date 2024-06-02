package ru.melowetty.bankservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BankServiceApplication

fun main(args: Array<String>) {
	runApplication<BankServiceApplication>(*args)
}
