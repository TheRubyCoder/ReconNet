package engine.impl.ctrl;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.ItemSelectable;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;

import org.apache.commons.collections15.Factory;

import petrinetze.IArc;
import petrinetze.INode;
import edu.uci.ics.jung.visualization.MultiLayerTransformer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.LabelEditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ShearingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import engine.Engine;

public class EditingModalGraphMouseEx<V extends INode, E extends IArc> extends AbstractModalGraphMouse implements
		ModalGraphMouse, ItemSelectable {

	private Engine engine;

	protected Factory<V> vertexFactory;
	protected Factory<E> edgeFactory;
	protected EditingGraphMousePlugin<V, E> editingPlugin;
	protected LabelEditingGraphMousePlugin<V, E> labelEditingPlugin;

	protected MultiLayerTransformer basicTransformer;
	protected RenderContext<V, E> rc;
    private PetrinetPopupMousePlugin popupPlugin;

    /**
	 * create an instance with default values
	 *
	 */
	public EditingModalGraphMouseEx(Engine engine, RenderContext<V, E> rc) {
		this(engine, rc, 1.1f, 1 / 1.1f);
        setMode(Mode.PICKING);
	}

	/**
	 * create an instance with passed values
	 *
	 * @param in
	 *            override value for scale in
	 * @param out
	 *            override value for scale out
	 */
	public EditingModalGraphMouseEx(Engine engine, RenderContext<V, E> rc, float in, float out) {
		super(in, out);
		this.engine = engine;

		this.rc = rc;
		this.basicTransformer = rc.getMultiLayerTransformer();
		loadPlugins();
		setModeKeyListener(new ModeKeyAdapter(this));
	}

	/**
	 * create the plugins, and load the plugins for TRANSFORMING mode
	 *
	 */
	@Override

	protected void loadPlugins() {
		pickingPlugin = new PickingGraphMousePlugin<INode, IArc>();
		animatedPickingPlugin = new AnimatedPickingGraphMousePlugin<INode, IArc>();
		translatingPlugin = new TranslatingGraphMousePlugin(InputEvent.BUTTON1_MASK);
		scalingPlugin = new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, in, out);
		rotatingPlugin = new RotatingGraphMousePlugin();
		shearingPlugin = new ShearingGraphMousePlugin();

        labelEditingPlugin = new LabelEditingGraphMousePlugin<V, E>();
        editingPlugin = new EditingGraphMousePluginEx<V, E>(engine, vertexFactory, edgeFactory);

        popupPlugin = new PetrinetPopupMousePlugin(engine);

        add(popupPlugin);
		add(scalingPlugin);
		setMode(Mode.EDITING);
	}


	/**
	 * setter for the Mode.
	 */
	@Override
	public void setMode(Mode mode) {
		if (this.mode != mode) {
			fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED, this.mode, ItemEvent.DESELECTED));
			this.mode = mode;
			if (mode == Mode.TRANSFORMING) {
				setTransformingMode();
			} else if (mode == Mode.PICKING) {
				setPickingMode();
			} else if (mode == Mode.EDITING) {
				setEditingMode();
			} else if (mode == Mode.ANNOTATING) {
				return;
			}
			if (modeBox != null) {
				modeBox.setSelectedItem(mode);
			}
			fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED, mode, ItemEvent.SELECTED));
		}
	}

	@Override
	protected void setPickingMode() {
		remove(translatingPlugin);
		remove(rotatingPlugin);
		remove(shearingPlugin);
		remove(editingPlugin);

		add(pickingPlugin);
		add(animatedPickingPlugin);
		add(labelEditingPlugin);

	}

	@Override
	protected void setTransformingMode() {
		remove(pickingPlugin);
		remove(animatedPickingPlugin);
		remove(editingPlugin);

		add(translatingPlugin);
		add(rotatingPlugin);
		add(shearingPlugin);
		add(labelEditingPlugin);

	}

	protected void setEditingMode() {
		remove(pickingPlugin);
		remove(animatedPickingPlugin);
		remove(translatingPlugin);
		remove(rotatingPlugin);
		remove(shearingPlugin);
		remove(labelEditingPlugin);

		add(editingPlugin);

	}


	/**
	 * @return the modeBox.
	 */
	@Override
	public JComboBox getModeComboBox() {
		if (modeBox == null) {
			modeBox = new JComboBox(new Mode[] { Mode.TRANSFORMING, Mode.PICKING, Mode.EDITING });
			modeBox.addItemListener(getModeListener());
		}
		modeBox.setSelectedItem(mode);
		return modeBox;
	}


	public static class ModeKeyAdapter extends KeyAdapter {
		private char t = 't';
		private char p = 'p';
		private char e = 'e';
        private char a = 'a';

		protected ModalGraphMouse graphMouse;

		public ModeKeyAdapter(ModalGraphMouse graphMouse) {
			this.graphMouse = graphMouse;
		}

		public ModeKeyAdapter(char t, char p, char e, ModalGraphMouse graphMouse) {
			this.t = t;
			this.p = p;
			this.e = e;
			this.graphMouse = graphMouse;
		}

		@Override
		public void keyTyped(KeyEvent event) {
			char keyChar = event.getKeyChar();
			if (keyChar == t) {
				((Component) event.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				graphMouse.setMode(Mode.TRANSFORMING);
			} else if (keyChar == p) {
				((Component) event.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				graphMouse.setMode(Mode.PICKING);
			} else if (keyChar == e) {
				((Component) event.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				graphMouse.setMode(Mode.EDITING);
			}
            else if (keyChar == a) {
                graphMouse.setMode(Mode.ANNOTATING);
            }
		}
	}



}
