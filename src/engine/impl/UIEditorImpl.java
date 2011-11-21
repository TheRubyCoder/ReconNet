package engine.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import petrinetze.Place;
import petrinetze.IRenew;
import petrinetze.Renews;
import engine.UIEditor;

/**
 * Klasse zum verwalten von RENEWS
 */
public class UIEditorImpl implements UIEditor {

    private final Map<Integer,Color> vertexPaint = new HashMap<Integer,Color>();

    private final Map<IRenew,String> renews = new IdentityHashMap<IRenew,String>();

    /**
     * Konstruktor fuer diese Klasse.
     */
    public UIEditorImpl() {
        renews.put(Renews.IDENTITY, "identity");
        renews.put(Renews.COUNT, "count");
    }

    /**
     * getPlacePaint gibt die Farbe von einem Vertex zurueck
     * @param node ist der gewaehlte Knoten
     * @return Color ist die zuruck zu gebende Farbe
     */
    @Override
    public Color getPlacePaint(Place node) {

        final Color p = vertexPaint.get(node.getId());

        return p != null ? p : new Color(Color.HSBtoRGB((node.getId() / 23f), 0.5f + (node.getId() / 127f) * 0.25f, 1f));
    }

    /**
     * setPlacePaint faerbt einen Knoten in der angegebenen Farbe
     * @param node der zu faerbende Vertex
     * @param paint die zu waehlende Farbe
     */
    @Override
    public void setPlacePaint(Place node, Color paint) {
        vertexPaint.put(node.getId(), paint);
    }

    /**
     * getColorMap gibt alle verwendeten Farben wieder
     * @return Map<Integer, String> bestehend aus einem Integer und
     * einer Farbe als String
     */
    @Override
    public Map<Integer, String> getColorMap() {
        final Map<Integer,String> colorMap = new HashMap<Integer,String>(vertexPaint.size());

        for (Map.Entry<Integer,Color> e : vertexPaint.entrySet()) {
            colorMap.put(e.getKey(), String.format("#%06d", e.getValue().getRGB()));
        }

        return colorMap;
    }

    /**
     * Gibt die Renew definition zurueck.
     * @return List<IRenew> ist eine Liste mit allen
     * Renews zurueck
     */
    @Override
    public List<IRenew> getRenews() {
        return new ArrayList<IRenew>(renews.keySet());
    }

    /**
     * displayName gibt den zugehoerigen Namen
     * zum renew zurueck.
     * @param renew zu dem der Name gefunden werden soll
     * @return String der zugehoerige String
     */
    @Override
    public String displayName(IRenew renew) {
        return renews.get(renew);
    }

    /**
     * defineRenew wird benutzt um einen Renew zu erzeugen
     * und dann in dem Set zu speichern.
     * @param name Name des Renews
     * @param f stellt als Map<String, String> ein Tupel von zwei Strings
     * dar
     * @return das erstellte Renew
     */
    @Override
    public IRenew defineRenew(String name, Map<String,String> f) {
        IRenew rnw = Renews.fromMap(f);
        renews.put(rnw, name);
        return rnw;
    }

    /**
     * removeRenew entfernt einen Renew aus dem internen Set
     * @param renew den zu entfernenden Renew
     * @return true wenn es erfolgreich war; sonst false
     */
    @Override
    public boolean removeRenew(IRenew renew) {
        if (renew == Renews.IDENTITY || renew == Renews.COUNT) {
            throw new IllegalArgumentException("Builtin renews must not be removed");
        }

        return null != renews.remove(renew);
    }
}
