package engine;

import petrinetze.INode;
import petrinetze.IPlace;
import petrinetze.IRenew;

import java.awt.Color;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 13.01.11
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
public interface UIEditor {

    Color getPlacePaint(IPlace node);

    void setPlacePaint(IPlace node, Color paint);

    Map<Integer,String> getColorMap();

    List<IRenew> getRenews();

    String displayName(IRenew renew);

    IRenew defineRenew(String name, Map<String,String> f);

    boolean removeRenew(IRenew renew);
}
