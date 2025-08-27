package ufes.estudos.Views;

import javax.swing.*;
import java.awt.*;

public class TelaLogView extends JInternalFrame {
    private JTextArea txtLog;

    public TelaLogView() {
        super("Logs do Sistema", true, true, true, true);
        setSize(700, 500);
        setLayout(new BorderLayout());

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(txtLog), BorderLayout.CENTER);
    }

    public void setLogText(String texto) {
        this.txtLog.setText(texto);
        // Leva o scroll para o final
        this.txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }
}