package ufes.estudos.service.ServiceInterfaces;

import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.Usuario;

public interface PerfilCompradorServiceInterface {
    void adicionarInsignia(String nome, String descricao);

    void removerInsignia(String nome);

    void atualizarInsignia(String nome, String descricao);

    boolean verificarInsignia(String nome);

    int contarInsignias();

    String buscarDescricao(String nome);

    String listarTodasInsignias();

    void criarPerfilComprador(Usuario usuario);

    PerfilComprador buscarPerfilCompradorPorUsuario(String usuario);


}
