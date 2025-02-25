package com.infosys.app.service;

import com.infosys.app.dto.WalletRequestDTO;

public interface WalletService
{
    String loadWallet(WalletRequestDTO request);
}
