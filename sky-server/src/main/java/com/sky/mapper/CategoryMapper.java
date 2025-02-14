package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * paging category
     * @param queryDTO categoryPageQueryDTO
     * @return category result
     */
    Page<Category> page(CategoryPageQueryDTO queryDTO);


    /**
     * modify category
     * @param category category object
     */
    @AutoFill(value = OperationType.UPDATE) //autofill - type UPDATE
    void modifyCategory(Category category);

    /**
     * add a new category
     * @param category new category object
     */
    @AutoFill(value = OperationType.INSERT) //autofill - type INSERT
    @Insert("insert into category (id, type, name, sort, status, create_time, update_time, create_user, update_user) VALUES (#{id}, #{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void addCategory(Category category);

    @Delete("delete from category where id=#{id}")
    void deleteCategory(Long id);

    //@Select("select * from category where type = #{type}")
    List<Category> listByCategory(Integer type);
}
