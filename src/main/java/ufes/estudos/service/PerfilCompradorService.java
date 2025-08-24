package ufes.estudos.service;

import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.repository.RepositoriesIntefaces.PerfilCompradorRepository;
import ufes.estudos.service.ServiceInterfaces.PerfilCompradorServiceInterface;

public class PerfilCompradorService implements PerfilCompradorServiceInterface {

    private final PerfilCompradorRepository perfilCompradorRepository;

    public PerfilCompradorService(PerfilCompradorRepository perfilCompradorRepository) {
        this.perfilCompradorRepository = perfilCompradorRepository;
    }

    @Override
    public void adicionarInsignia(String nome, String descricao) {

    }

    @Override
    public void removerInsignia(String nome) {

    }

    @Override
    public void atualizarInsignia(String nome, String descricao) {

    }

    @Override
    public boolean verificarInsignia(String nome) {
        return false;
    }

    @Override
    public int contarInsignias() {
        return 0;
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
    public void criarPerfilComprador(Usuario usuario) {
        PerfilComprador perfilComprador = new PerfilComprador();
        perfilCompradorRepository.adicionar(perfilComprador);
    }

    @Override
    public PerfilComprador buscarPerfilCompradorPorUsuario(String usuario) {
        return null;
    }
}
