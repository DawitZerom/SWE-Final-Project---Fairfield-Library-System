package edu.miu.cs.cs425.fairfieldlibrarywebapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.dto.CheckoutRecordDTO;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.exception.BookCopyNotAvailableException;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.exception.CustomNotFoundException;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.exception.MemberCannotCheckoutException;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.CheckoutRecord;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.repository.BookRepository;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.repository.CheckoutRecordRepository;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.repository.LibraryMemberRepository;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.CheckoutRecordService;

@Service
public class CheckoutRecordServiceImpl implements CheckoutRecordService {

        @Autowired
        private CheckoutRecordRepository checkoutRecordRepository;
        @Autowired
        private BookRepository bookRepository;
        @Autowired
        private LibraryMemberRepository libraryMemberRepository;

        @Override
        public List<CheckoutRecord> getCheckoutRecords() {
                return checkoutRecordRepository.findAllCheckouts();
        }

        @Override
        public Page<CheckoutRecord> getCheckoutRecordsPaged(int pageNo) {
                return checkoutRecordRepository
                                .findAllCheckouts(PageRequest.of(pageNo, 2, Direction.DESC, "checkoutDate"));
        }

        @Override
        public CheckoutRecord findCheckoutRecordById(Integer checkoutRecordId) {
                return checkoutRecordRepository.findById(checkoutRecordId).orElse(null);
        }

        @Override
        public CheckoutRecord saveNewCheckoutRecord(CheckoutRecordDTO checkoutRecordDTO)
                        throws CustomNotFoundException, BookCopyNotAvailableException, MemberCannotCheckoutException {
                var libraryMember = libraryMemberRepository.findByMemberNumber(checkoutRecordDTO.getMemberNumber())
                                .orElseThrow(() -> new CustomNotFoundException(
                                                String.format("Member with number: %s is not found",
                                                                checkoutRecordDTO.getMemberNumber())));
                var previousRecords = checkoutRecordRepository
                                .findCheckoutsdByMemberNumber(libraryMember.getMemberNumber());
                if (previousRecords.size() > 0) {
                        throw new MemberCannotCheckoutException(
                                        String.format("Member with number: %s cannot checkout another book because this member has another checkout record.",
                                                        checkoutRecordDTO.getMemberNumber()));
                }
                var book = bookRepository.findByIsbn(checkoutRecordDTO.getIsbn())
                                .orElseThrow(() -> new CustomNotFoundException(
                                                String.format("Book with ISBN: %s is not found",
                                                                checkoutRecordDTO.getIsbn())));
                // to do with book copy
                if (book.getAvailableCopy() <= 0) {
                        throw new BookCopyNotAvailableException(
                                        String.format("Book copy with ISBN: %s is not available",
                                                        checkoutRecordDTO.getIsbn()));
                }
                book.setAvailableCopy(book.getAvailableCopy() - 1);
                var checkoutRecord = new CheckoutRecord(book, libraryMember);
                return checkoutRecordRepository.save(checkoutRecord);
        }

        @Override
        public CheckoutRecord updateCheckoutRecord(CheckoutRecordDTO checkoutRecordDTO) throws CustomNotFoundException {
                var checkoutRecord = checkoutRecordRepository.findById(checkoutRecordDTO.getCheckoutRecordId())
                                .orElseThrow(() -> new CustomNotFoundException("Checkout record is not found"));
                var book = bookRepository.findByIsbn(checkoutRecordDTO.getIsbn())
                                .orElseThrow(() -> new CustomNotFoundException(
                                                String.format("Book with ISBN: %s is not found",
                                                                checkoutRecordDTO.getIsbn())));
                var libraryMember = libraryMemberRepository.findByMemberNumber(checkoutRecordDTO.getMemberNumber())
                                .orElseThrow(() -> new CustomNotFoundException(
                                                String.format("Member with memberNumber: %s is not found",
                                                                checkoutRecordDTO.getMemberNumber())));
                checkoutRecord.setBook(book);
                checkoutRecord.setLibraryMember(libraryMember);
                return checkoutRecordRepository.save(checkoutRecord);
        }

        @Override
        public void deleteCheckoutRecord(Integer checkoutRecordid) {
                checkoutRecordRepository.deleteById(checkoutRecordid);
        }

        @Override
        public Page<CheckoutRecord> searchCheckoutRecords(String searchString, int pageNo) {
                return checkoutRecordRepository
                                .searchCheckoutsByIsbnContainingOrBookTitleContaininOrMemberNumberContainingOrMemberNameContaining(
                                                searchString, searchString, searchString, searchString, searchString,
                                                PageRequest.of(pageNo, 2, Direction.DESC, "checkoutDate"));
        }

}
