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
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // espaço entre componentes

        // Label Usuário
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST; // alinha à direita
        painelPrincipal.add(new JLabel("Usuário:"), gbc);

        // Campo Usuário
        campoUsuario = new JTextField();
        campoUsuario.setFont(new Font("Roboto", Font.PLAIN, 12));
        campoUsuario.setPreferredSize(new Dimension(200, 28)); // altura reduzida
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // alinha à esquerda dentro da célula
        painelPrincipal.add(campoUsuario, gbc);

        // Label Senha
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Senha:"), gbc);

        // Campo Senha
        campoSenha = new JPasswordField();
        campoSenha.setFont(new Font("Roboto", Font.PLAIN, 12));
        campoSenha.setPreferredSize(new Dimension(200, 28));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(campoSenha, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new BorderLayout());
        btnCadastrar = new JButton("Cadastre-se");
        btnLogin = new JButton("Fazer Login");
        painelBotoes.add(btnCadastrar, BorderLayout.WEST);
        painelBotoes.add(btnLogin, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoes, gbc);

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
