package ufes.estudos.service.ServiceInterfaces;

import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.Model.Usuario.Usuario;
import java.util.List;

public interface PerfilVendedorServiceInterface {
    void criarPerfilVendedor(Usuario usuario);
    void atualizar(PerfilVendedor perfil);
    PerfilVendedor buscarPerfilVendedorPorUsuario(Usuario usuario);
    void adicionarInsignia(PerfilVendedor perfil, Insignia insignia);
    List<Insignia> buscarInsignias(PerfilVendedor perfil);
}