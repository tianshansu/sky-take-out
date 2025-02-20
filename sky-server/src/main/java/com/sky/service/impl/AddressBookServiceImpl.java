package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * add new address
     *
     * @param addressBook addressBook
     */
    @Override
    public void add(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.add(addressBook);

    }

    /**
     * select current default address
     *
     * @return default address
     */
    @Override
    public AddressBook getDefaultAddress() {
        Long userId = BaseContext.getCurrentId();
        AddressBook addressBook = addressBookMapper.getDefaultAddress(userId);

        return addressBook;
    }

    /**
     * set default address
     *
     * @param addressBook address
     */
    @Override
    public void setDefaultAddress(AddressBook addressBook) {
        //remove the previous default address
        AddressBook defaultAddress = getDefaultAddress();
        if (defaultAddress != null) {
            defaultAddress.setIsDefault(0);
            addressBookMapper.setDefaultAddress(defaultAddress);
        }
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setId(addressBook.getId());
        addressBook.setIsDefault(1);
        //set the new address to default
        addressBookMapper.setDefaultAddress(addressBook);
    }

    /**
     * select all addresses of current user
     *
     * @return address book list
     */
    @Override
    public List<AddressBook> list() {
        AddressBook addressBook = AddressBook.builder().userId(BaseContext.getCurrentId()).build();
        List<AddressBook> addressBookList = addressBookMapper.list(addressBook);

        return addressBookList;
    }

    /**
     * modify address by address id
     *
     * @param addressBook addressBook
     */
    @Override
    public void modify(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.modifyAddress(addressBook);
    }

    /**
     * select address by address id
     *
     * @param id address id
     * @return address
     */
    @Override
    public AddressBook getAddressById(Long id) {
        AddressBook addressBook = addressBookMapper.getAddressById(id);
        return addressBook;
    }

    /**
     * delete address by address id
     * @param id address id
     */
    @Override
    public void delete(Long id) {
        AddressBook addressBook = AddressBook.builder().userId(BaseContext.getCurrentId()).id(id).build();
        addressBookMapper.delete(addressBook);
    }
}
