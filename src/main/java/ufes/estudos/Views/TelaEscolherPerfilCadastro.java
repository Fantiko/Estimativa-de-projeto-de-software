package ufes.estudos.Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaEscolherPerfilCadastro extends JFrame implements IEscolherPerfilCadastroView {
    private JCheckBox checkVendedor;
    private JCheckBox checkComprador;
    private JButton btnFinalizar;

    public TelaEscolherPerfilCadastro() {
        setTitle("Solicitar Perfis");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Selecione os perfis que deseja solicitar:", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Roboto", Font.BOLD, 14));

        JPanel painelChecks = new JPanel(new GridLayout(2, 1, 5, 10));
        painelChecks.setBorder(new EmptyBorder(10, 0, 10, 0));
        checkVendedor = new JCheckBox("Desejo ser um Vendedor");
        checkComprador = new JCheckBox("Desejo ser um Comprador");

        painelChecks.add(checkVendedor);
        painelChecks.add(checkComprador);

        btnFinalizar = new JButton("Finalizar Cadastro e Enviar Solicitações");

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        painelPrincipal.add(painelChecks, BorderLayout.CENTER);
        painelPrincipal.add(btnFinalizar, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    @Override public boolean isVendedorSelecionado() { return checkVendedor.isSelected(); }
    @Override public boolean isCompradorSelecionado() { return checkComprador.isSelected(); }
    @Override public void setFinalizarListener(ActionListener listener) { btnFinalizar.addActionListener(listener); }
    @Override public void exibirMensagem(String mensagem) { JOptionPane.showMessageDialog(this, mensagem); }
    @Override public void fechar() { dispose(); }
}