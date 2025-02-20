package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Api(tags = "Address Book Related Interface")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * add new address
     * @param addressBook addressBook
     * @return result
     */
    @ApiOperation("add new address")
    @PostMapping
    public Result add(@RequestBody AddressBook addressBook) {
        log.info("new address:{}",addressBook.toString());

        addressBookService.add(addressBook);

        return Result.success();
    }

    /**
     * select current default address
     * @return result-default address
     */
    @ApiOperation("select current default address")
    @GetMapping("/default")
    public Result getDefaultAddress() {
        log.info("getting default address");
        AddressBook addressBook = addressBookService.getDefaultAddress();
        return Result.success(addressBook);
    }

    /**
     * set current default address
     * @param addressBook addressBook
     * @return result
     */
    @ApiOperation("set default address")
    @PutMapping("/default")
    public Result setDefaultAddress(@RequestBody AddressBook addressBook) {
        log.info("setting default address:{}",addressBook);
        addressBookService.setDefaultAddress(addressBook);
        return Result.success();
    }

    /**
     * select all addresses of current user
     * @return address book list
     */
    @ApiOperation("select all addresses of current user")
    @GetMapping("/list")
    public Result<List<AddressBook>> list(){
        log.info("list address book");
        List<AddressBook> addressBookList= addressBookService.list();
        return Result.success(addressBookList);
    }

    /**
     * modify address by address id
     * @param addressBook addressBook
     * @return result
     */
    @ApiOperation("modify address by address id")
    @PutMapping
    public Result modify(@RequestBody AddressBook addressBook) {
        log.info("modify address:{}",addressBook);
        addressBookService.modify(addressBook);
        return Result.success();
    }

    /**
     * select address by address id
     * @param id address id
     * @return address
     */
    @ApiOperation("select address by address id")
    @GetMapping("/{id}")
    public Result<AddressBook> getAddress(@PathVariable Long id) {
        log.info("get address:{}",id);
        AddressBook addressBook=addressBookService.getAddressById(id);
        return Result.success(addressBook);
    }

    /**
     * delete address by address id
     * @param id address id
     * @return result
     */
    @ApiOperation("delete address by address id")
    @DeleteMapping
    public Result delete(@RequestParam Long id) {
        log.info("delete address:{}",id);
        addressBookService.delete(id);
        return Result.success();
    }

}
