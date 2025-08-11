package ufes.estudos.Views;

import javax.swing.*;
import java.awt.*;

public class TelaEscolherPerfil extends JFrame implements IEscolherPerfilView {

    private JCheckBox checkVendedor;
    private JCheckBox checkComprador;
    private JButton btnLogar;

    public TelaEscolherPerfil() {
        setTitle("Escolher Perfil");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelChecks = new JPanel(new GridLayout(2, 1, 5, 5));
        checkVendedor = new JCheckBox("Vendedor");
        checkComprador = new JCheckBox("Comprador");

        checkVendedor.addActionListener(e -> {
            if (checkVendedor.isSelected()) {
                checkComprador.setSelected(false);
            }
        });

        checkComprador.addActionListener(e -> {
            if (checkComprador.isSelected()) {
                checkVendedor.setSelected(false);
            }
        });

        painelChecks.add(checkVendedor);
        painelChecks.add(checkComprador);

        btnLogar = new JButton("Logar");

        painelPrincipal.add(painelChecks, BorderLayout.CENTER);
        painelPrincipal.add(btnLogar, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    @Override
    public JButton getBtnLogar() {
        return btnLogar;
    }

    @Override
    public boolean isVendedorSelecionado() {
        return checkVendedor.isSelected();
    }

    @Override
    public boolean isCompradorSelecionado() {
        return checkComprador.isSelected();
    }

    @Override
    public void fechar() {
        dispose();
    }

    @Override
    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
