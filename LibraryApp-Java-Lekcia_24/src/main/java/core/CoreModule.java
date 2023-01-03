package core;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import core.abstractions.repositories.*;
import core.abstractions.services.IQueueService;
import core.abstractions.services.IRentalEntryService;
import core.service.QueueService;
import core.service.RentalEntryService;
import infrastructure.data.repositories.*;

public class CoreModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IRentalEntryService.class).annotatedWith(Names.named("rentalEntry")).to(RentalEntryService.class);
        bind(IQueueService.class).annotatedWith(Names.named("queue")).to(QueueService.class);

        bind(IDvdRepository.class).annotatedWith(Names.named("dvd")).to(DvdRepository.class);
        bind(IBookRepository.class).annotatedWith(Names.named("book")).to(BookRepository.class);
        bind(IMemberRepository.class).annotatedWith(Names.named("member")).to(MemberRepository.class);
        bind(IRentalEntryRepository.class).annotatedWith(Names.named("rentalRepo")).to(RentalEntryRepository.class);
        bind(IQueueItemRepository.class).annotatedWith(Names.named("queueItem")).to(QueueItemRepository.class);
    }

}
