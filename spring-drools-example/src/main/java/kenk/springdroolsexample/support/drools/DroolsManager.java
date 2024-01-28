package kenk.springdroolsexample.support.drools;

public interface DroolsManager {

    void refresh();

    void fireRule(String kieBaseName, Object... facts);

    void fireRule(Object... facts);
}
