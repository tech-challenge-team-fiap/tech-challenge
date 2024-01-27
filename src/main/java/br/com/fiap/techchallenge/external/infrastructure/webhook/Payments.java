package br.com.fiap.techchallenge.external.infrastructure.webhook;

public interface Payments {

    boolean checkStatusPayments(String id);

    public void startSetupMockServer(String numberOrder);

    public void stopMockServer();
}
