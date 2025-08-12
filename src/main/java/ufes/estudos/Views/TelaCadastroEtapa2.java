package ufes.estudos.Views;

import ufes.estudos.Model.Usuario.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaCadastroEtapa2 extends JFrame {

    private JTextField campoRazaoSocial;
    private JTextField campoEmail;
    private JTextField campoTelefone;
    private JButton btnVoltar;
    private JButton btnFinalizar;


    public TelaCadastroEtapa2(Usuario usuario) {
        setTitle("Cadastro - Etapa 2");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();

        if (usuario != null) {
            campoRazaoSocial.setText(usuario.getRazaoSocial());
            campoEmail.setText(usuario.getEmail());
            campoTelefone.setText(usuario.getTelefone());
        }
    }


    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel painelCampos = new JPanel(new GridLayout(5, 2, 5, 5));
        painelCampos.add(new JLabel("Nome/Razão Social:"));
        campoRazaoSocial = new JTextField();
        painelCampos.add(campoRazaoSocial);
        painelCampos.add(new JLabel("E-mail:"));
        campoEmail = new JTextField();
        painelCampos.add(campoEmail);
        painelCampos.add(new JLabel("Telefone (opcional):"));
        campoTelefone = new JTextField();
        painelCampos.add(campoTelefone);
        JPanel painelChecks = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelCampos.add(painelChecks);
        JPanel painelBotoes = new JPanel(new BorderLayout());
        btnVoltar = new JButton("Voltar");
        btnFinalizar = new JButton("Finalizar Cadastro");
        painelBotoes.add(btnVoltar, BorderLayout.WEST);
        painelBotoes.add(btnFinalizar, BorderLayout.EAST);
        painelPrincipal.add(painelCampos, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        add(painelPrincipal);
    }

    public String getRazaoSocial() { return campoRazaoSocial.getText(); }
    public String getEmail() { return campoEmail.getText(); }
    public String getTelefone() { return campoTelefone.getText(); }
    public void setVoltarListener(ActionListener listener) { btnVoltar.addActionListener(listener); }
    public void setFinalizarListener(ActionListener listener) { btnFinalizar.addActionListener(listener); }
}
