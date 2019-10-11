package ro.softvision.lmaxpoc.service;

import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Service;
import ro.softvision.lmaxpoc.entity.RingBufferEvent;

@Service
public class RingBufferEventFactory implements EventFactory<RingBufferEvent> {
    @Override
    public RingBufferEvent newInstance() {
        return new RingBufferEvent();
    }
}
