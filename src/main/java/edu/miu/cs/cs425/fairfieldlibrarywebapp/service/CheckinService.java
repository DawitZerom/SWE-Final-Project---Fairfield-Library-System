package edu.miu.cs.cs425.fairfieldlibrarywebapp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.dto.CheckinDTO;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.exception.CustomNotFoundException;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.CheckoutRecord;

public interface CheckinService {

    CheckoutRecord findCheckoutRecordById(Integer checkoutRecordId);

    List<CheckoutRecord> findCheckoutRecordsByIsbn(String isbn);

    CheckoutRecord checkin(CheckoutRecord checkoutRecord);

    Page<CheckoutRecord> getCheckinsPaged(int pageNo);

    CheckoutRecord updateCheckin(CheckinDTO checkinDTO) throws CustomNotFoundException;

    Page<CheckoutRecord> searchCheckins(String searchString, int pageNo);

    void deleteCheckin(Integer checkoutRecordid);
}
