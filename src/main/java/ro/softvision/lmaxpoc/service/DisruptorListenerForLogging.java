package ro.softvision.lmaxpoc.service;

import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Service;
import ro.softvision.lmaxpoc.entity.RingBufferEvent;

import java.time.LocalDateTime;

@Service
public class DisruptorListenerForLogging implements EventHandler<RingBufferEvent> {

    private long ordinal;
    private long noOfConsumers;

  /*  public DisruptorListenerForLogging(long ordinal, long noOfConsumers) {
        this.ordinal = ordinal;
        this.noOfConsumers = noOfConsumers;
    }*/

    @Override
    public void onEvent(RingBufferEvent ringBufferEvent, long l, boolean b) throws Exception {
        if ((l % noOfConsumers) == ordinal) {
            System.out.println(
                    "[DisruptorListenerForLogging] - " + ringBufferEvent.getId() + " Time : " + LocalDateTime
                            .now());
        }
    }

    public void setOrdinal(long ordinal) {
        this.ordinal = ordinal;
    }

    public void setNoOfConsumers(long noOfConsumers) {
        this.noOfConsumers = noOfConsumers;
    }
}
