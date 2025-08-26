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
    public void atualizar(PerfilVendedor perfil) {
        perfilVendedorRepository.atualizar(perfil);
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
    public void adicionarInsignia(PerfilVendedor perfil, Insignia insignia) {
        // A lógica de negócio estaria aqui. Ex: verificar se a insignia existe
        // e depois chamar o repositório correto.
        perfilVendedorRepository.adicionarInsignia(perfil.getId(), insignia.getId());
    }

    @Override
    public List<Insignia> buscarInsignias(PerfilVendedor perfil) {
        return perfilVendedorRepository.buscarInsignias(perfil.getId());
    }
}