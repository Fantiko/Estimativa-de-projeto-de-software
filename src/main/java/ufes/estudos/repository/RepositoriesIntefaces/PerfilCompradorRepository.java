package ufes.estudos.repository.RepositoriesIntefaces;

import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface PerfilCompradorRepository {
    void adicionar(PerfilComprador perfil);
    void atualizar(PerfilComprador perfil);

    Optional<PerfilComprador> buscarPorId(int id);
    Optional<PerfilComprador> buscarPorUsuarioId(Usuario usuario);

}
