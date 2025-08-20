package ufes.estudos.Views;

import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Presenter.AdicionarAnuncioPresenter;
import ufes.estudos.Presenter.CatalogoPresenter;
import ufes.estudos.Presenter.GerenciarAnunciosPresenter;
import ufes.estudos.Presenter.GerenciarAnunciosPresenter;
import ufes.estudos.Views.TelaGerenciarAnuncios; // Importar a classe concreta

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

public class MainView extends JFrame implements IMainView {
    private final JLabel lblTitulo;
    private final JButton btnLogout;
    private final JButton btnTrocarPerfil;
    private final JDesktopPane desktopPane;

    // Variáveis para controlar a posição em cascata das janelas
    private int cascadeX = 20;
    private int cascadeY = 20;
    private static final int OFFSET = 30;

    public MainView() {
        setLayout(new BorderLayout());

        // Cabeçalho
        lblTitulo = new JLabel("", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Roboto", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adiciona um respiro
        add(lblTitulo, BorderLayout.NORTH);

        // Área principal MDI
        desktopPane = new JDesktopPane();
        add(desktopPane, BorderLayout.CENTER);

        // Rodapé com botão de logout
        btnLogout = new JButton("Logout");
        btnTrocarPerfil = new JButton(); // <<< ADICIONE ESTA LINHA
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelRodape.add(btnTrocarPerfil); // <<< ADICIONE ESTA LINHA
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
    public void exibirMenuVendedor(Usuario usuario) { // PARÂMETRO ADICIONADO
        JButton btnAdicionar = new JButton("Adicionar Anúncio");
        btnAdicionar.addActionListener(e -> {
            TelaAdicionarAnuncio telaAnuncio = new TelaAdicionarAnuncio();
            new AdicionarAnuncioPresenter(telaAnuncio, usuario); // USUÁRIO PASSADO AQUI
            desktopPane.add(telaAnuncio);
            telaAnuncio.setVisible(true);
        });

        JButton btnGerenciar = new JButton("Gerenciar Anúncios");
        btnGerenciar.addActionListener(e -> {
            TelaGerenciarAnuncios telaGerenciar = new TelaGerenciarAnuncios(desktopPane);
            new GerenciarAnunciosPresenter(telaGerenciar, usuario); // <<< LINHA MODIFICADA
            desktopPane.add(telaGerenciar);
            telaGerenciar.setVisible(true);
        });

        abrirInternalFrame("Menu Vendedor",
                btnAdicionar,
                btnGerenciar
        );
    }

    @Override
    public void exibirMenuComprador(Usuario usuario) {
        JButton btnAbrirCatalogo = new JButton("Abrir Catálogo");
        btnAbrirCatalogo.addActionListener(e -> {
            TelaCatalogo tela = new TelaCatalogo();
            new CatalogoPresenter(tela, usuario); // <<< LINHA MODIFICADA
            desktopPane.add(tela);
            tela.setVisible(true);
        });

        abrirInternalFrame("Menu Comprador",
                btnAbrirCatalogo,
                new JButton("Meu Histórico de Compras") // Apenas um botão de placeholder
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
        // 1. Verifica se a janela já está aberta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame.getTitle().equals(titulo)) {
                try {
                    frame.setSelected(true); // Se estiver, apenas a traz para frente
                } catch (PropertyVetoException ignored) {}
                return; // E para a execução para não criar outra
            }
        }

        JInternalFrame internalFrame = new JInternalFrame(
                titulo, true, true, true, true
        );

        // 2. Painel com layout e espaçamento aprimorados
        JPanel panel = new JPanel(new GridLayout(componentes.length, 1, 10, 10)); // Layout vertical com 10px de espaçamento
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem interna de 20px

        // 3. Adiciona e estiliza os componentes
        for (JComponent c : componentes) {
            if (c instanceof JButton) {
                JButton button = (JButton) c;
                button.setFont(new Font("Roboto", Font.PLAIN, 14));
                button.setPreferredSize(new Dimension(200, 40)); // Tamanho preferencial
            }
            panel.add(c);
        }

        internalFrame.add(panel);
        internalFrame.pack(); // Ajusta o tamanho da janela ao conteúdo (agora bem definido)

        // 4. Posiciona a janela em cascata
        internalFrame.setLocation(cascadeX, cascadeY);
        cascadeX += OFFSET;
        cascadeY += OFFSET;

        // Reseta a posição se sair da tela
        if (cascadeX + internalFrame.getWidth() > desktopPane.getWidth() ||
                cascadeY + internalFrame.getHeight() > desktopPane.getHeight()) {
            cascadeX = 20;
            cascadeY = 20;
        }

        internalFrame.setVisible(true);
        desktopPane.add(internalFrame);

        try {
            internalFrame.setSelected(true); // Seleciona a nova janela
        } catch (PropertyVetoException ignored) {}
    }

    @Override
    public void configurarBotaoTrocaPerfil(String texto, ActionListener listener, boolean visivel) {
        btnTrocarPerfil.setText(texto);

        // Remove listeners antigos para evitar acúmulo
        for (ActionListener al : btnTrocarPerfil.getActionListeners()) {
            btnTrocarPerfil.removeActionListener(al);
        }
        // Adiciona o novo listener se ele existir
        if (listener != null) {
            btnTrocarPerfil.addActionListener(listener);
        }

        btnTrocarPerfil.setVisible(visivel);
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