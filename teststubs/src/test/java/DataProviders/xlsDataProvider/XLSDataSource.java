package DataProviders.xlsDataProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface XLSDataSource {
    String value();
    String sourcePath() default "user.dir";
    String sheetName() default "";
    int sheetIndex() default 0;
    boolean mapToParameters() default false;
    boolean skipFirstString() default false;
}
