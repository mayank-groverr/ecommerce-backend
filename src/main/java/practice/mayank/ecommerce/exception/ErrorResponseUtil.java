package practice.mayank.ecommerce.exception;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.context.request.WebRequest;
import java.net.URI;
import java.util.Map;


@Slf4j
@Getter
@Setter
public class ErrorResponseUtil{


    private ErrorResponseUtil(){}


    public static ProblemDetail of(String detail, String title, HttpStatusCode status, WebRequest request) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(status, detail);
        errorDetail.setTitle(title);
        String uri = request.getDescription(false).replace("uri=", "");
        errorDetail.setInstance(URI.create(uri));
        return errorDetail;
    }

    public static ProblemDetail of(String detail, HttpStatusCode status, WebRequest request) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(status, detail);
        String uri = request.getDescription(false).replace("uri=", "");
        errorDetail.setInstance(URI.create(uri));
        return errorDetail;
    }

    public static void addField(ProblemDetail problemDetail, String fieldName, Object value){
        problemDetail.setProperty(fieldName, value);
    }

    public static void addMultipleField(ProblemDetail problemDetail, Map<String, Object> fields){
        problemDetail.setProperties(fields);
    }


}
