package ufes.estudos.service;

import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.repository.RepositoriesIntefaces.PerfilVendedorRepository;
import ufes.estudos.service.ServiceInterfaces.PerfilCompradorServiceInterface;
import ufes.estudos.service.ServiceInterfaces.PerfilVendedorServiceInterface;

import java.util.Optional;

public class PerfilVendedorService implements PerfilVendedorServiceInterface {

    private final PerfilVendedorRepository perfilVendedorRepository;

    public PerfilVendedorService(PerfilVendedorRepository perfilVendedorRepository) {
        this.perfilVendedorRepository = perfilVendedorRepository;
    }

    @Override
    public void adicionarInsignia(String nome, String descricao) {

    }

    @Override
    public String buscarDescricao(String nome) {
        return null;
    }

    @Override
    public String listarTodasInsignias() {
        return null;
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

}
