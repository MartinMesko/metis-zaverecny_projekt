package infrastructure;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import core.abstractions.repositories.IBookRepository;
import core.abstractions.repositories.IDvdRepository;
import core.abstractions.repositories.IRentalEntryRepository;
import infrastructure.data.repositories.BookRepository;
import infrastructure.data.repositories.DvdRepository;
import infrastructure.data.repositories.RentalEntryRepository;

public class InfrastructureModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IDvdRepository.class).annotatedWith(Names.named("dvd")).to(DvdRepository.class);
        bind(IBookRepository.class).annotatedWith(Names.named("book")).to(BookRepository.class);
        bind(IRentalEntryRepository.class).annotatedWith(Names.named("rentalRepo")).to(RentalEntryRepository.class);
    }

}
