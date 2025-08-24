package ufes.estudos.Presenter;

import ufes.estudos.Model.State.CompradorState;
import ufes.estudos.Model.State.IMainState;
import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IMeuPerfilView;
import ufes.estudos.repository.PerfilRepository;
import javax.swing.JInternalFrame;

public class MeuPerfilPresenter {
    private final IMeuPerfilView view;

    public MeuPerfilPresenter(IMeuPerfilView view, Usuario usuario, IMainState estadoAtual) {
        this.view = view;
        carregarDados(usuario, estadoAtual);
        this.view.setFecharListener(e -> ((JInternalFrame) view).dispose());
    }

    private void carregarDados(Usuario usuario, IMainState estadoAtual) {
        // --- LÓGICA DE VERIFICAÇÃO DE ADMIN ---
        if (usuario.isAdmin()) {
            view.setReputacaoVisivel(false); // Esconde os campos de reputação
            view.exibirDados(usuario.getNome(), usuario.getEmail(), usuario.getTelefone(), null, 0, null);
            return; // Encerra a execução para não entrar na lógica abaixo
        }

        // Lógica para Comprador e Vendedor (permanece a mesma)
        view.setReputacaoVisivel(true); // Garante que os campos estejam visíveis
        PerfilRepository perfilRepository = PerfilRepository.getInstance();

        if (estadoAtual instanceof CompradorState) {
            PerfilComprador perfil = perfilRepository.getComprador(usuario.getNome());
            if (perfil != null) {
                view.exibirDados(usuario.getNome(), usuario.getEmail(), usuario.getTelefone(),
                        perfil.getNivelReputacao().toString(), perfil.getTotalEstrelas(), "Comprador");
            }
        } else { // Vendedor
            PerfilVendedor perfil = perfilRepository.getVendedor(usuario.getNome());
            if (perfil != null) {
                view.exibirDados(usuario.getNome(), usuario.getEmail(), usuario.getTelefone(),
                        perfil.getNivelReputacao().toString(), perfil.getTotalEstrelas(), "Vendedor");
            }
        }
    }
}