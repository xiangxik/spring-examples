package kenk.springdroolsexample.model;

import org.kie.api.definition.type.Duration;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;

@Role(Role.Type.EVENT)
@Duration("callDuration")
public class Monitoring {

    private String  originNumber;
    private String  destinationNumber;

    private long    callDuration;

    public String getOriginNumber() {
        return originNumber;
    }

    public void setOriginNumber(String originNumber) {
        this.originNumber = originNumber;
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public long getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(long callDuration) {
        this.callDuration = callDuration;
    }

    @Override
    public String toString() {
        return "Monitoring{" +
                "originNumber='" + originNumber + '\'' +
                ", destinationNumber='" + destinationNumber + '\'' +
                ", callDuration=" + callDuration +
                '}';
    }
}
