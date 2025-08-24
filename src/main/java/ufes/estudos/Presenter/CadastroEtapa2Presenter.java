package ufes.estudos.Presenter;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.*; // Importa todas as Views
import ufes.estudos.repository.RepositoriesSQLite.UsuarioSQLiteRepository;
import ufes.estudos.service.UsuarioService;
import javax.swing.*;

public class CadastroEtapa2Presenter {

    private TelaCadastroEtapa2 view;
    private Usuario usuario;
    private UsuarioService usuarioService;

    public CadastroEtapa2Presenter(TelaCadastroEtapa2 view, Usuario usuario) {
        this.view = view;
        this.usuario = usuario;
        this.usuarioService = new UsuarioService(new UsuarioSQLiteRepository(new SQLiteConnectionManager()));

        this.view.setVoltarListener(e -> voltar());
        this.view.setFinalizarListener(e -> finalizar());
    }

    private void voltar() {
        view.dispose();
        TelaCadastroEtapa1 telaCadastro = new TelaCadastroEtapa1(usuario);
        new CadastroEtapa1Presenter(telaCadastro, usuario);
        telaCadastro.setVisible(true);
    }

    private void finalizar() {
        if (view.getEmail().isEmpty() || view.getRazaoSocial().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nome/Razão Social e E-mail são obrigatórios!");
            return;
        }

        usuario.setNome(view.getRazaoSocial());
        usuario.setEmail(view.getEmail());
        usuario.setTelefone(view.getTelefone());

        boolean isFirstUser = usuarioService.totalUsuarios() == 0;
        if (isFirstUser) {
            usuario.setAdmin(true);
        }

        var usuarioSalvo = usuarioService.registrar(usuario);

        if (usuarioSalvo.isPresent()) {
            if (isFirstUser) {
                // Se for o primeiro, ele é admin e vai direto para o login
                JOptionPane.showMessageDialog(view, "Cadastro concluído! Como primeiro usuário, sua conta é de Administrador.");
                view.dispose();
                TelaLogin telaLogin = new TelaLogin();
                new LoginPresenter(telaLogin);
                telaLogin.setVisible(true);
            } else {
                // Se não for o primeiro, abre a tela de solicitação de perfil
                view.dispose();
                TelaEscolherPerfilCadastro telaEscolha = new TelaEscolherPerfilCadastro();
                new EscolherPerfilCadastroPresenter(telaEscolha, usuarioSalvo.get());
                telaEscolha.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(view, "Falha no cadastro. Verifique se o nome de usuário já existe.");
        }
    }
}