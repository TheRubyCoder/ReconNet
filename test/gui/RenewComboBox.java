package gui;

import petrinetze.IRenew;
import petrinetze.Renews;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 13.01.11
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
public class RenewComboBox extends JComboBox {

    public RenewComboBox(IRenew items[]) {
        super(items);
    }

    public RenewComboBox() {
        this(new IRenew[]{
            Renews.IDENTITY,
            Renews.COUNT
        });
    }
}
