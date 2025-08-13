package ufes.estudos.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame implements IMainView {
    private final JLabel lblTitulo;
    private final JButton btnLogout;
    private final JDesktopPane desktopPane;

    public MainView() {
        setLayout(new BorderLayout());

        // Cabeçalho
        lblTitulo = new JLabel("", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Roboto", Font.BOLD, 20));
        add(lblTitulo, BorderLayout.NORTH);

        // Área principal MDI
        desktopPane = new JDesktopPane();
        add(desktopPane, BorderLayout.CENTER);

        // Rodapé com botão de logout
        btnLogout = new JButton("Logout");
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelRodape.add(btnLogout);
        add(painelRodape, BorderLayout.SOUTH);

        // Configurações da janela principal
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void setTitulo(String titulo) {
        lblTitulo.setText(titulo);
    }

    @Override
    public void exibirMenuVendedor() {
        abrirInternalFrame("Menu Vendedor",
                new JButton("Adicionar Anúncio"),
                new JButton("Gerenciar Anúncios")
        );
    }

    @Override
    public void exibirMenuComprador() {
        abrirInternalFrame("Menu Comprador",
                new JButton("Comprar Item"),
                new JButton("Histórico de Compras")
        );
    }

    @Override
    public void exibirMenuAdmin() {
        abrirInternalFrame("Menu Administrador",
                new JButton("Aprovar Perfis"),
                new JButton("Visualizar Logs")
        );
    }

    private void abrirInternalFrame(String titulo, JComponent... componentes) {
        JInternalFrame internalFrame = new JInternalFrame(
                titulo, true, true, true, true
        );
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        for (JComponent c : componentes) {
            panel.add(c);
        }

        internalFrame.add(panel);
        internalFrame.pack();
        internalFrame.setVisible(true);
        desktopPane.add(internalFrame);
        try {
            internalFrame.setSelected(true);
        } catch (Exception ignored) {}
    }

    @Override
    public void setLogoutListener(ActionListener listener) {
        btnLogout.addActionListener(listener);
    }

    @Override
    public void fechar() {
        dispose();
    }
}
