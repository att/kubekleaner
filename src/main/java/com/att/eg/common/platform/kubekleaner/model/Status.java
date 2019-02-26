package main.java.com.att.eg.common.platform.kubekleaner.model;

import com.att.eg.monitoring.annotations.statuscodes.IntegerValued;
import com.att.eg.monitoring.annotations.statuscodes.MonitoredStatusCode;

public enum Status implements IntegerValued {
    @MonitoredStatusCode("Success")
    OK(0),
    @MonitoredStatusCode("Something bad happened")
    ERROR(1),
    @MonitoredStatusCode("Intercepted request")
    INTERCEPTED_REQUEST(2);
    
    private int value;

    private Status(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
