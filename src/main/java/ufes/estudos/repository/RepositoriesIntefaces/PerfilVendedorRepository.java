package ufes.estudos.repository.RepositoriesIntefaces;

import ufes.estudos.Model.Usuario.PerfilVendedor;

import java.util.Optional;

public interface PerfilVendedorRepository {
    void adicionar(PerfilVendedor perfil);
    void atualizar(PerfilVendedor perfil);

    Optional<PerfilVendedor> buscarPorId(int id);
    Optional<PerfilVendedor> buscarPorUsuarioId(int usuarioId);

}
