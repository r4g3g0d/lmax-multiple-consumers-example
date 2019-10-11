package ro.softvision.lmaxpoc.service;

import com.lmax.disruptor.EventTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.softvision.lmaxpoc.entity.RequestEvent;
import ro.softvision.lmaxpoc.entity.RingBufferEvent;

@Service
public class DisruptorProducer implements EventTranslator<RingBufferEvent> {

    private DisruptorService disruptorService;
    private RequestEvent requestEvent;

    @Autowired
    public DisruptorProducer(DisruptorService disruptorService) {
        this.disruptorService = disruptorService;
    }

    public void publishToDisruptor(RequestEvent requestEvent) {
        this.requestEvent = requestEvent;
        disruptorService.getDisruptor().publishEvent(this);
    }

    @Override
    public void translateTo(RingBufferEvent ringBufferEvent, long l) {
        ringBufferEvent.setData(this.requestEvent.getData());
        ringBufferEvent.setId(this.requestEvent.getId());
    }
}
