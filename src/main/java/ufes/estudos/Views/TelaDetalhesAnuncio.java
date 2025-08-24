package ufes.estudos.Views;

import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.Item.Material;
import ufes.estudos.Model.Item.Defeito;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaDetalhesAnuncio extends JInternalFrame implements IDetalhesAnuncioView {

    private JTextField txtIdc, txtSubcategoria, txtTamanho, txtCor, txtMassa, txtPreco;
    private JComboBox<String> cbTipoPeca, cbComposicao, cbEstado, cbDefeitos;
    private JButton btnEditar, btnSalvar, btnDeletar, btnCancelar;
    private String idcOriginal; // Guarda o ID original para criar o objeto Item

    public TelaDetalhesAnuncio() {
        super("Detalhes do Anúncio", true, true, true, true);
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos (semelhante à tela de adicionar, mas serão preenchidos)
        gbc.gridx = 0; gbc.gridy = 0; painelCampos.add(new JLabel("ID-C:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; txtIdc = new JTextField(); painelCampos.add(txtIdc, gbc);
        gbc.gridx = 0; gbc.gridy = 1; painelCampos.add(new JLabel("Tipo de Peça:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; cbTipoPeca = new JComboBox<>(new String[]{"Vestuário", "Calçados", "Bolsas e Mochilas", "Bijuterias e Acessórios"}); painelCampos.add(cbTipoPeca, gbc);
        gbc.gridx = 0; gbc.gridy = 2; painelCampos.add(new JLabel("Subcategoria:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; txtSubcategoria = new JTextField(); painelCampos.add(txtSubcategoria, gbc);
        gbc.gridx = 0; gbc.gridy = 3; painelCampos.add(new JLabel("Tamanho:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; txtTamanho = new JTextField(); painelCampos.add(txtTamanho, gbc);
        gbc.gridx = 0; gbc.gridy = 4; painelCampos.add(new JLabel("Cor Predominante:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; txtCor = new JTextField(); painelCampos.add(txtCor, gbc);
        gbc.gridx = 0; gbc.gridy = 5; painelCampos.add(new JLabel("Composição Principal:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; cbComposicao = new JComboBox<>(new String[]{"Algodão", "Poliéster", "Couro", "Metal", "Plástico de base fóssil", "Outros"}); painelCampos.add(cbComposicao, gbc);
        gbc.gridx = 0; gbc.gridy = 6; painelCampos.add(new JLabel("Massa Estimada (kg):"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; txtMassa = new JTextField(); painelCampos.add(txtMassa, gbc);
        gbc.gridx = 0; gbc.gridy = 7; painelCampos.add(new JLabel("Estado de Conservação:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; cbEstado = new JComboBox<>(new String[]{"Novo", "Seminovo", "Usado"}); painelCampos.add(cbEstado, gbc);
        gbc.gridx = 0; gbc.gridy = 8; painelCampos.add(new JLabel("Defeito Principal:"), gbc);
        gbc.gridx = 1; gbc.gridy = 8; cbDefeitos = new JComboBox<>(new String[]{"Rasgo estruturante", "Sola sem relevo funcional", "Alça reparada", "Oxidação visível"}); painelCampos.add(cbDefeitos, gbc);
        gbc.gridx = 0; gbc.gridy = 9; painelCampos.add(new JLabel("Preço Base (R$):"), gbc);
        gbc.gridx = 1; gbc.gridy = 9; txtPreco = new JTextField(); painelCampos.add(txtPreco, gbc);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEditar = new JButton("Editar");
        btnSalvar = new JButton("Salvar");
        btnDeletar = new JButton("Deletar");
        btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnDeletar);
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar);

        painelPrincipal.add(painelCampos, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        add(painelPrincipal);

        // Estado inicial
        setCamposEditaveis(false);
        setModoEdicao(false);
    }

    @Override
    public void exibirAnuncio(Item item) {
        this.idcOriginal = item.getIdentificadorCircular();
        txtIdc.setText(item.getIdentificadorCircular());
        cbTipoPeca.setSelectedItem(item.getTipoPeca());
        txtSubcategoria.setText(item.getSubcategoria());
        txtTamanho.setText(item.getTamanho());
        txtCor.setText(item.getCorPredominante());
        cbComposicao.setSelectedItem(item.getMaterial().getNome());
        txtMassa.setText(String.valueOf(item.getMassaEstimada()));
        cbEstado.setSelectedItem(item.getEstadoConservacao());
        cbDefeitos.setSelectedItem(item.getDefeito().getDefeito());
        txtPreco.setText(String.format("%.2f", item.getPrecoBase()));
    }

    @Override
    public void setCamposEditaveis(boolean editavel) {
        // ID-C nunca é editável
        txtIdc.setEditable(false);
        // Outros campos
        cbTipoPeca.setEnabled(editavel);
        txtSubcategoria.setEditable(editavel);
        txtTamanho.setEditable(editavel);
        txtCor.setEditable(editavel);
        cbComposicao.setEnabled(editavel);
        txtMassa.setEditable(editavel);
        cbEstado.setEnabled(editavel);
        cbDefeitos.setEnabled(editavel);
        txtPreco.setEditable(editavel);
    }

    @Override
    public void setModoEdicao(boolean modoEdicao) {
        btnEditar.setVisible(!modoEdicao);
        btnDeletar.setVisible(!modoEdicao);
        btnSalvar.setVisible(modoEdicao);
        btnCancelar.setVisible(modoEdicao);
    }

    @Override
    public Item getDadosAnuncioEditado() {
        try {
            String tipoPeca = (String) cbTipoPeca.getSelectedItem();
            String subcategoria = txtSubcategoria.getText();
            String tamanho = txtTamanho.getText();
            String cor = txtCor.getText();
            String composicao = (String) cbComposicao.getSelectedItem();
            double massa = Double.parseDouble(txtMassa.getText().replace(',', '.'));
            String estado = (String) cbEstado.getSelectedItem();
            String defeitoStr = (String) cbDefeitos.getSelectedItem();
            double preco = Double.parseDouble(txtPreco.getText().replace(',', '.'));

            // CONSTRUTOR CORRIGIDO PARA INCLUIR OS NOVOS PARÂMETROS
            return new Item(idcOriginal, tipoPeca, subcategoria, tamanho, cor,
                    new Material(composicao, 0), new Defeito(defeitoStr, 0),
                    estado, massa, preco,
                    null, 0.0, 0.0); // Passa valores padrão para os campos não editáveis
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int exibirConfirmacaoDelecao() {
        Object[] options = {"Apagar", "Cancelar"};
        return JOptionPane.showOptionDialog(this,
                "Deseja mesmo apagar este item?",
                "Confirmação de Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null, options, options[1]);
    }

    @Override public void setEditarListener(ActionListener listener) { btnEditar.addActionListener(listener); }
    @Override public void setSalvarListener(ActionListener listener) { btnSalvar.addActionListener(listener); }
    @Override public void setDeletarListener(ActionListener listener) { btnDeletar.addActionListener(listener); }
    @Override public void setCancelarListener(ActionListener listener) { btnCancelar.addActionListener(listener); }
    @Override public void exibirMensagem(String mensagem) { JOptionPane.showMessageDialog(this, mensagem); }
    @Override public void fechar() { dispose(); }
}