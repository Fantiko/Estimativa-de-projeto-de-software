package ufes.estudos.service;

import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.repository.RepositoriesIntefaces.InsigniasRepository;
import ufes.estudos.repository.RepositoriesIntefaces.PerfilVendedorRepository;
import ufes.estudos.service.ServiceInterfaces.PerfilVendedorServiceInterface;

import java.util.List;
import java.util.Optional;

public class PerfilVendedorService implements PerfilVendedorServiceInterface {

    private final PerfilVendedorRepository perfilVendedorRepository;
    private final InsigniasRepository insigniasRepository;
    public PerfilVendedorService(PerfilVendedorRepository perfilVendedorRepository, InsigniasRepository insigniasRepository) {
        this.perfilVendedorRepository = perfilVendedorRepository;
        this.insigniasRepository = insigniasRepository;
    }
    @Override
    public void criarPerfilVendedor(Usuario usuario) {
        PerfilVendedor perfilVendedor = new PerfilVendedor(usuario);
        perfilVendedorRepository.adicionar(perfilVendedor);
    }

    @Override
    public PerfilVendedor buscarPerfilVendedorPorUsuario(Usuario usuario) {
        Optional<PerfilVendedor> perfilVendedor = perfilVendedorRepository.buscarPorUsuarioId(usuario);

        if (perfilVendedor.isPresent()) {
            return perfilVendedor.get();
        } else {
            throw new RuntimeException("Perfil Vendedor não encontrado para o usuário: " + usuario.getId());

        }
    }

    @Override
    public void adicionarInsignia(int perfilCompradorId, int insigniaId) {
        insigniasRepository.adicionarInsignia(perfilCompradorId, insigniaId);
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
    public void adicionar(int perfilId, int insigniaId) {
        insigniasRepository.adicionarInsignia(perfilId, insigniaId);
    }

    @Override
    public String buscarDescricao(int idInsignea) {
        return insigniasRepository.buscarDescricao(idInsignea);
    }

    @Override
    public List<Insignia> buscarTodas() {

        return insigniasRepository.listarTodasInsignias();
    }

    @Override
    public List<Insignia> listarTodasInsignias() {
        return insigniasRepository.listarTodasInsignias();
    }


}
