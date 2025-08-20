package ufes.estudos.service;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IdService {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 12;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Gera um novo Identificador Circular (ID-C) de 12 caracteres.
     * Este método não garante unicidade. A verificação deve ser feita
     * por quem o chama, utilizando o método isUnique().
     * @return uma String com o ID-C gerado.
     */
    public String generate() {
        return IntStream.range(0, LENGTH)
                .map(i -> RANDOM.nextInt(CHARS.length()))
                .mapToObj(randomIndex -> String.valueOf(CHARS.charAt(randomIndex)))
                .collect(Collectors.joining());
    }

    /**
     * Placeholder para a lógica de verificação de unicidade do ID.
     * No futuro, este método pode consultar um banco de dados ou
     * um repositório para garantir que o ID não exista.
     * @param idc o ID a ser verificado.
     * @return true se o ID for único, false caso contrário.
     */
    public boolean isUnique(String idc) {
        // TODO: Implementar a lógica de verificação de unicidade
        // Ex: return !anuncioRepository.existsByIdc(idc);
        return true; // Retornando true por padrão por enquanto
    }
}