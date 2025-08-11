package ufes.estudos.Views;

import ufes.estudos.Model.Usuario.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaCadastroEtapa1 extends JFrame implements ICadastroView {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JPasswordField campoConfirmarSenha;
    private JButton btnCancelar;
    private JButton btnAvancar;


    public TelaCadastroEtapa1(Usuario usuario) {
        setTitle("Cadastro - Etapa 1");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();

        if(usuario != null){
            campoUsuario.setText(usuario.getUsuario());
            campoSenha.setText(usuario.getSenha());
            campoConfirmarSenha.setText(usuario.getSenha());
        }
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 5, 5));
        painelCampos.add(new JLabel("Usuário:"));
        campoUsuario = new JTextField();
        painelCampos.add(campoUsuario);
        painelCampos.add(new JLabel("Senha:"));
        campoSenha = new JPasswordField();
        painelCampos.add(campoSenha);
        painelCampos.add(new JLabel("Confirmar Senha:"));
        campoConfirmarSenha = new JPasswordField();
        painelCampos.add(campoConfirmarSenha);
        JPanel painelBotoes = new JPanel(new BorderLayout());
        btnCancelar = new JButton("Cancelar");
        btnAvancar = new JButton("Avançar");
        painelBotoes.add(btnCancelar, BorderLayout.WEST);
        painelBotoes.add(btnAvancar, BorderLayout.EAST);
        painelPrincipal.add(painelCampos, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        add(painelPrincipal);
    }

    @Override public String getUsuario() { return campoUsuario.getText(); }
    @Override public String getSenha() { return new String(campoSenha.getPassword()); }
    @Override public String getConfirmarSenha() { return new String(campoConfirmarSenha.getPassword()); }
    @Override public void setAvancarListener(ActionListener listener) { btnAvancar.addActionListener(listener); }
    @Override public void setCancelarListener(ActionListener listener) { btnCancelar.addActionListener(listener); }
    @Override public void exibirMensagem(String mensagem) { JOptionPane.showMessageDialog(this, mensagem); }
}
