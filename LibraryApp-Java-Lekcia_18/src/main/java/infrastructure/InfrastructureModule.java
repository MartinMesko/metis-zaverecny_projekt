package infrastructure;

import com.google.inject.AbstractModule;
import core.abstractions.repositories.IBookRepository;
import core.abstractions.repositories.IDvdRepository;
import infrastructure.data.repositories.BookRepository;
import infrastructure.data.repositories.DvdRepository;

public class InfrastructureModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IDvdRepository.class).to(DvdRepository.class);
        bind(IBookRepository.class).to(BookRepository.class);
    }

}
