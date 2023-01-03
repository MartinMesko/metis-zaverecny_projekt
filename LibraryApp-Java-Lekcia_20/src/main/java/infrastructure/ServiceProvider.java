package infrastructure;

import com.google.inject.Guice;
import com.google.inject.Injector;
import core.abstractions.repositories.IBookRepository;
import core.abstractions.repositories.IDvdRepository;
import core.abstractions.repositories.IMemberRepository;
import infrastructure.data.repositories.BookRepository;
import infrastructure.data.repositories.DvdRepository;
import infrastructure.data.repositories.MemberRepository;

public class ServiceProvider {

    private IDvdRepository iDvdRepository;
    private IBookRepository iBookRepository;

    private IMemberRepository iMemberRepository;

    private final Injector injector;

    public ServiceProvider() {
        injector = Guice.createInjector(new InfrastructureModule());
        injectDvdRepositoryClass();
        injectBookRepositoryClass();
        injectMemberRepositoryClass();
    }

    private void injectDvdRepositoryClass() {
        IDvdRepository dvd = injector.getInstance(DvdRepository.class);
        setIDvdRepository(dvd);
    }

    private void injectBookRepositoryClass() {
        IBookRepository book = injector.getInstance(BookRepository.class);
        setIBookRepository(book);
    }

    private void injectMemberRepositoryClass() {
        IMemberRepository member = injector.getInstance(MemberRepository.class);
        setIMemberRepository(member);
    }

    public IDvdRepository getIDvdRepository() {
        return iDvdRepository;
    }

    private void setIDvdRepository(IDvdRepository iDvdRepository) {
        this.iDvdRepository = iDvdRepository;
    }

    public IBookRepository getIBookRepository() {
        return iBookRepository;
    }

    private void setIBookRepository(IBookRepository iBookRepository) {
        this.iBookRepository = iBookRepository;
    }

    public IMemberRepository getIMemberRepository() {
        return iMemberRepository;
    }

    private void setIMemberRepository(IMemberRepository iMemberRepository) {
        this.iMemberRepository = iMemberRepository;
    }

}
