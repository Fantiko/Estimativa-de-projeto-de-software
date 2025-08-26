package ufes.estudos.service.ServiceInterfaces;

import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.Usuario;
import java.util.List;

public interface PerfilCompradorServiceInterface {
    void criarPerfilComprador(Usuario usuario);
    void atualizar(PerfilComprador perfil);
    PerfilComprador buscarPerfilCompradorPorUsuario(Usuario usuario);
    void adicionarInsignia(PerfilComprador perfil, Insignia insignia);
    List<Insignia> buscarInsignias(PerfilComprador perfil);
}