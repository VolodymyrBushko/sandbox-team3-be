package com.exadel.discountwebapp.discount.validator;

import com.exadel.discountwebapp.discount.vo.DiscountBaseVO;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FlatAmountOrPercentageFieldsNotNullValidator implements ConstraintValidator<FlatAmountOrPercentageFieldsNotNull, DiscountBaseVO> {

    @Override
    public void initialize(FlatAmountOrPercentageFieldsNotNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(DiscountBaseVO discountBaseVO, ConstraintValidatorContext constraintValidatorContext) {
        return ((discountBaseVO.getFlatAmount() != null || discountBaseVO.getPercentage() != null)
                &&
                (!(discountBaseVO.getFlatAmount() != null && discountBaseVO.getPercentage() != null)));
    }
}