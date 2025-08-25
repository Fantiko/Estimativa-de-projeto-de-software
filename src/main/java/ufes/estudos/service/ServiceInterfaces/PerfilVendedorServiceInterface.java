package ufes.estudos.service.ServiceInterfaces;

import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.Model.Usuario.Usuario;

import java.util.List;

public interface PerfilVendedorServiceInterface {
    void criarPerfilVendedor(Usuario usuario);

    PerfilVendedor buscarPerfilVendedorPorUsuario(Usuario usuario);

    void adicionarInsignia(int perfilCompradorId, int insigniaId);

    void removerInsignia(int perfilCompradorId, int insigniaId);

    int contarInsignias();

    void adicionar(int perfilId, int insigniaId);

    String buscarDescricao(int idInsignea);

    List<Insignia> buscarTodas();

    List<Insignia> listarTodasInsignias();
}
