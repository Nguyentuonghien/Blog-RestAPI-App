package com.springboot.restapi.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
* các API sẽ ném ra một ResourceNotFoundException bất cứ khi nào một POST với một id đã cho không được tìm thấy trong cơ sở dữ liệu
* @ResponseStatus: Sử dụng @ResponseStatus trước class định nghĩa exception sẽ chỉ dẫn HTTP Code trả về khi exception này xảy ra từ controller
* */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String resourceName;
    private String fieldName;
    private long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        // co dang: "Post not found with id : 1"
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFieldValue() {
        return fieldValue;
    }

}
