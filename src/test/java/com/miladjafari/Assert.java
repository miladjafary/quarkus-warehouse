package com.miladjafari;

import com.miladjafari.dto.ServiceResponseDto;
import com.miladjafari.dto.ValidationErrorDto;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Assert {
    public static void assertErrorResponse(ServiceResponseDto actualResponse) {
        assertThat(actualResponse, is(notNullValue()));
        assertThat(actualResponse.getStatus(), equalTo(Response.Status.BAD_REQUEST));
        assertThat(actualResponse.getErrors().isEmpty(), is(false));
    }

    public static void assertValidationErrors(List<ValidationErrorDto> expectedErrors, List<ValidationErrorDto> actualErrors) {
        sort(expectedErrors);
        sort(actualErrors);

        for (int i = 0; i < expectedErrors.size(); i++) {
            ValidationErrorDto expected = expectedErrors.get(i);
            ValidationErrorDto actual = actualErrors.get(i);

            assertThat(actual.getCode(), equalTo(expected.getCode()));
            assertThat(actual.getParam(), equalTo(expected.getParam()));
            assertThat(actual.getDescription(), equalTo(expected.getDescription()));
        }
    }

    public static void assertSuccessResponse(ServiceResponseDto actualResponse) {
        assertThat(actualResponse, is(notNullValue()));
        assertThat(actualResponse.getStatus(), equalTo(Response.Status.OK));
        assertThat(actualResponse.getErrors().isEmpty(), is(true));
    }

    private static void sort(List<ValidationErrorDto> errors) {
        errors.sort((error1, error2) -> {
            if (error1.getCode().equals(error2.getCode())) {
                if (error1.getParam().equals(error2.getParam())) {
                    return error1.getDescription().compareTo(error2.getParam());
                }
                return error1.getParam().compareTo(error2.getParam());
            }
            return error1.getCode().compareTo(error2.getCode());
        });
    }
}
