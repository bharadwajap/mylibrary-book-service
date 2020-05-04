package com.mylibrary.book.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <pre  class="code">
 * {
 * "type": "https://adidas-group.com/problems/scv/unauthorized",
 * "title": "Authentication required",
 * "detail": "Missing authentication credentials for the Greeting resource.",
 * "instance": "/greeting",
 * "status": 401
 * }
 * </pre>
 *
 * @author Vadym Lotar
 * @since 1.0-SNAPSHOT
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Problem Detail response")
public class ProblemDetail {

    @ApiModelProperty(value = "Problem Detail Title", required = true, example = "Resource not found")
    private final String title;

    @ApiModelProperty(value = "Problem Detail Description", required = true, example = "Requested resource cannot be found")
    private final String detail;

    @ApiModelProperty(value = "Field is identifier and as such it MAY be used to denote additional error codes", example = "ERR_00001")
    private String type;

    @ApiModelProperty(value = "The URI of the resource in question", example = "/books/25534321")
    private String instance;

    @ApiModelProperty(value = "HTTP Status Code", example = "404")
    private Integer status;

    @ApiModelProperty(value = "List of additional errors")
    private List<ProblemDetail> errors;

}
