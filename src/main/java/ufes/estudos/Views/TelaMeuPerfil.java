package ufes.estudos.Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaMeuPerfil extends JInternalFrame implements IMeuPerfilView {
    private JLabel lblNome, lblEmail, lblTelefone, lblNivel, lblEstrelas, lblTipoPerfil;
    private JLabel lblTituloNivel, lblTituloEstrelas; // <<< CAMPOS ADICIONADOS
    private JButton btnFechar;

    public TelaMeuPerfil() {
        super("Meus Dados do Perfil", true, true, true, true);
        setSize(400, 300);
        setLayout(new BorderLayout(10, 10));

        JPanel painelDados = new JPanel(new GridLayout(5, 2, 10, 10));
        painelDados.setBorder(new EmptyBorder(15, 15, 15, 15));

        lblTipoPerfil = new JLabel();
        lblTipoPerfil.setFont(new Font("Roboto", Font.BOLD, 16));

        painelDados.add(new JLabel("Nome Completo:"));
        lblNome = new JLabel();
        painelDados.add(lblNome);

        painelDados.add(new JLabel("Email:"));
        lblEmail = new JLabel();
        painelDados.add(lblEmail);

        painelDados.add(new JLabel("Telefone:"));
        lblTelefone = new JLabel();
        painelDados.add(lblTelefone);

        // --- TÍTULOS AGORA SÃO CAMPOS DA CLASSE ---
        lblTituloNivel = new JLabel("Nível de Reputação:");
        painelDados.add(lblTituloNivel);
        lblNivel = new JLabel();
        painelDados.add(lblNivel);

        lblTituloEstrelas = new JLabel("Progresso (Estrelas):");
        painelDados.add(lblTituloEstrelas);
        lblEstrelas = new JLabel();
        painelDados.add(lblEstrelas);

        btnFechar = new JButton("Fechar");
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotao.add(btnFechar);

        add(lblTipoPerfil, BorderLayout.NORTH);
        add(painelDados, BorderLayout.CENTER);
        add(painelBotao, BorderLayout.SOUTH);
    }

    @Override
    public void exibirDados(String nome, String email, String telefone, String nivel, double estrelas, String tipoPerfil) {
        lblTipoPerfil.setText(tipoPerfil != null ? "   Reputação como " + tipoPerfil : "   Perfil de Administrador");
        lblNome.setText(nome);
        lblEmail.setText(email);
        lblTelefone.setText(telefone != null ? telefone : "Não informado");
        lblNivel.setText(nivel);
        lblEstrelas.setText(String.format("%.2f / 5.00", estrelas));
    }

    @Override
    public void setFecharListener(ActionListener listener) {
        btnFechar.addActionListener(listener);
    }

    // --- NOVO MÉTODO IMPLEMENTADO ---
    @Override
    public void setReputacaoVisivel(boolean visivel) {
        lblTituloNivel.setVisible(visivel);
        lblNivel.setVisible(visivel);
        lblTituloEstrelas.setVisible(visivel);
        lblEstrelas.setVisible(visivel);
    }
}