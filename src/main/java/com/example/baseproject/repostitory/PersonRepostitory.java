package com.example.baseproject.repostitory;

import com.example.baseproject.domain.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/1/25 16:05
 * @Version 1.0
 */
@RepositoryRestResource(path = "person")
public interface PersonRepostitory extends JpaRepository<PersonModel, Long> {
    /*
    IsAfter、After、IsGreaterThan、GreaterThan
    IsGreaterThanEqual、GreaterThanEqual
    IsBefore、Before、IsLessThan、LessThan
    IsLessThanEqual、LessThanEqual
    IsBetween、Between
    IsNull、Null
    IsNotNull、NotNull
    IsIn、In
    IsNotIn、NotIn
    IsStartingWith、StartingWith、StartsWith
    IsEndingWith、EndingWith、EndsWith
    IsContaining、Containing、Contains
    IsLike、Like
    IsNotLike、NotLike
    IsTrue、True
    IsFalse、False
    Is、Equals
    IsNot、Not
     */

    /**
     * Spring Data这个小型的DSL依旧有其局限性，有时候通过方法名称表达预期的查询很烦琐，甚至无法实现。如果遇到这种情形的话，Spring Data能够让我们通过@Query注解来解决问题
     * @param address
     * @return
     */
    @RestResource(path = "addressContaining", rel = "addressContaining")
    PersonModel findByAddressContaining(@Param("address")String address);

    /**
     * http://localhost:8080/person/search/nameStartsWith?name=董
     * 查询name为开始包含董的数据
     * @param name
     * @return
     */
    @RestResource(path = "nameStartsWith", rel = "nameStartsWith")
    List<PersonModel> findByNameStartsWith(@Param("name")String name);

    /**
     * 要处理String类型的属性时，如果需要忽略大小写则可以在方法签名中加入IgnoringCase，这样执行对比的时候就会不再考虑字符是大写还是小写
     * @param name
     * @param age
     * @return
     */
    @RestResource(path = "nameStartsWithIgnoringCase", rel = "nameStartsWithIgnoringCase")
    List<PersonModel> findByNameStartsWithIgnoringCase(@Param("name")String name ,@Param("age")int age);

    /**
     * 如果需要匹配多个添加则用And和Or连接
     * @param name
     * @param age
     * @return
     */
    @RestResource(path = "nameAndAge", rel = "nameAndAge")
    List<PersonModel> findByNameAndAge(@Param("name")String name ,@Param("age")int age);

    /**
     * 可以在方法名称的结尾处添加OrderBy，实现结果集排序
     * @param name
     * @return
     */
    @RestResource(path = "nameStartsWithOrderByAgeDesc", rel = "nameStartsWithOrderByAgeDesc")
    List<PersonModel> findByNameStartsWithOrderByAgeDesc(@Param("name") String name);

    /**
     * isman为ture的人
     * boolean值查询
     * @return
     */
    @RestResource(path = "manIsFalse", rel = "manIsFalse")
    List<PersonModel> findByIsManIsFalse();
}
