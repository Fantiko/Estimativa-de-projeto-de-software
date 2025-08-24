package ufes.estudos.Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaNegociacao extends JDialog implements INegociacaoView {
    private JLabel lblNomeItem;
    private JLabel lblPrecoOriginal;
    private JLabel lblFaixaDeOferta;
    private JTextField txtValorOferta;
    private JButton btnEnviarOferta;

    public TelaNegociacao(Frame owner) {
        super(owner, "Fazer Oferta", true); // JDialog modal
        setSize(400, 250);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblNomeItem = new JLabel();
        lblNomeItem.setFont(new Font("Roboto", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painelPrincipal.add(lblNomeItem, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        painelPrincipal.add(new JLabel("Preço do Anúncio:"), gbc);
        gbc.gridx = 1;
        lblPrecoOriginal = new JLabel();
        painelPrincipal.add(lblPrecoOriginal, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        painelPrincipal.add(new JLabel("Sua oferta pode ser entre:"), gbc);
        gbc.gridx = 1;
        lblFaixaDeOferta = new JLabel();
        lblFaixaDeOferta.setFont(new Font("Roboto", Font.ITALIC, 12));
        painelPrincipal.add(lblFaixaDeOferta, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        painelPrincipal.add(new JLabel("Sua Oferta (R$):"), gbc);
        gbc.gridx = 1;
        txtValorOferta = new JTextField();
        painelPrincipal.add(txtValorOferta, gbc);

        btnEnviarOferta = new JButton("Enviar Oferta");
        gbc.gridy = 4; gbc.gridx = 1; gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(btnEnviarOferta, gbc);

        add(painelPrincipal, BorderLayout.CENTER);
    }

    @Override public void setNomeItem(String nome) { lblNomeItem.setText(nome); }
    @Override public void setPrecoOriginal(String preco) { lblPrecoOriginal.setText(preco); }
    @Override public void setFaixaDeOferta(String faixa) { lblFaixaDeOferta.setText(faixa); }
    @Override public String getValorOferta() { return txtValorOferta.getText(); }
    @Override public void setEnviarOfertaListener(ActionListener listener) { btnEnviarOferta.addActionListener(listener); }
    @Override public void exibirMensagem(String mensagem) { JOptionPane.showMessageDialog(this, mensagem); }
    @Override public void fechar() { dispose(); }
}