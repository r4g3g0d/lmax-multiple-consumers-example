package ro.softvision.lmaxpoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.softvision.lmaxpoc.entity.RequestEvent;
import ro.softvision.lmaxpoc.service.DisruptorProducer;
import ro.softvision.lmaxpoc.util.Computation;
import ro.softvision.lmaxpoc.util.ExecutorsProvider;

@RestController
public class DisruptorController {

    private final DisruptorProducer eventProducerService;
    private final ExecutorsProvider executorsProvider;

    @Autowired
    public DisruptorController(DisruptorProducer eventProducerService, ExecutorsProvider executorsProvider) {
        this.eventProducerService = eventProducerService;
        this.executorsProvider = executorsProvider;
    }

    @PostMapping("/requests")
    @Async
    private RequestEvent insertRequest(@RequestBody RequestEvent requestEvent) {
        Computation.runAsync(() -> eventProducerService.publishToDisruptor(requestEvent), executorsProvider.getExecutorService());
        return requestEvent;
    }
}
