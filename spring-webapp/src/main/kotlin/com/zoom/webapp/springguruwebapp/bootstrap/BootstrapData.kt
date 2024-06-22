package com.zoom.webapp.springguruwebapp.bootstrap

import com.zoom.webapp.springguruwebapp.domain.Author
import com.zoom.webapp.springguruwebapp.domain.Book
import com.zoom.webapp.springguruwebapp.domain.Publisher
import com.zoom.webapp.springguruwebapp.repositories.AuthorRepository
import com.zoom.webapp.springguruwebapp.repositories.BookRepository
import com.zoom.webapp.springguruwebapp.repositories.PublisherRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class BootstrapData(
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository,
    private val publisherRepository: PublisherRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val eric = Author(
            firstName = "Eric",
            lastName = "Effiort"
        )

        val ddd = Book(
            title = "Tame me the church",
            isbn = "1234567"
        )

        val ericSaved = authorRepository.save(eric)
        val dddSaved = bookRepository.save(ddd)

        val rod = Author(
            firstName = "Rod",
            lastName = "Johnson"
        )

        val noEJB = Book(
            title = "J2EE Development without EJB",
            isbn = "987654321"
        )

        val rodSaved = authorRepository.save(rod)
        val noEjbSaved = bookRepository.save(noEJB)

        ericSaved.books.add(dddSaved)
        rodSaved.books.add(noEjbSaved)
        dddSaved.authors.add(ericSaved)
        noEjbSaved.authors.add(rodSaved)

        val publisher = Publisher(
            name = "My Publisher",
            address = "123 Main St"
        )

        val savedPublisher = publisherRepository.save(publisher)

        dddSaved.publisher = savedPublisher
        noEjbSaved.publisher = savedPublisher

        authorRepository.save(ericSaved)
        authorRepository.save(rodSaved)
        bookRepository.save(dddSaved)
        bookRepository.save(noEjbSaved)

        println("In Bootstrap")
        println("Author Count: ${authorRepository.count()}")
        println("Book Count: ${bookRepository.count()}")
        println("Publisher Count: ${publisherRepository.count()}")
        println("Author Content: ${authorRepository.findAll()}")
        println("Book Content: ${bookRepository.findAll()}")
    }
}