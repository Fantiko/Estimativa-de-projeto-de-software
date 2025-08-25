package ufes.estudos.repository.RepositoriesIntefaces;

import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.Model.Usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface PerfilVendedorRepository {
    Optional<PerfilVendedor> adicionar(PerfilVendedor perfil);
    void atualizar(PerfilVendedor perfil);

    Optional<PerfilVendedor> buscarPorId(int id);
    Optional<PerfilVendedor> buscarPorUsuarioId(Usuario usuario);
    List<Insignia> buscarInsignias(int perfilVendedorId);

}
