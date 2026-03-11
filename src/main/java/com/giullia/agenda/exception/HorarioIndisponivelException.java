package com.giullia.agenda.exception;

// Exceção lançada quando o horário do serviço já está ocupado
public class HorarioIndisponivelException extends RuntimeException {

    public HorarioIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
