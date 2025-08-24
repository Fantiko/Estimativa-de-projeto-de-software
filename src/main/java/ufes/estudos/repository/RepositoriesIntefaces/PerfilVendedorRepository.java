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

    void adicionarInsignia(int perfilVendedorId, int insigniaId);
    void removerInsignia(int perfilVendedorId, int insigniaId);
    List<Insignia> buscarInsignias(int perfilVendedorId);

}
