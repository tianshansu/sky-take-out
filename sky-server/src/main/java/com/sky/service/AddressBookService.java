package com.sky.service;


import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    /**
     * add new address
     * @param addressBook addressBook
     */
    void add(AddressBook addressBook);

    /**
     * select current default address
     * @return default address
     */
    AddressBook getDefaultAddress();

    /**
     * set default address
     * @param addressBook addressBook
     */
    void setDefaultAddress(AddressBook addressBook);

    /**
     * select all addresses of current user
     * @return address book list
     */
    List<AddressBook> list();

    /**
     * modify address by address id
     * @param addressBook addressBook
     */
    void modify(AddressBook addressBook);

    /**
     * select address by address id
     * @param id address id
     * @return address
     */
    AddressBook getAddressById(Long id);

    /**
     * delete address by address id
     * @param id address id
     */
    void delete(Long id);
}
