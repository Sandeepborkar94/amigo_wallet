package com.infosys.app.transfer_money;

public interface MoneyTransferService 
{
    MoneyTransferResponseDTO transferMoney(MoneyTransferRequestDTO request);
}
