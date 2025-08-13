package ufes.estudos.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame implements ILoginView {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton btnCadastrar;
    private JButton btnLogin;

    public TelaLogin() {
        setTitle("Tela de Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(2, 2, 5, 5));
        painelCampos.add(new JLabel("Usuário:"));
        campoUsuario = new JTextField();
        painelCampos.add(campoUsuario);

        painelCampos.add(new JLabel("Senha:"));
        campoSenha = new JPasswordField();
        painelCampos.add(campoSenha);

        JPanel painelBotoes = new JPanel(new BorderLayout());
        btnCadastrar = new JButton("Cadastre-se");
        btnLogin = new JButton("Fazer Login");
        painelBotoes.add(btnCadastrar, BorderLayout.WEST);
        painelBotoes.add(btnLogin, BorderLayout.EAST);

        painelPrincipal.add(painelCampos, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    @Override
    public String getUsername() {
        return campoUsuario.getText();
    }

    @Override
    public String getSenha() {
        return new String(campoSenha.getPassword());
    }

    @Override
    public void setLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    @Override
    public void setCadastrarListener(ActionListener listener) {
        btnCadastrar.addActionListener(listener);
    }

    @Override
    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}
