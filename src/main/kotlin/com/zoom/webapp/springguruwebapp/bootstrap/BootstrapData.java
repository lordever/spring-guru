package com.zoom.webapp.springguruwebapp.bootstrap;

import com.zoom.webapp.springguruwebapp.domain.Author;
import com.zoom.webapp.springguruwebapp.domain.Book;
import com.zoom.webapp.springguruwebapp.domain.Publisher;
import com.zoom.webapp.springguruwebapp.repositories.AuthorRepository;
import com.zoom.webapp.springguruwebapp.repositories.BookRepository;
import com.zoom.webapp.springguruwebapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Effiort");

        Book ddd = new Book();
        ddd.setTitle("Take me the church");
        ddd.setIsbn("1234567");

        Author ericSaved = authorRepository.save(eric);
        Book dddSaved = bookRepository.save(ddd);

        Author rod = new Author();
        rod.setFirstName("Rod");
        rod.setLastName("Johnson");

        Book noEJB = new Book();
        noEJB.setTitle("J2EE Development without EJB");
        noEJB.setIsbn("987654321");

        Author rodSaved = authorRepository.save(rod);
        Book noEjbSaved = bookRepository.save(noEJB);

        ericSaved.getBooks().add(dddSaved);
        rodSaved.getBooks().add(noEjbSaved);

        authorRepository.save(ericSaved);
        authorRepository.save(rodSaved);

        System.out.println("In Bootstrap");
        System.out.println("Author Count:" + authorRepository.count());
        System.out.println("Book Count:" + bookRepository.count());
        System.out.println("Author Content:" + authorRepository.findAll());
        System.out.println("Book Content:" + bookRepository.findAll());

        Publisher publisher = new Publisher();
        publisher.setName("My Publisher");
        publisher.setAddress("123 Main St");

        publisherRepository.save(publisher);

        System.out.println("Publisher Count:" + publisherRepository.count());
    }
}
