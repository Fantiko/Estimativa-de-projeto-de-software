package ufes.estudos.service;

import ufes.estudos.service.ServiceInterfaces.ILog; // Import da nossa interface

public class LogService {
    private static LogService instance;
    private ILog logger;

    private LogService() {
        // Por padrão, inicia com um formato. Vamos usar XML como exemplo.
        // A escolha do usuário será lida do banco/arquivo de config e usada aqui.
        this.logger = new FantikoLogAdapter("xml"); // <<< USA A NOVA CLASSE
    }

    public static LogService getInstance() {
        if (instance == null) {
            instance = new LogService();
        }
        return instance;
    }

    public ILog getLogger() {
        return logger;
    }

    public void setFormato(String formato) {
        // Permite trocar o adaptador em tempo de execução
        this.logger = new FantikoLogAdapter(formato); // <<< USA A NOVA CLASSE
        System.out.println("Formato do log alterado para: " + formato);
    }
}