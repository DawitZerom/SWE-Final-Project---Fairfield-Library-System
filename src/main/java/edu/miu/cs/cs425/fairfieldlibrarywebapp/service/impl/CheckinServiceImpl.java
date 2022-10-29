package edu.miu.cs.cs425.fairfieldlibrarywebapp.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.dto.CheckinDTO;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.exception.CustomNotFoundException;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.CheckoutRecord;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.repository.CheckoutRecordRepository;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.CheckinService;

@Service
public class CheckinServiceImpl implements CheckinService {

    @Autowired
    private CheckoutRecordRepository checkoutRecordRepository;

    @Override
    public CheckoutRecord findCheckoutRecordById(Integer checkoutRecordId) {
        return checkoutRecordRepository.findById(checkoutRecordId).orElse(null);
    }

    @Override
    public List<CheckoutRecord> findCheckoutRecordsByIsbn(String isbn) {
        return checkoutRecordRepository.findCheckoutsdByIsbn(isbn);
    }

    @Override
    public CheckoutRecord checkin(CheckoutRecord checkoutRecord) {
        checkoutRecord.setIsCheckedIn("yes");
        checkoutRecord.setCheckinDate(LocalDate.now());
        double overdueFee = calculateOverdueFee(checkoutRecord);
        checkoutRecord.setOverdueFee(overdueFee);
        return checkoutRecordRepository.save(checkoutRecord);
    }

    private double calculateOverdueFee(CheckoutRecord checkoutRecord) {
        double overdueFee = 0;
        if (LocalDate.now().isAfter(checkoutRecord.getDueDate())) {
            var days = ChronoUnit.DAYS.between(checkoutRecord.getDueDate(), LocalDate.now());
            overdueFee = days * checkoutRecord.getLibraryMember().getLibraryMemberType().getOverdueFee();
        }
        return overdueFee;
    }

    @Override
    public Page<CheckoutRecord> getCheckinsPaged(int pageNo) {
        return checkoutRecordRepository.findAllCheckins(PageRequest.of(pageNo, 2, Direction.DESC, "checkinDate"));
    }

    @Override
    public CheckoutRecord updateCheckin(CheckinDTO checkinDTO) throws CustomNotFoundException {
        var checkoutRecord = checkoutRecordRepository.findById(checkinDTO.getCheckoutRecordId())
                .orElseThrow();
        checkoutRecord.setIsCheckedIn(checkinDTO.getIsCheckedIn());
        if (checkinDTO.getIsCheckedIn().toLowerCase().equals("no")) {
            checkoutRecord.setOverdueFee(0.0);
            checkoutRecord.setCheckinDate(null);
        }
        return checkoutRecordRepository.save(checkoutRecord);
    }

    @Override
    public Page<CheckoutRecord> searchCheckins(String searchString, int pageNo) {
        return checkoutRecordRepository
                .searchCheckinsByIsbnContainingOrBookTitleContaininOrMemberNumberContainingOrMemberNameContaining(
                        searchString, searchString, searchString, searchString, searchString,
                        PageRequest.of(pageNo, 2, Direction.DESC, "checkinDate"));
    }

    @Override
    public void deleteCheckin(Integer checkoutRecordid) {
        checkoutRecordRepository.deleteById(checkoutRecordid);
    }

}
