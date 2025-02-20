package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressBookMapper {


    /**
     * add new address
     * @param addressBook addressBook
     */
    void add(AddressBook addressBook);


    /**
     * modify address
     * @param addressBook addressBook obj
     */
    void modifyAddress(AddressBook addressBook);

    /**
     * select current default address
     * @param userId userId
     * @return addressBook
     */
    @Select("select * from address_book where user_id=#{userId} and is_default=1")
    AddressBook getDefaultAddress(Long userId);


    /**
     * select all addresses of current user
     * @param addressBook current user's id
     * @return address book list
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * set default address
     * @param addressBook addressBook
     */
    @Update("update address_book set is_default=#{isDefault} where user_id=#{userId} and id=#{id}")
    void setDefaultAddress(AddressBook addressBook);

    /**
     * get address book by address id
     * @param id address id
     * @return address
     */
    @Select("select * from address_book where id=#{id}")
    AddressBook getAddressById(Long id);

    /**
     * delete address by address id
     * @param addressBook addressBook
     */
    @Delete("delete from address_book where user_id=#{userId} and id=#{id}")
    void delete(AddressBook addressBook);
}
