package ufes.estudos.Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaAdicionarAnuncio extends JInternalFrame implements IAdicionarAnuncioView {

    private JTextField txtIdc, txtSubcategoria, txtTamanho, txtCor, txtMassa, txtPreco;
    private JComboBox<String> cbTipoPeca, cbComposicao, cbEstado, cbDefeitos;
    private JButton btnSalvar, btnCancelar;

    public TelaAdicionarAnuncio() {
        super("Adicionar Novo Anúncio", true, true, true, true);
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Painel de campos
        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 0: ID-C
        gbc.gridx = 0; gbc.gridy = 0;
        painelCampos.add(new JLabel("ID-C (deixe em branco para gerar):"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtIdc = new JTextField();
        painelCampos.add(txtIdc, gbc);
        gbc.weightx = 0;

        // Linha 1: Tipo de Peça e Subcategoria
        gbc.gridx = 0; gbc.gridy = 1;
        painelCampos.add(new JLabel("Tipo de Peça:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        cbTipoPeca = new JComboBox<>(new String[]{"", "Vestuário", "Calçados", "Bolsas e Mochilas", "Bijuterias e Acessórios"});
        painelCampos.add(cbTipoPeca, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painelCampos.add(new JLabel("Subcategoria:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        txtSubcategoria = new JTextField();
        painelCampos.add(txtSubcategoria, gbc);

        // Linha 2: Tamanho e Cor
        gbc.gridx = 0; gbc.gridy = 3;
        painelCampos.add(new JLabel("Tamanho:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        txtTamanho = new JTextField();
        painelCampos.add(txtTamanho, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        painelCampos.add(new JLabel("Cor Predominante:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        txtCor = new JTextField();
        painelCampos.add(txtCor, gbc);

        // Linha 3: Composição e Massa
        gbc.gridx = 0; gbc.gridy = 5;
        painelCampos.add(new JLabel("Composição Principal:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        cbComposicao = new JComboBox<>(new String[]{"", "Algodão", "Poliéster", "Couro", "Metal", "Plástico de base fóssil", "Outros"});
        painelCampos.add(cbComposicao, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        painelCampos.add(new JLabel("Massa Estimada (kg):"), gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        txtMassa = new JTextField();
        painelCampos.add(txtMassa, gbc);

        // Linha 4: Estado e Defeitos
        gbc.gridx = 0; gbc.gridy = 7;
        painelCampos.add(new JLabel("Estado de Conservação:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7;
        cbEstado = new JComboBox<>(new String[]{"", "Novo", "Seminovo", "Usado"});
        painelCampos.add(cbEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        painelCampos.add(new JLabel("Defeito Principal:"), gbc);
        gbc.gridx = 1; gbc.gridy = 8;
        cbDefeitos = new JComboBox<>();
        painelCampos.add(cbDefeitos, gbc);

        // Linha 5: Preço
        gbc.gridx = 0; gbc.gridy = 9;
        painelCampos.add(new JLabel("Preço Base (R$):"), gbc);
        gbc.gridx = 1; gbc.gridy = 9;
        txtPreco = new JTextField();
        painelCampos.add(txtPreco, gbc);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar Anúncio");
        btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnSalvar);

        painelPrincipal.add(painelCampos, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        add(painelPrincipal);
    }

    @Override public String getIdc() { return txtIdc.getText(); }
    @Override public String getTipoPeca() { return (String) cbTipoPeca.getSelectedItem(); }
    @Override public String getSubcategoria() { return txtSubcategoria.getText(); }
    @Override public String getTamanho() { return txtTamanho.getText(); }
    @Override public String getCor() { return txtCor.getText(); }
    @Override public String getComposicao() { return (String) cbComposicao.getSelectedItem(); }
    @Override public String getMassa() { return txtMassa.getText(); }
    @Override public String getEstado() { return (String) cbEstado.getSelectedItem(); }
    @Override public String getDefeito() { return (String) cbDefeitos.getSelectedItem(); }
    @Override public String getPreco() { return txtPreco.getText(); }

    @Override
    public void setDefeitosOptions(String[] defeitos) {
        cbDefeitos.setModel(new DefaultComboBoxModel<>(defeitos));
    }

    @Override public void setTipoPecaListener(ActionListener listener) { cbTipoPeca.addActionListener(listener); }
    @Override public void setSalvarListener(ActionListener listener) { btnSalvar.addActionListener(listener); }
    @Override public void setCancelarListener(ActionListener listener) { btnCancelar.addActionListener(listener); }
    @Override public void exibirMensagem(String mensagem) { JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE); }
    @Override public void fechar() { dispose(); }
}