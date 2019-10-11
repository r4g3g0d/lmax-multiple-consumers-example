package ro.softvision.lmaxpoc.service;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.softvision.lmaxpoc.entity.RingBufferEvent;

@Service
public class DisruptorService {

    private RingBufferEventFactory ringBufferEventFactory;
    private DisruptorListener disruptorListener;
    private DisruptorListenerForLogging disruptorListenerForLogging;
    private DisruptorListenerForComputation disruptorListenerForComputation;

    private Disruptor<RingBufferEvent> disruptor;
    private int ringBufferSize = 1024;



    @Autowired
    public DisruptorService(RingBufferEventFactory ringBufferEventFactory, DisruptorListener disruptorListener,
            DisruptorListenerForLogging disruptorListenerForLogging, DisruptorListenerForComputation disruptorListenerForComputation) {
        this.ringBufferEventFactory = ringBufferEventFactory;
        this.disruptorListener = disruptorListener;
        this.disruptorListenerForLogging = disruptorListenerForLogging;
        this.disruptorListenerForComputation = disruptorListenerForComputation;
        constructDisruptor();
    }

    private void constructDisruptor() {
        disruptor = new Disruptor<>(ringBufferEventFactory, ringBufferSize, DaemonThreadFactory.INSTANCE);
        disruptorListener.setNoOfConsumers(3);
        disruptorListener.setOrdinal(0);

        disruptorListenerForLogging.setNoOfConsumers(3);
        disruptorListenerForLogging.setOrdinal(1);

        disruptorListenerForComputation.setNoOfConsumers(3);
        disruptorListenerForComputation.setOrdinal(2);

        disruptor.handleEventsWith(disruptorListener);
        disruptor.handleEventsWith(disruptorListenerForLogging);
        disruptor.handleEventsWith(disruptorListenerForComputation);
        //disruptor.after(disruptorListener).handleEventsWith(disruptorListenerForLogging);
        disruptor.start();
    }

    public Disruptor<RingBufferEvent> getDisruptor() {
        return disruptor;
    }


}
