package ufes.estudos.service.ServiceInterfaces;

import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.Usuario;

import java.util.List;

public interface PerfilCompradorServiceInterface {
    void criarPerfilComprador(Usuario usuario);

    PerfilComprador buscarPerfilCompradorPorUsuario(Usuario usuario);

    void adicionarInsignia(int perfilCompradorId, int insigniaId);

    void removerInsignia(int perfilCompradorId, int insigniaId);

    int contarInsignias();

    String buscarDescricao(int idInsignea);


    List<Insignia> listarTodasInsignias();
}