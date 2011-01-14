package engine.impl;

import engine.UIEditor;
import petrinetze.INode;
import petrinetze.IPlace;
import petrinetze.IRenew;
import petrinetze.Renews;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 13.01.11
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */
public class UIEditorImpl implements UIEditor {

    private final Map<Integer,Color> vertexPaint = new HashMap<Integer,Color>();

    private final Map<IRenew,String> renews = new IdentityHashMap<IRenew,String>();

    public UIEditorImpl() {
        renews.put(Renews.IDENTITY, "identity");
        renews.put(Renews.COUNT, "count");
    }

    @Override
    public Color getPlacePaint(IPlace node) {

        final Color p = vertexPaint.get(node.getId());

        return p != null ? p : new Color(Color.HSBtoRGB((node.getId() / 23f), 0.5f + (node.getId() / 127f) * 0.25f, 1f));
    }

    @Override
    public void setPlacePaint(IPlace node, Color paint) {
        vertexPaint.put(node.getId(), paint);
    }

    @Override
    public Map<Integer, String> getColorMap() {
        final Map<Integer,String> colorMap = new HashMap<Integer,String>(vertexPaint.size());

        for (Map.Entry<Integer,Color> e : vertexPaint.entrySet()) {
            colorMap.put(e.getKey(), String.format("#%06d", e.getValue().getRGB()));
        }

        return colorMap;
    }

    @Override
    public List<IRenew> getRenews() {
        return new ArrayList<IRenew>(renews.keySet());
    }

    @Override
    public String displayName(IRenew renew) {
        return renews.get(renew);
    }

    @Override
    public IRenew defineRenew(String name, Map<String,String> f) {
        IRenew rnw = Renews.fromMap(f);
        renews.put(rnw, name);
        return rnw;
    }

    @Override
    public boolean removeRenew(IRenew renew) {
        if (renew == Renews.IDENTITY || renew == Renews.COUNT) {
            throw new IllegalArgumentException("Builtin renews must not be removed");
        }

        return null != renews.remove(renew);
    }
}
