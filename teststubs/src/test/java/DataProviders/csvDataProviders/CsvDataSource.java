package DataProviders.csvDataProviders;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CsvDataSource {
    String value();
    char separator() default ';';
    String sourcePath() default "user.dir";
    boolean mapToParameters() default false;
    boolean skipFirstString() default false;
}
