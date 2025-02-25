package com.infosys.app.service;


import com.infosys.app.entity.CashbackRequestDTO;
import com.infosys.app.entityDto.CashbackResponseDTO;

public interface CashbackService 
{
    CashbackResponseDTO processCashback(CashbackRequestDTO request);
}
