package ufes.estudos.service.ServiceInterfaces;

import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.Model.Usuario.Usuario;

public interface PerfilVendedorServiceInterface {
    void adicionarInsignia(String nome, String descricao);

    String buscarDescricao(String nome);

    String listarTodasInsignias();

    void criarPerfilVendedor(Usuario usuario);

    PerfilVendedor buscarPerfilVendedorPorUsuario(Usuario usuario);
}
