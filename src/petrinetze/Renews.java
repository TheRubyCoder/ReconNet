package petrinetze;

import petrinetze.impl.RenewCount;
import petrinetze.impl.RenewId;
import petrinetze.impl.RenewMap;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 10.12.10
 * Time: 09:09
 * To change this template use File | Settings | File Templates.
 */
public final class Renews {

    private Renews() {}

    public static final IRenew IDENTITY = new RenewId();

    public static final IRenew COUNT = new RenewCount();

    public static final IRenew fromMap(Map<String,String> f) {
        return new RenewMap(f);
    }
}
