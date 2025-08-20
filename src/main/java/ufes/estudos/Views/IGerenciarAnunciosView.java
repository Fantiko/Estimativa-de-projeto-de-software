package ufes.estudos.Views;

import ufes.estudos.Model.Item.Item;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IGerenciarAnunciosView {
    void atualizarTabela(List<Item> anuncios);
    void fechar();
}