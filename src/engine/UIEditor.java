package engine;

import petrinet.INode;
import petrinet.IRenew;
import petrinet.Place;

import java.awt.Color;
import java.util.List;
import java.util.Map;

public interface UIEditor {

    Color getPlacePaint(Place node);

    void setPlacePaint(Place node, Color paint);

    Map<Integer,String> getColorMap();

    List<IRenew> getRenews();

    String displayName(IRenew renew);

    IRenew defineRenew(String name, Map<String,String> f);

    boolean removeRenew(IRenew renew);
}
