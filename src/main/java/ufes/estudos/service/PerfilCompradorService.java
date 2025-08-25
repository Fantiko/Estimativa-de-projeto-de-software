package ufes.estudos.service;

import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.repository.RepositoriesIntefaces.InsigniasRepository;
import ufes.estudos.repository.RepositoriesIntefaces.PerfilCompradorRepository;
import ufes.estudos.service.ServiceInterfaces.PerfilCompradorServiceInterface;

import java.util.List;
import java.util.Optional;

public class PerfilCompradorService implements PerfilCompradorServiceInterface {

    private final PerfilCompradorRepository perfilCompradorRepository;
    private final InsigniasRepository insigniasRepository;

    public PerfilCompradorService(PerfilCompradorRepository perfilCompradorRepository, InsigniasRepository insigniasRepository) {
        this.perfilCompradorRepository = perfilCompradorRepository;
        this.insigniasRepository = insigniasRepository;
    }

    @Override
    public void criarPerfilComprador(Usuario usuario) {
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

    @Override
    public void adicionarInsignia(int perfilId, int insigniaId) {
        insigniasRepository.adicionarInsignia(perfilId, insigniaId);
    }

    @Override
    public void removerInsignia(int perfilId, int insigniaId) {
        insigniasRepository.removerInsignia(perfilId, insigniaId);
    }

    @Override
    public int contarInsignias() {
        return insigniasRepository.contarInsignias();
    }

    @Override
    public String buscarDescricao(int idInsignea) {
        return insigniasRepository.buscarDescricao(idInsignea);
    }

    @Override
    public List<Insignia> listarTodasInsignias() {
        return insigniasRepository.listarTodasInsignias();
    }
}