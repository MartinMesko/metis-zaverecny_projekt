package core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import core.abstractions.services.IQueueService;
import core.abstractions.services.IRentalEntryService;
import core.service.QueueService;
import core.service.RentalEntryService;

public class CoreServiceProvider {

    private IRentalEntryService iRentalEntryService;
    private IQueueService iQueueService;

    private final Injector injector;


    public CoreServiceProvider() {
        injector = Guice.createInjector(new CoreModule());
        injectRentalEntryService();
        injectQueueService();
    }

    private void injectRentalEntryService() {
        IRentalEntryService res = injector.getInstance(RentalEntryService.class);
        setIRentalEntryService(res);
    }

    private void injectQueueService() {
        IQueueService qs = injector.getInstance(QueueService.class);
        setIQueueService(qs);
    }

    public IRentalEntryService getIRentalEntryService() {
        return iRentalEntryService;
    }

    private void setIRentalEntryService(IRentalEntryService iRentalEntryService) {
        this.iRentalEntryService = iRentalEntryService;
    }

    public IQueueService getIQueueService() {
        return iQueueService;
    }

    private void setIQueueService(IQueueService iQueueService) {
        this.iQueueService = iQueueService;
    }

}
