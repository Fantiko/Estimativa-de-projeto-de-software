package ufes.estudos.service;

import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.repository.RepositoriesIntefaces.PerfilCompradorRepository;
import ufes.estudos.service.ServiceInterfaces.PerfilCompradorServiceInterface;

import java.util.Optional;

public class PerfilCompradorService implements PerfilCompradorServiceInterface {

    private final PerfilCompradorRepository perfilCompradorRepository;

    public PerfilCompradorService(PerfilCompradorRepository perfilCompradorRepository) {
        this.perfilCompradorRepository = perfilCompradorRepository;
    }

    // ... (outros métodos de Insignia, se houver)

    @Override
    public void criarPerfilComprador(Usuario usuario) {
        // --- CORREÇÃO AQUI ---
        // Agora, o PerfilComprador é criado a partir do usuário existente, herdando seu ID.
        PerfilComprador perfilComprador = new PerfilComprador(usuario);
        perfilCompradorRepository.adicionar(perfilComprador);
    }

    @Override
    public PerfilComprador buscarPerfilCompradorPorUsuario(Usuario usuario) {
        Optional<PerfilComprador> perfil = perfilCompradorRepository.buscarPorUsuarioId(usuario);
        if (perfil.isPresent()) {
            return perfil.get();
        }
        throw new RuntimeException("Perfil de comprador não encontrado para o usuário: " + usuario.getNome());
    }

    // Deixei os métodos de insignia vazios como estavam, para implementarmos no futuro se necessário
    @Override
    public void adicionarInsignia(String nome, String descricao) {}

    @Override
    public void removerInsignia(String nome) {}

    @Override
    public void atualizarInsignia(String nome, String descricao) {}

    @Override
    public boolean verificarInsignia(String nome) { return false; }

    @Override
    public int contarInsignias() { return 0; }

    @Override
    public String buscarDescricao(String nome) { return null; }

    @Override
    public String listarTodasInsignias() { return null; }
}