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
    public void atualizar(PerfilComprador perfil) {
        perfilCompradorRepository.atualizar(perfil);
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
    public void adicionarInsignia(PerfilComprador perfil, Insignia insignia) {
        // A chamada agora é para o repositório correto
        perfilCompradorRepository.adicionarInsignia(perfil.getId(), insignia.getId());
    }

    @Override
    public List<Insignia> buscarInsignias(PerfilComprador perfil) {
        return perfilCompradorRepository.buscarInsignias(perfil.getId());
    }
}