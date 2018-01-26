package com.example.baseproject.repostitory;

import com.example.baseproject.domain.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

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
    @RestResource(path = "startWith", rel = "startWith")
    PersonModel findById(@Param("id")Long id);

    @RestResource(path = "nameStartsWith", rel = "nameStartsWith")
    PersonModel findByNameStartsWith(@Param("name")String name);
}
