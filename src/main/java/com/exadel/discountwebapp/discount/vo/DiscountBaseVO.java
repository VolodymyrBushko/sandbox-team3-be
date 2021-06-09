package com.exadel.discountwebapp.discount.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class DiscountBaseVO implements Serializable {

    @NotBlank(message = "Title should not be empty")
    @Size(min = 2, max = 100, message = "Length of the title should be between 2 and 100")
    private String title;

    @NotBlank(message = "ShortDescription should not be empty")
    @Size(max = 255, message = "The maximum length of the shortDescription is 255")
    private String shortDescription;

    @NotBlank(message = "Description should not be empty")
    @Size(max = 510, message = "The maximum length of the description is 510")
    private String description;

    @Size(max = 510, message = "The maximum length of the imageUrl is 510")
    @Pattern(regexp = "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$", message = "Incorrect imageUrl")
    private String imageUrl;

    @NotNull(message = "FlatAmount should not be null")
    @DecimalMin(value = "0.0", message = "The minimum for the flatAmount is 0.0")
    @Digits(integer = 8, fraction = 2, message = "FlatAmount should have maximum 8 numbers in integer and 2 in fraction part")
    private BigDecimal flatAmount;

    @NotNull(message = "Percentage should not be null")
    @DecimalMin(value = "0.0", message = "The minimum for the percentage is 0.0")
    @Digits(integer = 3, fraction = 1, message = "Percentage should have maximum 3 numbers in integer and 1 in fraction part")
    private BigDecimal percentage;

    @NotNull(message = "Price should not be null")
    @DecimalMin(value = "0.0", message = "The minimum for the price is 0.0")
    @Digits(integer = 8, fraction = 2, message = "Price should have maximum 8 numbers in integer and 2 in fraction part")
    private BigDecimal price;

    @NotNull(message = "StartDate should not be null")
    private LocalDateTime startDate;

    @NotNull(message = "ExpirationDate should not be null")
    @Future(message = "ExpirationDate should be in the future")
    private LocalDateTime expirationDate;

    @NotNull(message = "Quantity should not be null")
    @Positive(message = "Quantity must be a positive number")
    private Integer quantity;

    @NotNull(message = "PerUser should not be null")
    @Positive(message = "PerUser must be a positive number")
    private Integer perUser;

    @NotNull(message = "CategoryId should not be null")
    @Positive(message = "CategoryId must be a positive number")
    private Long categoryId;

    @NotNull(message = "VendorId should not be null")
    @Positive(message = "VendorId must be a positive number")
    private Long vendorId;
}
