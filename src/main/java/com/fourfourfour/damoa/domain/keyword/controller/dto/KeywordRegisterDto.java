package com.fourfourfour.damoa.domain.keyword.controller.dto;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@ToString(of = {"keywordName"})
public class KeywordRegisterDto {

    @Length(message = "{pattern}", min = 1, max = 20)
    private String keywordName;
}
