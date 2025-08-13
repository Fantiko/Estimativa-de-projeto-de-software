package ufes.estudos.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame implements IMainView {
    private final JLabel lblTitulo;
    private final JPanel painelMenu;
    private final JButton btnLogout;

    public MainView() {
        setLayout(new BorderLayout());
        lblTitulo = new JLabel("", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Roboto", Font.BOLD, 20));

        painelMenu = new JPanel();
        btnLogout = new JButton("Logout");

        add(lblTitulo, BorderLayout.NORTH);
        add(painelMenu, BorderLayout.CENTER);
        add(btnLogout, BorderLayout.SOUTH);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void setTitulo(String titulo) {
        lblTitulo.setText(titulo);
    }

    @Override
    public void exibirMenuVendedor() {
        painelMenu.removeAll();
        painelMenu.add(new JButton("Adicionar Anúncio"));
        painelMenu.add(new JButton("Gerenciar Anúncios"));
        painelMenu.revalidate();
        painelMenu.repaint();
    }

    @Override
    public void exibirMenuComprador() {
        painelMenu.removeAll();
        painelMenu.add(new JButton("Comprar Item"));
        painelMenu.add(new JButton("Histórico de Compras"));
        painelMenu.revalidate();
        painelMenu.repaint();
    }

    @Override
    public void exibirMenuAdmin() {
        painelMenu.removeAll();
        painelMenu.add(new JButton("Aprovar Perfis"));
        painelMenu.add(new JButton("Visualizar Logs"));
        painelMenu.revalidate();
        painelMenu.repaint();
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
