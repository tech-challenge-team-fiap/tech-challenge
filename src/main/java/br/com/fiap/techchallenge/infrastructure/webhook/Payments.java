package br.com.fiap.techchallenge.infrastructure.webhook;

public interface Payments {

    boolean checkStatusPayments(String id);

    public void startSetupMockServer(String numberOrder);

    public void stopMockServer();
}
