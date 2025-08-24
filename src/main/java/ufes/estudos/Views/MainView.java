package ufes.estudos.Views;

import ufes.estudos.Model.State.CompradorState;
import ufes.estudos.Model.State.VendedorState;
import ufes.estudos.Model.State.IMainState;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Presenter.*;
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
    private final JLabel lblUsuarioLogado;

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

        // --- RODAPÉ MODIFICADO ---
        JPanel painelRodape = new JPanel(new BorderLayout()); // Layout modificado
        painelRodape.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Nome do usuário à esquerda
        lblUsuarioLogado = new JLabel();
        lblUsuarioLogado.setFont(new Font("Roboto", Font.ITALIC, 12));
        painelRodape.add(lblUsuarioLogado, BorderLayout.WEST);

        // Botões à direita
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLogout = new JButton("Logout");
        btnTrocarPerfil = new JButton();
        painelBotoes.add(btnTrocarPerfil);
        painelBotoes.add(btnLogout);
        painelRodape.add(painelBotoes, BorderLayout.EAST);

        add(painelRodape, BorderLayout.SOUTH);
        // --- FIM DA MODIFICAÇÃO DO RODAPÉ ---

        // Configurações da janela principal
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Adicione esta ação dentro da classe MainView
    private ActionListener getAbrirPerfilListener(Usuario usuario, IMainState estado) {
        return e -> {
            TelaMeuPerfil tela = new TelaMeuPerfil();
            new MeuPerfilPresenter(tela, usuario, estado);
            desktopPane.add(tela);
            tela.setVisible(true);
        };
    }

    @Override
    public void setTitulo(String titulo) {
        lblTitulo.setText(titulo);
    }

    @Override
    public void setNomeUsuarioLogado(String nomeUsuario) {
        lblUsuarioLogado.setText("Usuário: " + nomeUsuario);
    }

    @Override
    public void exibirMenuVendedor(Usuario usuario) {
        JButton btnAdicionar = new JButton("Adicionar Anúncio");
        btnAdicionar.addActionListener(e -> {
            TelaAdicionarAnuncio telaAnuncio = new TelaAdicionarAnuncio();
            new AdicionarAnuncioPresenter(telaAnuncio, usuario);
            desktopPane.add(telaAnuncio);
            telaAnuncio.setVisible(true);
        });

        JButton btnGerenciar = new JButton("Gerenciar Anúncios");
        btnGerenciar.addActionListener(e -> {
            TelaGerenciarAnuncios telaGerenciar = new TelaGerenciarAnuncios(desktopPane);
            new GerenciarAnunciosPresenter(telaGerenciar, usuario);
            desktopPane.add(telaGerenciar);
            telaGerenciar.setVisible(true);
        });

        JButton btnGerenciarOfertas = new JButton("Gerenciar Ofertas");
        btnGerenciarOfertas.addActionListener(e -> {
            TelaGerenciarOfertas tela = new TelaGerenciarOfertas();
            new GerenciarOfertasPresenter(tela, usuario);
            desktopPane.add(tela);
            tela.setVisible(true);
        });

        JButton btnMeuPerfil = new JButton("Meus Dados do Perfil");
        btnMeuPerfil.addActionListener(getAbrirPerfilListener(usuario, new VendedorState()));

        abrirInternalFrame("Menu Vendedor",
                btnAdicionar,
                btnGerenciar,
                btnGerenciarOfertas,
                btnMeuPerfil // <<< BOTÃO ADICIONADO AQUI
        );
    }

    @Override
    public void exibirMenuComprador(Usuario usuario) {
        JButton btnAbrirCatalogo = new JButton("Abrir Catálogo");
        btnAbrirCatalogo.addActionListener(e -> {
            TelaCatalogo tela = new TelaCatalogo();
            new CatalogoPresenter(tela, usuario);
            desktopPane.add(tela);
            tela.setVisible(true);
        });

        JButton btnMinhasOfertas = new JButton("Minhas Ofertas (Carrinho)");
        btnMinhasOfertas.addActionListener(e -> {
            TelaMinhasOfertas tela = new TelaMinhasOfertas();
            new MinhasOfertasPresenter(tela, usuario);
            desktopPane.add(tela);
            tela.setVisible(true);
        });

        // Botão do perfil que faltava ser adicionado ao frame
        JButton btnMeuPerfil = new JButton("Meus Dados do Perfil");
        btnMeuPerfil.addActionListener(getAbrirPerfilListener(usuario, new CompradorState()));

        // --- CORREÇÃO ESTÁ AQUI ---
        // Adicionamos o 'btnMeuPerfil' à lista de botões a serem exibidos.
        abrirInternalFrame("Menu Comprador",
                btnAbrirCatalogo,
                btnMinhasOfertas,
                btnMeuPerfil, // <<< BOTÃO ADICIONADO AQUI
                new JButton("Meu Histórico de Compras")
        );
    }


    @Override
    public void exibirMenuAdmin(Usuario usuario) {
        JButton btnAprovar = new JButton("Aprovar Perfis");
        btnAprovar.addActionListener(e -> {
            TelaAprovarPerfis tela = new TelaAprovarPerfis();
            new AprovarPerfisPresenter(tela);
            desktopPane.add(tela);
            tela.setVisible(true);
        });

        JButton btnMeuPerfil = new JButton("Meus Dados do Perfil");
        // Para o admin, podemos mostrar o perfil de vendedor como padrão, ou criar uma tela específica.
        // Por ora, ele verá o perfil de vendedor se tiver um.
        btnMeuPerfil.addActionListener(getAbrirPerfilListener(usuario, new VendedorState()));

        abrirInternalFrame("Menu Administrador",
                btnAprovar,
                btnMeuPerfil, // <<< BOTÃO ADICIONADO AQUI
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